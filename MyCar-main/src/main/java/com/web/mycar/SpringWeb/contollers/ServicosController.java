package com.web.mycar.SpringWeb.contollers;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.web.mycar.SpringWeb.models.Peca;
import com.web.mycar.SpringWeb.models.Servico;
import com.web.mycar.SpringWeb.repository.ServicoRepo;
import com.web.mycar.SpringWeb.repository.VeiculoRepo;

import jakarta.servlet.http.HttpServletResponse;

import com.web.mycar.SpringWeb.repository.FuncionarioRepo;
import com.web.mycar.SpringWeb.repository.PecasRepo;
// PDF imports
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;  
import java.awt.Color;
//PDF
@Controller
public class ServicosController {

    private final ServicoRepo servicoRepo;
    private final VeiculoRepo veiculoRepo;
    private final FuncionarioRepo funcionarioRepo;
    private final PecasRepo pecaRepo;

    public ServicosController(ServicoRepo servicoRepo, VeiculoRepo veiculoRepo, 
                              FuncionarioRepo funcionarioRepo, PecasRepo pecaRepo) {
        this.servicoRepo = servicoRepo;
        this.veiculoRepo = veiculoRepo;
        this.funcionarioRepo = funcionarioRepo;
        this.pecaRepo = pecaRepo;
    }

    @GetMapping("/servicos")
    public String index(Model model) {
        // Usa o método novo que busca tudo de uma vez
        model.addAttribute("servicos", servicoRepo.findAllCompleto());
        
        model.addAttribute("novoServico", new Servico());
        model.addAttribute("listaVeiculos", veiculoRepo.findAll());
        model.addAttribute("listaFuncionarios", funcionarioRepo.findAll());
        model.addAttribute("listaPecas", pecaRepo.findAll());
        return "servicos/index";
    }

    @PostMapping("/servicos/salvar")
    public String salvar(@ModelAttribute Servico servico) {
        
        double somaTotalPecas = 0.0;

        // 1. Verifica e processa as peças
        if (servico.getPecasUtilizadas() != null) {
            for (var item : servico.getPecasUtilizadas()) {
                
                // Vincula o filho ao pai
                item.setServico(servico); 
                
                // Busca a peça original no banco
                Peca pecaOriginal = pecaRepo.findById(item.getPeca().getId()).orElse(null);
                
                if (pecaOriginal != null) {
                    // === BAIXA DE ESTOQUE ===
                    int estoqueAtual = pecaOriginal.getQuantidadeEstoque();
                    int quantidadeUsada = item.getQuantidade();
                    
                    // Atualiza o estoque da peça
                    pecaOriginal.setQuantidadeEstoque(estoqueAtual - quantidadeUsada);
                    pecaRepo.save(pecaOriginal); // Grava o novo estoque
                    // ========================

                    // Cálculos financeiros
                    item.setValorUnitario(pecaOriginal.getPrecoVenda());
                    double totalItem = pecaOriginal.getPrecoVenda() * item.getQuantidade();
                    item.setValorTotal(totalItem);
                    
                    somaTotalPecas += totalItem;
                }
            }
        }

        // 2. Calcula totais finais
        double maoDeObra = (servico.getValorMaoDeObra() != null) ? servico.getValorMaoDeObra() : 0.0;
        servico.setValorTotal(maoDeObra + somaTotalPecas);

        // 3. Salva o serviço
        servicoRepo.save(servico);
        
        return "redirect:/servicos";
    }


    //Gerar PDF da OS
    @GetMapping("/servicos/imprimir/{id}")
    public void gerarPdf(@PathVariable Long id, HttpServletResponse response) throws DocumentException, IOException {
        
        // 1. Busca o serviço no banco
        Servico servico = servicoRepo.findById(id).orElse(null);
        if (servico == null) return;

        // 2. Configura a resposta para ser um PDF
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=OS_" + id + ".pdf";
        response.setHeader(headerKey, headerValue);

        // 3. Cria o documento PDF
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // --- TÍTULO ---
        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitulo.setSize(18);
        fontTitulo.setColor(Color.BLUE);
        
        Paragraph titulo = new Paragraph("Ordem de Serviço #" + servico.getId(), fontTitulo);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(titulo);
        
        document.add(new Paragraph(" ")); // Pula linha

        // --- DADOS DO CLIENTE E VEÍCULO ---
        Font fontDados = FontFactory.getFont(FontFactory.HELVETICA);
        fontDados.setSize(12);

        document.add(new Paragraph("Data: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(servico.getDataEntrada()), fontDados));
        document.add(new Paragraph("Cliente: " + servico.getVeiculo().getCliente().getNome(), fontDados));
        document.add(new Paragraph("Telefone: " + servico.getVeiculo().getCliente().getTelefone(), fontDados));
        document.add(new Paragraph("Veículo: " + servico.getVeiculo().getModelo() + " - " + servico.getVeiculo().getPlaca(), fontDados));
        if (servico.getMecanico() != null) {
            document.add(new Paragraph("Mecânico Responsável: " + servico.getMecanico().getNome(), fontDados));
        }
        document.add(new Paragraph("Descrição: " + servico.getDescricao(), fontDados));
        
        document.add(new Paragraph(" ")); // Pula linha

        // --- TABELA DE PEÇAS ---
        if (servico.getPecasUtilizadas() != null && !servico.getPecasUtilizadas().isEmpty()) {
            PdfPTable table = new PdfPTable(4); // 4 Colunas
            table.setWidthPercentage(100f);
            table.setWidths(new float[] {3.5f, 1.0f, 2.0f, 2.0f});
            table.setSpacingBefore(10);

            // Cabeçalho da Tabela
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setPadding(5);
            
            cell.setPhrase(new Phrase("Peça/Item")); table.addCell(cell);
            cell.setPhrase(new Phrase("Qtd")); table.addCell(cell);
            cell.setPhrase(new Phrase("Valor Un.")); table.addCell(cell);
            cell.setPhrase(new Phrase("Total")); table.addCell(cell);

            // Dados da Tabela
            for (var item : servico.getPecasUtilizadas()) {
                table.addCell(item.getPeca().getNome());
                table.addCell(String.valueOf(item.getQuantidade()));
                table.addCell("R$ " + String.format("%.2f", item.getValorUnitario()));
                table.addCell("R$ " + String.format("%.2f", item.getValorTotal()));
            }
            document.add(table);
        } else {
            document.add(new Paragraph("Nenhuma peça utilizada neste serviço."));
        }

        document.add(new Paragraph(" ")); // Pula linha

        // --- TOTAIS ---
        Font fontTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTotal.setSize(14);

        double maoDeObra = servico.getValorMaoDeObra() != null ? servico.getValorMaoDeObra() : 0.0;
        document.add(new Paragraph("Valor Mão de Obra: R$ " + String.format("%.2f", maoDeObra), fontDados));
        
        Paragraph totalFinal = new Paragraph("TOTAL A PAGAR: R$ " + String.format("%.2f", servico.getValorTotal()), fontTotal);
        totalFinal.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(totalFinal);

        document.close();
    }
}