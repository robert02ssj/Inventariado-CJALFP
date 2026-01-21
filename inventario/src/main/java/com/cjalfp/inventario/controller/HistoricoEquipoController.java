package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Inventario;
import com.cjalfp.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/consultas/historico-equipo")
@RequiredArgsConstructor
public class HistoricoEquipoController {

    private final InventarioRepository inventarioRepository;

    @GetMapping
    public String historicoEquipo(
            @RequestParam(required = false) String serie,
            Model model) {
        
        if (serie != null && !serie.isEmpty()) {
            List<Inventario> historial = inventarioRepository.findHistorialByEquipo(serie);
            
            if (!historial.isEmpty()) {
                // Calcular días para cada asignación
                Map<Integer, Long> diasPorAsignacion = new HashMap<>();
                for (Inventario inv : historial) {
                    long dias = calcularDias(inv.getFechaAsignacion(), inv.getFechaDevolucion());
                    diasPorAsignacion.put(inv.getId(), dias);
                }
                
                model.addAttribute("equipo", historial.get(0).getEquipo());
                model.addAttribute("historial", historial);
                model.addAttribute("diasPorAsignacion", diasPorAsignacion);
                model.addAttribute("encontrado", true);
            } else {
                model.addAttribute("encontrado", false);
                model.addAttribute("mensaje", "No se encontró ningún equipo con ese número de serie/IMEI");
            }
        }
        
        model.addAttribute("busquedaActual", serie);
        return "consultas/historico-equipo";
    }
    
    // Método auxiliar para calcular días entre fechas
    public static long calcularDias(LocalDateTime inicio, LocalDateTime fin) {
        if (fin == null) {
            fin = LocalDateTime.now();
        }
        return ChronoUnit.DAYS.between(inicio, fin);
    }
}
