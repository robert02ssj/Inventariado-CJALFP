package com.cjalfp.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/consultas")
public class ConsultasController {

    @GetMapping
    public String menuConsultas() {
        return "consultas/menu";
    }
}
