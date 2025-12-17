package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Telefono;
import com.cjalfp.inventario.repository.EstadoRepository;
import com.cjalfp.inventario.repository.LineaRepository;
import com.cjalfp.inventario.repository.ModeloRepository;
import com.cjalfp.inventario.repository.TelefonoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/telefonos")
@RequiredArgsConstructor
public class TelefonoController {

    private final TelefonoRepository telefonoRepository;
    private final ModeloRepository modeloRepository;
    private final EstadoRepository estadoRepository;
    private final LineaRepository lineaRepository;

    // --- 1. NUEVO TELÉFONO ---
    @GetMapping("/nuevo")
    public String nuevoTelefono(Model model) {
        Telefono telefono = new Telefono();
        telefono.setMovilidad(true); // Por defecto, un teléfono suele ser móvil
        
        model.addAttribute("telefono", telefono);
        
        // Carga de desplegables
        model.addAttribute("modelos", modeloRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        model.addAttribute("lineas", lineaRepository.findAll());
        
        model.addAttribute("titulo", "Nuevo Teléfono");
        return "telefonos/formulario";
    }

    // --- 2. EDITAR TELÉFONO ---
    @GetMapping("/editar/{id}")
    public String editarTelefono(@PathVariable Integer id, Model model) {
        Telefono telefono = telefonoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        
        model.addAttribute("telefono", telefono);
        
        model.addAttribute("modelos", modeloRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        model.addAttribute("lineas", lineaRepository.findAll());
        
        model.addAttribute("titulo", "Editar Teléfono");
        return "telefonos/formulario";
    }

    // --- 3. GUARDAR ---
    @PostMapping("/guardar")
    public String guardarTelefono(@ModelAttribute Telefono telefono) {
        telefonoRepository.save(telefono);
        return "redirect:/equipos?tipo=2"; // Redirige a la pestaña Teléfonos
    }

    // --- 4. BORRAR ---
    @GetMapping("/borrar/{id}")
    public String borrarTelefono(@PathVariable Integer id) {
        telefonoRepository.deleteById(id);
        return "redirect:/equipos?tipo=2";
    }
}