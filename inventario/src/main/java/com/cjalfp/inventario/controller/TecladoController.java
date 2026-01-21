package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Teclado;
import com.cjalfp.inventario.repository.EstadoRepository;
import com.cjalfp.inventario.repository.ModeloRepository;
import com.cjalfp.inventario.repository.TecladoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/teclados")
@RequiredArgsConstructor
public class TecladoController {

    private final TecladoRepository tecladoRepository;
    private final ModeloRepository modeloRepository;
    private final EstadoRepository estadoRepository;

    @GetMapping("/nuevo")
    public String nuevoTeclado(Model model) {
        model.addAttribute("teclado", new Teclado());
        model.addAttribute("modelos", modeloRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        model.addAttribute("titulo", "Nuevo Teclado");
        return "teclados/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarTeclado(@PathVariable Integer id, Model model) {
        Teclado teclado = tecladoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        model.addAttribute("teclado", teclado);
        model.addAttribute("modelos", modeloRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        model.addAttribute("titulo", "Editar Teclado");
        return "teclados/formulario";
    }

    @PostMapping("/guardar")
    public String guardarTeclado(@ModelAttribute Teclado teclado, RedirectAttributes redirectAttributes) {
        tecladoRepository.save(teclado);
        redirectAttributes.addFlashAttribute("mensaje", "✅ Teclado guardado correctamente");
        return "redirect:/equipos?tipo=5";
    }

    @GetMapping("/borrar/{id}")
    public String borrarTeclado(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            tecladoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "✅ Teclado eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Error al eliminar el teclado");
        }
        return "redirect:/equipos?tipo=5";
    }
}