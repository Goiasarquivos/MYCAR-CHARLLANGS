package com.web.mycar.SpringWeb.contollers;

import com.web.mycar.SpringWeb.models.Cliente;
import com.web.mycar.SpringWeb.models.Veiculo;
import com.web.mycar.SpringWeb.repository.ClientesRepo;
import jakarta.validation.Valid; // <--- Importante
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // <--- Importante
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientesController {

    private final ClientesRepo repo;

    public ClientesController(ClientesRepo repo) {
        this.repo = repo;
    }

    // 1. LISTAR
    @GetMapping("/clientes")
    public String index(Model model) {
        // 1. Carrega lista
        model.addAttribute("clientes", repo.findAllWithVeiculos());
        
        // 2. Cria cliente vazio para o formulário não quebrar
        Cliente novoCliente = new Cliente();
        
        // 3. OBRIGATÓRIO: Cria a lista e adiciona um veículo vazio
        // Se pular isso, dá tela branca por causa do veiculos[0]
        java.util.List<Veiculo> listaVeiculos = new java.util.ArrayList<>();
        listaVeiculos.add(new Veiculo());
        novoCliente.setVeiculos(listaVeiculos);
        
        model.addAttribute("cliente", novoCliente);
        
        return "clientes/index";
    }

    // 2. SALVAR (COM VALIDAÇÃO)
    @PostMapping("/clientes/salvar")
    // Adicionamos @Valid, BindingResult e Model
    public String salvar(@Valid Cliente cliente, BindingResult result, Model model) {
        
        // 1. Verifica se tem erro (Ex: Telefone errado ou Nome vazio)
        if (result.hasErrors()) {
            
            // Recarrega a lista de clientes para a tabela do fundo não sumir
            model.addAttribute("clientes", repo.findAllWithVeiculos());
            
            // Avisa o HTML para reabrir o modal e mostrar o erro
            model.addAttribute("erroNoFormulario", true);
            
            return "clientes/index"; // Volta para a mesma tela
        }

        // Vincula os veículos ao cliente atual.
        // Sem isso, os veículos seriam salvos com o campo 'cliente_id' nulo.
        if (cliente.getVeiculos() != null && !cliente.getVeiculos().isEmpty()) {
            for (Veiculo veiculo : cliente.getVeiculos()) {
                veiculo.setCliente(cliente);
            }
        }
        
        // 3. Salva
        repo.save(cliente);
        return "redirect:/clientes";
    }


    @GetMapping("/clientes/{id}/excluir")
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

@GetMapping("/clientes/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        // Busca o cliente no banco
        Cliente cliente = repo.findById(id).orElse(null);
        
        // Manda para a tela preencher o formulário
        model.addAttribute("cliente", cliente);
        
        // Recarrega a lista de fundo
        model.addAttribute("clientes", repo.findAllWithVeiculos());
        
        return "clientes/index";
    }
    
}
