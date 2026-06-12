package com.web.mycar.SpringWeb.contollers;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Importante para capturar erros
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.mycar.SpringWeb.models.Peca;
import com.web.mycar.SpringWeb.repository.PecasRepo;

import jakarta.validation.Valid; // Importante para ativar a validação

@Controller
public class PecasController {
    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,##0.00")


    private final PecasRepo repo;

    public PecasController(PecasRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/pecas")
    public String index(Model model){
        List<Peca> pecas = repo.findAll();
        model.addAttribute("pecas", pecas);

        //  Envia um objeto vazio para o formulário poder usar
        model.addAttribute("peca", new Peca());

        return "pecas/index";
    }

    
    @PostMapping("/peca/salvar")
    public String salvar(@Valid Peca peca, BindingResult result, Model model) {
        
        // 1. Verifica se houve algum erro de validação (campo vazio, etc)
        if (result.hasErrors()) {
            // Se houve erro, precisamos carregar a lista de peças novamente
            // senão a tabela lá embaixo vai sumir quando a página recarregar
            model.addAttribute("pecas", repo.findAll());
            
            // Retorna para a mesma página (não faz o redirect) para mostrar os erros
            return "pecas/index";
        }

        // 2. Se não tem erros, salva no banco
        repo.save(peca);
        
        // 3. Redireciona para limpar o formulário e atualizar a lista
        return "redirect:/pecas";
    }

    @GetMapping("/pecas/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
    try {
        repo.deleteById(id);
        attr.addFlashAttribute("mensagemSucesso", "Cliente excluído com sucesso!");
    } catch (DataIntegrityViolationException e) {
        // Se der erro (tem carro ou serviço), cai aqui e mostra a mensagem bonita
        attr.addFlashAttribute("mensagemErro", "Não é possível excluir este cliente pois ele possui veículos ou serviços associados!");
    }
    
    // NÃO COLOCAR deleteById AQUI DE NOVO!
    
    return "redirect:/clientes";
}

    @GetMapping("/pecas/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        // Busca a peça pelo ID
        Peca pecaEncontrada = repo.findById(id).orElse(null);

        // Envia ela para o formulário (o modal vai abrir preenchido)
        model.addAttribute("peca", pecaEncontrada);
        model.addAttribute("pecas", repo.findAll()); // Carrega a tabela atrás
        
        return "pecas/index";
    }

    
}