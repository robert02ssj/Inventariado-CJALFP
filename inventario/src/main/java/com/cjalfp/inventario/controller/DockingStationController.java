package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.DockingStation;
import com.cjalfp.inventario.repository.DockingStationRepository;
import com.cjalfp.inventario.repository.EstadoRepository;
import com.cjalfp.inventario.repository.ModeloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/docking")
@RequiredArgsConstructor
public class DockingStationController {

    private final DockingStationRepository dockingRepository;
    private final ModeloRepository modeloRepository;
    private final EstadoRepository estadoRepository;

    // --- 1. NUEVA DOCKING ---
    @GetMapping("/nuevo")
    public String nuevaDocking(Model model) {
        model.addAttribute("dockingStation", new DockingStation());
        
        // Cargamos modelos y estados (No hacen falta líneas aquí)
        model.addAttribute("modelos", modeloRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        
        model.addAttribute("titulo", "Nueva Docking Station");
        return "docking/formulario";
    }

    // --- 2. EDITAR DOCKING ---
    @GetMapping("/editar/{id}")
    public String editarDocking(@PathVariable Integer id, Model model) {
        DockingStation docking = dockingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        
        model.addAttribute("dockingStation", docking);
        
        model.addAttribute("modelos", modeloRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        
        model.addAttribute("titulo", "Editar Docking Station");
        return "docking/formulario";
    }

    // --- 3. GUARDAR ---
    @PostMapping("/guardar")
    public String guardarDocking(@ModelAttribute DockingStation dockingStation, RedirectAttributes redirectAttributes) {
        dockingRepository.save(dockingStation);
        redirectAttributes.addFlashAttribute("mensaje", "✅ Docking Station guardada correctamente");
        return "redirect:/equipos?tipo=6"; // Redirige a la pestaña Docking
    }

    // --- 4. BORRAR ---
    @GetMapping("/borrar/{id}")
    public String borrarDocking(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            dockingRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "✅ Docking Station eliminada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Error al eliminar la Docking Station");
        }
        return "redirect:/equipos?tipo=6";
    }
}