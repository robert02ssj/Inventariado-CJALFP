package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Equipo;
import com.cjalfp.inventario.model.Estado;
import com.cjalfp.inventario.model.Inventario;
import com.cjalfp.inventario.repository.EquipoRepository;
import com.cjalfp.inventario.repository.EstadoRepository;
import com.cjalfp.inventario.repository.InventarioRepository;
import com.cjalfp.inventario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioRepository inventarioRepository;
    private final EquipoRepository equipoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstadoRepository estadoRepository;

    // --- 1. LISTAR HISTÓRICO ---
    @GetMapping
    public String listarInventario(
            @RequestParam(required = false) String busqueda,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        
        int pageSize = 10;
        List<Inventario> lista = inventarioRepository.findAll();

        if (busqueda != null && !busqueda.isEmpty()) {
            String term = busqueda.toLowerCase();
            lista = lista.stream()
                .filter(inv ->
                    (inv.getUsuario().getNombre() + " " + inv.getUsuario().getApellidos()).toLowerCase().contains(term) ||
                    (inv.getEquipo().getModelo().getNombre().toLowerCase().contains(term)) ||
                    (inv.getEquipo().getNumeroSerie() != null && inv.getEquipo().getNumeroSerie().toLowerCase().contains(term))
                )
                .collect(Collectors.toList());
        }
        
        // Ordenamos para ver lo más reciente primero (opcional pero recomendado)
        lista.sort((a, b) -> b.getFechaAsignacion().compareTo(a.getFechaAsignacion()));

        // Aplicar paginación manual
        int totalItems = lista.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        int fromIndex = page * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);
        
        List<Inventario> inventariosPaginados = lista.subList(fromIndex, toIndex);

        model.addAttribute("inventarios", inventariosPaginados);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("busquedaActual", busqueda);
        return "inventario/lista";
    }

    // --- 2. FORMULARIO DE ASIGNACIÓN (NUEVO) ---
    @GetMapping("/asignar")
    public String mostrarFormularioAsignar(Model model) {
        model.addAttribute("inventario", new Inventario());
        
        // Cargar Usuarios
        model.addAttribute("usuarios", usuarioRepository.findAll());
        
        // Cargar SOLO Equipos Disponibles (Estado ID = 1)
        // Esto evita que asignes un equipo que ya tiene otra persona
        model.addAttribute("equiposLibres", equipoRepository.findByEstadoId(1));
        
        return "inventario/formulario";
    }

    // --- 3. GUARDAR ASIGNACIÓN ---
    @PostMapping("/guardar")
    public String guardarAsignacion(@ModelAttribute Inventario inventario) {
        // 1. Establecemos la fecha de asignación actual automáticamente
        inventario.setFechaAsignacion(LocalDateTime.now());
        
        // 2. CAMBIO DE ESTADO DEL EQUIPO AUTOMÁTICO
        // Recuperamos el equipo seleccionado
        Equipo equipo = equipoRepository.findById(inventario.getEquipo().getId()).orElse(null);
        if (equipo != null) {
            // Buscamos el estado "Asignado" (ID 2 según tu script SQL)
            Estado estadoAsignado = estadoRepository.findById(2).orElse(null);
            if (estadoAsignado != null) {
                equipo.setEstado(estadoAsignado);
                equipoRepository.save(equipo); // Guardamos el equipo actualizado
            }
        }

        // 3. Guardamos el registro de inventario
        inventarioRepository.save(inventario);
        
        return "redirect:/inventario";
    }

    // --- 4. DEVOLVER EQUIPO (Lo implementaremos después, pero dejo el hueco) ---
    @GetMapping("/devolver/{id}")
    public String devolverEquipo(@PathVariable Integer id) {
        Inventario inv = inventarioRepository.findById(id).orElse(null);
        if (inv != null && inv.getFechaDevolucion() == null) {
            // 1. Poner fecha de devolución
            inv.setFechaDevolucion(LocalDateTime.now());
            inventarioRepository.save(inv);

            // 2. Liberar el equipo (Estado 1 = Disponible)
            Equipo equipo = inv.getEquipo();
            Estado estadoDisponible = estadoRepository.findById(1).orElse(null);
            equipo.setEstado(estadoDisponible);
            equipoRepository.save(equipo);
        }
        return "redirect:/inventario";
    }
}