package com.web.mycar.SpringWeb.contollers;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FinanceiroController {
    @GetMapping("/financeiro")
    public String index() {
        return "financeiro/index";
    }
    
}