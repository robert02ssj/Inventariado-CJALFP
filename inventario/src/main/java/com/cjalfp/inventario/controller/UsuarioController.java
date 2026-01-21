package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Usuario;
import com.cjalfp.inventario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    // --- LISTAR (Ya lo tenías) ---
    @GetMapping
    public String listarUsuarios(
            @RequestParam(required = false) String busqueda,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        
        int pageSize = 10;
        List<Usuario> lista = usuarioRepository.findAll();
        
        if (busqueda != null && !busqueda.isEmpty()) {
            String term = busqueda.toLowerCase();
            lista = lista.stream()
                    .filter(u -> u.getNombre().toLowerCase().contains(term) ||
                                 (u.getApellidos() != null && u.getApellidos().toLowerCase().contains(term)) ||
                                 (u.getLdap() != null && u.getLdap().toLowerCase().contains(term)))
                    .collect(Collectors.toList());
        }
        
        // Aplicar paginación manual
        int totalItems = lista.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        int fromIndex = page * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);
        
        List<Usuario> usuariosPaginados = (fromIndex < totalItems) 
            ? lista.subList(fromIndex, toIndex) 
            : new ArrayList<>();
        
        model.addAttribute("usuarios", usuariosPaginados);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("busquedaActual", busqueda);
        return "usuarios/lista";
    }

    // --- 1. MOSTRAR FORMULARIO DE CREACIÓN ---
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("usuario", new Usuario()); // Objeto vacío
        model.addAttribute("titulo", "Nuevo Usuario");
        return "usuarios/formulario";
    }

    // --- 2. MOSTRAR FORMULARIO DE EDICIÓN ---
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de usuario inválido:" + id));
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Editar Usuario");
        return "usuarios/formulario";
    }

    // --- 3. GUARDAR (Crear o Actualizar) ---
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/usuarios"; // Vuelve a la lista
    }

    // --- 4. BORRAR ---
    @GetMapping("/borrar/{id}")
    public String borrarUsuario(@PathVariable Integer id) {
        // En una app real, aquí comprobaríamos si el usuario tiene equipos asignados antes de borrar
        usuarioRepository.deleteById(id);
        return "redirect:/usuarios";
    }
}