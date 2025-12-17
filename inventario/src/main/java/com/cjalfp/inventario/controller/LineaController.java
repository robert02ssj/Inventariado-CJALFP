package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Linea;
import com.cjalfp.inventario.repository.LineaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/lineas")
@RequiredArgsConstructor
public class LineaController {

    private final LineaRepository lineaRepository;

    // --- 1. LISTAR ---
    @GetMapping
    public String listarLineas(@RequestParam(required = false) String busqueda, Model model) {
        List<Linea> lista = lineaRepository.findAll();

        if (busqueda != null && !busqueda.isEmpty()) {
            String term = busqueda.toLowerCase();
            lista = lista.stream()
                    .filter(l -> (l.getNumeroCorto() != null && l.getNumeroCorto().contains(term)) ||
                                 (l.getNumeroLargo() != null && l.getNumeroLargo().contains(term)))
                    .collect(Collectors.toList());
        }

        model.addAttribute("lineas", lista);
        model.addAttribute("busquedaActual", busqueda);
        return "lineas/lista";
    }

    // --- 2. NUEVA ---
    @GetMapping("/nueva")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("linea", new Linea());
        model.addAttribute("titulo", "Nueva Línea Móvil");
        return "lineas/formulario";
    }

    // --- 3. EDITAR ---
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Linea linea = lineaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("linea", linea);
        model.addAttribute("titulo", "Editar Línea");
        return "lineas/formulario";
    }

    // --- 4. GUARDAR ---
    @PostMapping("/guardar")
    public String guardarLinea(@ModelAttribute Linea linea) {
        lineaRepository.save(linea);
        return "redirect:/lineas";
    }

    // --- 5. BORRAR ---
    @GetMapping("/borrar/{id}")
    public String borrarLinea(@PathVariable Integer id) {
        lineaRepository.deleteById(id);
        return "redirect:/lineas";
    }
}