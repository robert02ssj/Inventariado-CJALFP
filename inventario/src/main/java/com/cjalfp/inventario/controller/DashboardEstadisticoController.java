package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Equipo;
import com.cjalfp.inventario.model.Estado;
import com.cjalfp.inventario.model.Tipo;
import com.cjalfp.inventario.repository.EquipoRepository;
import com.cjalfp.inventario.repository.EstadoRepository;
import com.cjalfp.inventario.repository.InventarioRepository;
import com.cjalfp.inventario.repository.TipoRepository;
import com.cjalfp.inventario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/consultas/dashboard")
@RequiredArgsConstructor
public class DashboardEstadisticoController {

    private final EquipoRepository equipoRepository;
    private final EstadoRepository estadoRepository;
    private final TipoRepository tipoRepository;
    private final UsuarioRepository usuarioRepository;
    private final InventarioRepository inventarioRepository;

    @GetMapping
    public String dashboardEstadistico(Model model) {
        
        // Estados del sistema:
        // 1 = Disponible
        // 2 = Asignado
        // 3 = Averiado/Retirado
        
        // 1. Equipos por tipo
        List<Tipo> tipos = tipoRepository.findAll();
        Map<String, Long> equiposPorTipo = new HashMap<>();
        long totalEquipos = 0;
        
        for (Tipo tipo : tipos) {
            // Usar query optimizada en lugar de filtrar en memoria
            long count = equipoRepository.countByTipoObjetoId(tipo.getId());
            equiposPorTipo.put(tipo.getNombre(), count);
            totalEquipos += count;
        }
        
        // 2. Equipos por estado (dinámico, funciona con cualquier número de estados)
        List<Estado> estados = estadoRepository.findAll();
        Map<String, Long> equiposPorEstado = new HashMap<>();
        Map<String, Double> porcentajesPorEstado = new HashMap<>();
        
        for (Estado estado : estados) {
            // Usar query optimizada en lugar de cargar todos los equipos
            long count = equipoRepository.countByEstadoId(estado.getId());
            equiposPorEstado.put(estado.getNombreEstado(), count);
            
            if (totalEquipos > 0) {
                double porcentaje = (count * 100.0) / totalEquipos;
                porcentajesPorEstado.put(estado.getNombreEstado(), porcentaje);
            } else {
                porcentajesPorEstado.put(estado.getNombreEstado(), 0.0);
            }
        }
        
        // 3. Usuarios con/sin equipos
        long totalUsuarios = usuarioRepository.count();
        long usuariosSinEquipos = usuarioRepository.findUsuariosSinEquipos().size();
        long usuariosConEquipos = totalUsuarios - usuariosSinEquipos;
        double porcentajeUsuariosConEquipos = totalUsuarios > 0 ? 
            (usuariosConEquipos * 100.0) / totalUsuarios : 0.0;
        
        model.addAttribute("equiposPorTipo", equiposPorTipo);
        model.addAttribute("totalEquipos", totalEquipos);
        model.addAttribute("equiposPorEstado", equiposPorEstado);
        model.addAttribute("porcentajesPorEstado", porcentajesPorEstado);
        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("usuariosConEquipos", usuariosConEquipos);
        model.addAttribute("usuariosSinEquipos", usuariosSinEquipos);
        model.addAttribute("porcentajeUsuariosConEquipos", porcentajeUsuariosConEquipos);
        
        return "consultas/dashboard-estadistico";
    }
}
