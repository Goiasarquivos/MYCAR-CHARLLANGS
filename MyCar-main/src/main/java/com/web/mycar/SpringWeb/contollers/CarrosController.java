package com.web.mycar.SpringWeb.contollers;

import com.web.mycar.SpringWeb.models.Veiculo;
import com.web.mycar.SpringWeb.repository.ClientesRepo;
import com.web.mycar.SpringWeb.repository.VeiculoRepo;
import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CarrosController {

    private final VeiculoRepo veiculoRepo;
    private final ClientesRepo clientesRepo;

    public CarrosController(VeiculoRepo veiculoRepo, ClientesRepo clientesRepo) {
        this.veiculoRepo = veiculoRepo;
        this.clientesRepo = clientesRepo;
    }

    // Lista todos os veículos e carrega os clientes para o formulário
    @GetMapping("/carros")
    public String index(Model model) {
        List<Veiculo> veiculos = veiculoRepo.findAll();
        model.addAttribute("veiculos", veiculos);
        model.addAttribute("clientes", clientesRepo.findAll());
        model.addAttribute("veiculo", new Veiculo());
        return "carros/index";
    }

    // Salvar novo veículo com Validação de Placa Duplicada
    @PostMapping("/carros/salvar")
    public String salvar(@Valid @ModelAttribute Veiculo veiculo, BindingResult result, Model model, RedirectAttributes attr) {
        
        // 1. Se houver erro de validação básica (Ex: campo vazio)
        if (result.hasErrors()) {
            model.addAttribute("clientes", clientesRepo.findAll());
            model.addAttribute("veiculos", veiculoRepo.findAll());
            return "carros/index"; 
        }

        try {
            // 2. Tenta salvar no banco
            veiculoRepo.save(veiculo);
            attr.addFlashAttribute("mensagemSucesso", "Carro salvo com sucesso!");
            return "redirect:/carros"; // Redireciona para limpar o formulário

        } catch (DataIntegrityViolationException e) {
            // 3. Se der erro de PLACA DUPLICADA, cai aqui
            model.addAttribute("mensagemErro", "Erro: Já existe um carro cadastrado com a placa " + veiculo.getPlaca());
            
            // Recarrega as listas para a página não quebrar ao voltar
            model.addAttribute("clientes", clientesRepo.findAll());
            model.addAttribute("veiculos", veiculoRepo.findAll());
            
            return "carros/index"; // Volta para a mesma tela (mantém o que foi digitado)
        }
    }

    // Excluir Carros com Validação de Histórico
    @GetMapping("/carros/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        try {
            veiculoRepo.deleteById(id);
            attr.addFlashAttribute("mensagemSucesso", "Veículo excluído com sucesso!");
        } catch (DataIntegrityViolationException e) {
            // Se o carro tiver serviços vinculados, cai aqui
            attr.addFlashAttribute("mensagemErro", "Não é possível excluir este veículo pois ele possui serviços registrados no histórico!");
        }
        return "redirect:/carros";
    }
    // Método para abrir o formulário com os dados do carro preenchidos
    @GetMapping("/carros/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        // 1. Busca o carro que você quer editar
        Veiculo veiculo = veiculoRepo.findById(id).orElse(null);
        
        // 2. Manda esse carro específico para o formulário (para preencher os campos)
        model.addAttribute("veiculo", veiculo);
        
        // 3. PRECISA recarregar as listas de apoio (Tabela de baixo e Select de Clientes)
        // Se não fizer isso, a tabela e o dropdown de clientes somem da tela!
        model.addAttribute("veiculos", veiculoRepo.findAll());
        model.addAttribute("clientes", clientesRepo.findAll());
        
        // 4. Retorna para a MESMA tela (carros/index), mas agora com os dados preenchidos
        return "carros/index"; 
    }
    
}