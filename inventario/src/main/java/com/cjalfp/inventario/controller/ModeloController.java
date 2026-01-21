package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Marca;
import com.cjalfp.inventario.model.Modelo;
import com.cjalfp.inventario.repository.MarcaRepository;
import com.cjalfp.inventario.repository.ModeloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/modelos")
@RequiredArgsConstructor
public class ModeloController {

    private final ModeloRepository modeloRepository;
    private final MarcaRepository marcaRepository; // NECESARIO PARA EL DESPLEGABLE

    // --- 1. LISTAR ---
    @GetMapping
    public String listarModelos(
            @RequestParam(required = false) String busqueda,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        
        int pageSize = 10;
        List<Modelo> lista = modeloRepository.findAll();

        if (busqueda != null && !busqueda.isEmpty()) {
            String term = busqueda.toLowerCase();
            lista = lista.stream()
                    .filter(m -> m.getNombre().toLowerCase().contains(term) ||
                                 m.getMarca().getNombreFabricante().toLowerCase().contains(term))
                    .collect(Collectors.toList());
        }

        // Aplicar paginación manual
        int totalItems = lista.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        int fromIndex = page * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);
        
        List<Modelo> modelosPaginados = lista.subList(fromIndex, toIndex);

        model.addAttribute("modelos", modelosPaginados);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("busquedaActual", busqueda);
        return "modelos/lista";
    }

    // --- 2. NUEVO ---
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("modelo", new Modelo());
        // Pasamos la lista de marcas para llenar el <select>
        model.addAttribute("marcas", marcaRepository.findAll()); 
        model.addAttribute("titulo", "Nuevo Modelo");
        return "modelos/formulario";
    }

    // --- 3. EDITAR ---
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        
        model.addAttribute("modelo", modelo);
        // También necesitamos las marcas al editar para poder cambiarla si queremos
        model.addAttribute("marcas", marcaRepository.findAll());
        model.addAttribute("titulo", "Editar Modelo");
        return "modelos/formulario";
    }

    // --- 4. GUARDAR ---
    @PostMapping("/guardar")
    public String guardarModelo(@ModelAttribute Modelo modelo) {
        // Spring es listo: al recibir el ID de la marca del formulario, 
        // busca la entidad Marca automáticamente y la asigna.
        modeloRepository.save(modelo);
        return "redirect:/modelos";
    }

    // --- 5. BORRAR ---
    @GetMapping("/borrar/{id}")
    public String borrarModelo(@PathVariable Integer id) {
        try {
            modeloRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("No se puede borrar el modelo, seguramente hay equipos asignados.");
        }
        return "redirect:/modelos";
    }
}