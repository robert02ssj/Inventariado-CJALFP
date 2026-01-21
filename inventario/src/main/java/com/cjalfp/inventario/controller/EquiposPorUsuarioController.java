package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Inventario;
import com.cjalfp.inventario.model.Usuario;
import com.cjalfp.inventario.repository.InventarioRepository;
import com.cjalfp.inventario.repository.UsuarioRepository;
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

@Controller
@RequestMapping("/consultas/equipos-por-usuario")
@RequiredArgsConstructor
public class EquiposPorUsuarioController {

    private final InventarioRepository inventarioRepository;
    private final UsuarioRepository usuarioRepository;

    @GetMapping
    public String equiposPorUsuario(
            @RequestParam(required = false) String busqueda,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        
        int pageSize = 10;
        List<Inventario> asignacionesActivas;
        
        // Buscar asignaciones activas
        if (busqueda != null && !busqueda.isEmpty()) {
            asignacionesActivas = inventarioRepository.findAsignacionesActivasByUsuario(busqueda);
        } else {
            asignacionesActivas = inventarioRepository.findAsignacionesActivas();
        }
        
        // Agrupar equipos por usuario
        Map<Usuario, List<Inventario>> equiposPorUsuario = new LinkedHashMap<>();
        for (Inventario inv : asignacionesActivas) {
            Usuario usuario = inv.getUsuario();
            equiposPorUsuario.computeIfAbsent(usuario, k -> new ArrayList<>()).add(inv);
        }
        
        // Convertir a lista para paginar
        List<Map.Entry<Usuario, List<Inventario>>> listaUsuarios = new ArrayList<>(equiposPorUsuario.entrySet());
        
        // Aplicar paginación
        int totalItems = listaUsuarios.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        int fromIndex = page * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);
        
        List<Map.Entry<Usuario, List<Inventario>>> usuariosPaginados = 
            (fromIndex < totalItems) ? listaUsuarios.subList(fromIndex, toIndex) : new ArrayList<>();
        
        model.addAttribute("equiposPorUsuario", usuariosPaginados);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("busquedaActual", busqueda);
        
        return "consultas/equipos-por-usuario";
    }
}
