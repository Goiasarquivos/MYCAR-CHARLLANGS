package com.web.mycar.SpringWeb.contollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.web.mycar.SpringWeb.models.Servico;
import com.web.mycar.SpringWeb.repository.ClientesRepo;
import com.web.mycar.SpringWeb.repository.ServicoRepo;
import com.web.mycar.SpringWeb.repository.VeiculoRepo;

@Controller
public class HomeController {

    // 1. Injeção de dependência do Repositório
    private final VeiculoRepo veiculoRepo;
    private final ClientesRepo clientesRepo;
    private final ServicoRepo servicoRepo;

    public HomeController(VeiculoRepo veiculoRepo, ClientesRepo clientesRepo, ServicoRepo servicoRepo) {
        this.veiculoRepo = veiculoRepo;
        this.clientesRepo = clientesRepo;
        this.servicoRepo = servicoRepo;
    }

    // método para cuidar da Home
    @GetMapping("/")
    public String index(Model model) {
        
        // Lógica 1: Adicionar o nome 
        model.addAttribute("nome", "José");

        // Lógica 2: Contar os carros (estava no método dashboard)
        long totalCarros = veiculoRepo.count();
        model.addAttribute("qtdCarros", totalCarros);

        long totalClientes = clientesRepo.count();
        model.addAttribute("qtdClientes", totalClientes);

        long totalServicos = servicoRepo.count();
        model.addAttribute("qtdServicos", totalServicos);

        // Retorna o HTML
        return "home/index";
    }

    
}
