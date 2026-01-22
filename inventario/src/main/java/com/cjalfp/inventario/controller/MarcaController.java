package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Marca;
import com.cjalfp.inventario.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaRepository marcaRepository;

    // --- 1. LISTAR ---
    @GetMapping
    public String listarMarcas(
            @RequestParam(required = false) String busqueda,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        
        int pageSize = 10;
        List<Marca> lista = marcaRepository.findAll();

        if (busqueda != null && !busqueda.isEmpty()) {
            lista = lista.stream()
                    .filter(m -> m.getNombreFabricante().toLowerCase().contains(busqueda.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Aplicar paginación manual
        int totalItems = lista.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        int fromIndex = page * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);
        
        List<Marca> marcasPaginadas = (fromIndex < totalItems) 
            ? lista.subList(fromIndex, toIndex) 
            : new ArrayList<>();

        model.addAttribute("marcas", marcasPaginadas);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("busquedaActual", busqueda);
        return "marcas/lista";
    }

    // --- 2. FORMULARIO NUEVA MARCA ---
    @GetMapping("/nueva")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("marca", new Marca());
        model.addAttribute("titulo", "Nueva Marca");
        return "marcas/formulario";
    }

    // --- 3. FORMULARIO EDITAR MARCA ---
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de marca inválido: " + id));
        
        model.addAttribute("marca", marca);
        model.addAttribute("titulo", "Editar Marca");
        return "marcas/formulario";
    }

    // --- 4. GUARDAR ---
    @PostMapping("/guardar")
    public String guardarMarca(@ModelAttribute Marca marca, RedirectAttributes redirectAttributes) {
        marcaRepository.save(marca);
        redirectAttributes.addFlashAttribute("mensaje", "✅ Marca guardada correctamente");
        return "redirect:/marcas";
    }

    // --- 5. BORRAR ---
    @GetMapping("/borrar/{id}")
    public String borrarMarca(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            marcaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "✅ Marca eliminada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Error: La marca tiene modelos asociados y no se puede eliminar");
        }
        return "redirect:/marcas";
    }

    // --- 6. GUARDAR AJAX ---
    @PostMapping("/guardar-ajax")
    @ResponseBody
    public Marca guardarAjax(@RequestBody Marca marca) {
        if (marca.getNombreFabricante() == null || marca.getNombreFabricante().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del fabricante es obligatorio");
        }
        return marcaRepository.save(marca);
    }
}