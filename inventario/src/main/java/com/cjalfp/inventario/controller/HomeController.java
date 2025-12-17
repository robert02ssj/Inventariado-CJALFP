package com.cjalfp.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        // La plantilla principal está en templates/index.html
        return "index"; // Esto busca el archivo templates/index.html
    }
}