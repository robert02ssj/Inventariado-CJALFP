package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Modelo;
import com.cjalfp.inventario.repository.ModeloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/modelos")
@RequiredArgsConstructor
public class ModeloController {

    private final ModeloRepository modeloRepository;

    @GetMapping
    public String listarModelos(
            @RequestParam(required = false) String busqueda,
            Model model) {

        List<Modelo> lista = modeloRepository.findAll();

        // Filtro: Buscamos coincidencias en el nombre del Modelo O en el nombre de la Marca
        if (busqueda != null && !busqueda.isEmpty()) {
            String term = busqueda.toLowerCase();
            lista = lista.stream()
                    .filter(m -> m.getNombre().toLowerCase().contains(term) || 
                                 m.getMarca().getNombreFabricante().toLowerCase().contains(term))
                    .collect(Collectors.toList());
        }

        model.addAttribute("modelos", lista);
        model.addAttribute("busquedaActual", busqueda);

        return "modelos/lista";
    }
}