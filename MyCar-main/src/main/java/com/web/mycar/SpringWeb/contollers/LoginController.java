package com.web.mycar.SpringWeb.contollers; // Nota: Verifique se a pasta é 'contollers' ou 'controllers'

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// ADICIONE ESTA LINHA ABAIXO:
@Controller 
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login/index";
    }
}