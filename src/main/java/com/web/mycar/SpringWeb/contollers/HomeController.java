package com.web.mycar.SpringWeb.contollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/","/index"})
    public String index(Model model) {

        model.addAttribute("nome","José");
        // Retorna o nome da view dentro da pasta templates/home
        return "home/index";
    }

    @GetMapping("/relatorios")
    public String relatorios(Model model) {
        // Retorna o relatórios view
        return "home/ralatorios";
    }
    
}