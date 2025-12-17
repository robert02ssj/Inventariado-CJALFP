package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Inventario;
import com.cjalfp.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioRepository inventarioRepository;

    @GetMapping
    public String listarInventario(
            @RequestParam(required = false) String busqueda,
            Model model) {

        // 1. Obtener todo el histórico
        List<Inventario> lista = inventarioRepository.findAll();

        // 2. Filtro del Buscador (si el usuario escribió algo)
        if (busqueda != null && !busqueda.isEmpty()) {
            String term = busqueda.toLowerCase();
            lista = lista.stream()
                .filter(inv ->
                    // Busca por Nombre o Apellidos del Usuario
                    (inv.getUsuario().getNombre() + " " + inv.getUsuario().getApellidos()).toLowerCase().contains(term) ||
                    // Busca por LDAP
                    (inv.getUsuario().getLdap() != null && inv.getUsuario().getLdap().toLowerCase().contains(term)) ||
                    // Busca por Nº Serie del Equipo
                    (inv.getEquipo().getNumeroSerie() != null && inv.getEquipo().getNumeroSerie().toLowerCase().contains(term)) ||
                    // Busca por Modelo del Equipo
                    (inv.getEquipo().getModelo().getNombre().toLowerCase().contains(term))
                )
                .collect(Collectors.toList());
        }

        model.addAttribute("inventarios", lista);
        model.addAttribute("busquedaActual", busqueda);

        return "inventario/lista"; // Renderiza templates/inventario/lista.html
    }
}