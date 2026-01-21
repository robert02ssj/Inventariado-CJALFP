package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Raton;
import com.cjalfp.inventario.repository.EstadoRepository;
import com.cjalfp.inventario.repository.ModeloRepository;
import com.cjalfp.inventario.repository.RatonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ratones")
@RequiredArgsConstructor
public class RatonController {

    private final RatonRepository ratonRepository;
    private final ModeloRepository modeloRepository;
    private final EstadoRepository estadoRepository;

    @GetMapping("/nuevo")
    public String nuevoRaton(Model model) {
        model.addAttribute("raton", new Raton());
        model.addAttribute("modelos", modeloRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        model.addAttribute("titulo", "Nuevo Ratón");
        return "ratones/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarRaton(@PathVariable Integer id, Model model) {
        Raton raton = ratonRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        model.addAttribute("raton", raton);
        model.addAttribute("modelos", modeloRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        model.addAttribute("titulo", "Editar Ratón");
        return "ratones/formulario";
    }

    @PostMapping("/guardar")
    public String guardarRaton(@ModelAttribute Raton raton, RedirectAttributes redirectAttributes) {
        ratonRepository.save(raton);
        redirectAttributes.addFlashAttribute("mensaje", "✅ Ratón guardado correctamente");
        return "redirect:/equipos?tipo=4";
    }

    @GetMapping("/borrar/{id}")
    public String borrarRaton(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            ratonRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "✅ Ratón eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Error al eliminar el ratón");
        }
        return "redirect:/equipos?tipo=4";
    }
}