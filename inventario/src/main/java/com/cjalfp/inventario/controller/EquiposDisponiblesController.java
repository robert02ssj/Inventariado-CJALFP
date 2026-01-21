package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Equipo;
import com.cjalfp.inventario.model.Tipo;
import com.cjalfp.inventario.repository.EquipoRepository;
import com.cjalfp.inventario.repository.TipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/consultas/equipos-disponibles")
@RequiredArgsConstructor
public class EquiposDisponiblesController {

    private final EquipoRepository equipoRepository;
    private final TipoRepository tipoRepository;

    @GetMapping
    public String equiposDisponibles(
            @RequestParam(required = false) Integer tipoId,
            Model model) {
        
        // Obtener equipos con estado Disponible (id = 1)
        List<Equipo> equiposDisponibles = equipoRepository.findByEstadoId(1);
        
        // Filtrar por tipo si se especifica
        if (tipoId != null) {
            equiposDisponibles = equiposDisponibles.stream()
                .filter(e -> e.getTipoObjeto() != null && e.getTipoObjeto().getId().equals(tipoId))
                .collect(Collectors.toList());
        }
        
        // Agrupar equipos por tipo
        Map<String, List<Equipo>> equiposPorTipo = new LinkedHashMap<>();
        for (Equipo equipo : equiposDisponibles) {
            if (equipo.getTipoObjeto() != null) {
                String nombreTipo = equipo.getTipoObjeto().getNombre();
                equiposPorTipo.computeIfAbsent(nombreTipo, k -> new ArrayList<>()).add(equipo);
            }
        }
        
        // Obtener todos los tipos para el filtro
        List<Tipo> tipos = tipoRepository.findAll();
        
        model.addAttribute("equiposPorTipo", equiposPorTipo);
        model.addAttribute("totalDisponibles", equiposDisponibles.size());
        model.addAttribute("tipos", tipos);
        model.addAttribute("tipoSeleccionado", tipoId);
        
        return "consultas/equipos-disponibles";
    }
}
