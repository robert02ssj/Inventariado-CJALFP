package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Pantalla;
import com.cjalfp.inventario.repository.EstadoRepository;
import com.cjalfp.inventario.repository.ModeloRepository;
import com.cjalfp.inventario.repository.PantallaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pantallas")
@RequiredArgsConstructor
public class PantallaController {

    private final PantallaRepository pantallaRepository;
    private final ModeloRepository modeloRepository;
    private final EstadoRepository estadoRepository;

    @GetMapping("/nueva")
    public String nuevaPantalla(Model model) {
        model.addAttribute("pantalla", new Pantalla());
        model.addAttribute("modelos", modeloRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        model.addAttribute("titulo", "Nueva Pantalla");
        return "pantallas/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarPantalla(@PathVariable Integer id, Model model) {
        Pantalla pantalla = pantallaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("pantalla", pantalla);
        model.addAttribute("modelos", modeloRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        model.addAttribute("titulo", "Editar Pantalla");
        return "pantallas/formulario";
    }

    @PostMapping("/guardar")
    public String guardarPantalla(@ModelAttribute Pantalla pantalla, RedirectAttributes redirectAttributes) {
        pantallaRepository.save(pantalla);
        redirectAttributes.addFlashAttribute("mensaje", "✅ Pantalla guardada correctamente");
        return "redirect:/equipos?tipo=3";
    }

    @GetMapping("/borrar/{id}")
    public String borrarPantalla(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            pantallaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "✅ Pantalla eliminada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Error al eliminar la pantalla");
        }
        return "redirect:/equipos?tipo=3";
    }
}