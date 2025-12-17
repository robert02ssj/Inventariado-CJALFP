package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Ordenador;
import com.cjalfp.inventario.repository.EstadoRepository;
import com.cjalfp.inventario.repository.LineaRepository;
import com.cjalfp.inventario.repository.ModeloRepository;
import com.cjalfp.inventario.repository.OrdenadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ordenadores")
@RequiredArgsConstructor
public class OrdenadorController {

    private final OrdenadorRepository ordenadorRepository;
    private final ModeloRepository modeloRepository;
    private final EstadoRepository estadoRepository;
    private final LineaRepository lineaRepository;

    // NOTA: El "Listar" ya lo hace el EquipoController general, 
    // así que aquí nos centramos solo en Crear/Editar/Borrar.

    // --- 1. NUEVO ORDENADOR ---
    @GetMapping("/nuevo")
    public String nuevoOrdenador(Model model) {
        model.addAttribute("ordenador", new Ordenador());
        
        // CARGAMOS LOS DESPLEGABLES
        model.addAttribute("modelos", modeloRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        model.addAttribute("lineas", lineaRepository.findAll());
        
        model.addAttribute("titulo", "Nuevo Ordenador");
        return "ordenadores/formulario";
    }

    // --- 2. EDITAR ORDENADOR ---
    @GetMapping("/editar/{id}")
    public String editarOrdenador(@PathVariable Integer id, Model model) {
        Ordenador ordenador = ordenadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        
        model.addAttribute("ordenador", ordenador);
        
        // CARGAMOS LOS DESPLEGABLES TAMBIÉN AL EDITAR
        model.addAttribute("modelos", modeloRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        model.addAttribute("lineas", lineaRepository.findAll());
        
        model.addAttribute("titulo", "Editar Ordenador");
        return "ordenadores/formulario";
    }

    // --- 3. GUARDAR ---
    @PostMapping("/guardar")
    public String guardarOrdenador(@ModelAttribute Ordenador ordenador) {
        // Al guardar, JPA sabe automáticamente que es un Tipo 1 (Ordenador)
        ordenadorRepository.save(ordenador);
        return "redirect:/equipos?tipo=1"; // Redirigimos a la pestaña de Ordenadores
    }

    // --- 4. BORRAR ---
    @GetMapping("/borrar/{id}")
    public String borrarOrdenador(@PathVariable Integer id) {
        ordenadorRepository.deleteById(id);
        return "redirect:/equipos?tipo=1";
    }
}