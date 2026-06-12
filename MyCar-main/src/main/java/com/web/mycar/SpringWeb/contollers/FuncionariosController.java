package com.web.mycar.SpringWeb.contollers;

import com.web.mycar.SpringWeb.models.Funcionario;
import com.web.mycar.SpringWeb.repository.FuncionarioRepo;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.format.annotation.NumberFormat;
@Controller
public class FuncionariosController {

    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,##0.00")

    private final FuncionarioRepo repo;

    public FuncionariosController(FuncionarioRepo repo) {
        this.repo = repo;
    }

    // 1. LISTAR
    @GetMapping("/funcionarios")
    public String index(Model model) {
        // Carrega a lista
        model.addAttribute("funcionarios", repo.findAll());
        
        // --- CORREÇÃO 1: Envia o objeto vazio (evita Tela Branca) ---
        model.addAttribute("funcionario", new Funcionario());
        
        return "funcionarios/index";
    }

    // 2. SALVAR
    @PostMapping("/funcionario/salvar")
    public String salvar(@Valid Funcionario funcionario, BindingResult result, Model model, RedirectAttributes attr) {
        
        // Lógica de erro correta ---
        if (result.hasErrors()) {
            model.addAttribute("funcionarios", repo.findAll()); // Recarrega a lista
            model.addAttribute("erroNoFormulario", true); // Abre o modal
            return "funcionarios/index"; // Volta para a mesma tela
        }

        try {
            repo.save(funcionario);
            attr.addFlashAttribute("mensagemSucesso", "Funcionário salvo com sucesso!");
            
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagemErro", "Erro: Verifique se os dados já não existem.");
            model.addAttribute("funcionarios", repo.findAll());
            return "funcionarios/index";
        }
        
        return "redirect:/funcionarios";
    }

    // 3. EDITAR
    @GetMapping("/funcionarios/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Funcionario funcionario = repo.findById(id).orElse(null);
        
        model.addAttribute("funcionario", funcionario);
        model.addAttribute("funcionarios", repo.findAll());
        
        return "funcionarios/index";
    }

    // 4. EXCLUIR
    @GetMapping("/funcionarios/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        try {
            repo.deleteById(id);
            attr.addFlashAttribute("mensagemSucesso", "Funcionário excluído com sucesso!");
        } catch (DataIntegrityViolationException e) {
            attr.addFlashAttribute("mensagemErro", "Não é possível excluir: Funcionário possui vínculos no sistema.");
        }
        
        // --- CORREÇÃO 3: Redireciona para Funcionários, não Clientes ---
        return "redirect:/funcionarios";
    }
}