package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Inventario;
import com.cjalfp.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/consultas/asignaciones-antiguas")
@RequiredArgsConstructor
public class AsignacionesAntiguasController {

    private final InventarioRepository inventarioRepository;

    @GetMapping
    public String asignacionesAntiguas(Model model) {
        
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime hace365Dias = ahora.minusDays(365);
        LocalDateTime hace180Dias = ahora.minusDays(180);
        
        // Obtener asignaciones anteriores a hace 365 días (muy antiguas)
        List<Inventario> asignacionesMuyAntiguas = inventarioRepository
            .findAsignacionesAnterioresA(hace365Dias);
        
        // Obtener asignaciones entre 180 y 365 días
        List<Inventario> todasAntiguas = inventarioRepository
            .findAsignacionesAnterioresA(hace180Dias);
        
        // Usar HashSet para optimizar la búsqueda (O(1) en lugar de O(n))
        Set<Integer> idsMuyAntiguas = new HashSet<>();
        for (Inventario inv : asignacionesMuyAntiguas) {
            idsMuyAntiguas.add(inv.getId());
        }
        
        // Filtrar asignaciones antiguas (excluir muy antiguas)
        List<Inventario> asignacionesAntiguas = todasAntiguas.stream()
            .filter(inv -> !idsMuyAntiguas.contains(inv.getId()))
            .toList();
        
        // Calcular días para cada asignación
        Map<Integer, Long> diasPorAsignacion = new HashMap<>();
        for (Inventario inv : asignacionesMuyAntiguas) {
            long dias = ChronoUnit.DAYS.between(inv.getFechaAsignacion(), ahora);
            diasPorAsignacion.put(inv.getId(), dias);
        }
        for (Inventario inv : asignacionesAntiguas) {
            long dias = ChronoUnit.DAYS.between(inv.getFechaAsignacion(), ahora);
            diasPorAsignacion.put(inv.getId(), dias);
        }
        
        model.addAttribute("asignacionesMuyAntiguas", asignacionesMuyAntiguas);
        model.addAttribute("asignacionesAntiguas", asignacionesAntiguas);
        model.addAttribute("diasPorAsignacion", diasPorAsignacion);
        
        return "consultas/asignaciones-antiguas";
    }
}
