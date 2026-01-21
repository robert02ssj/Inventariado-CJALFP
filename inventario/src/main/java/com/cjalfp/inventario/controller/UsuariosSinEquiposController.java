package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Usuario;
import com.cjalfp.inventario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/consultas/usuarios-sin-equipos")
@RequiredArgsConstructor
public class UsuariosSinEquiposController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping
    public String usuariosSinEquipos(Model model) {
        
        // Obtener usuarios sin equipos asignados
        List<Usuario> usuariosSinEquipos = usuarioRepository.findUsuariosSinEquipos();
        long totalUsuarios = usuarioRepository.count();
        long usuariosSinEquiposCount = usuariosSinEquipos.size();
        
        double porcentaje = totalUsuarios > 0 ? 
            (usuariosSinEquiposCount * 100.0) / totalUsuarios : 0.0;
        
        model.addAttribute("usuariosSinEquipos", usuariosSinEquipos);
        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("usuariosSinEquiposCount", usuariosSinEquiposCount);
        model.addAttribute("porcentaje", porcentaje);
        
        return "consultas/usuarios-sin-equipos";
    }
}
