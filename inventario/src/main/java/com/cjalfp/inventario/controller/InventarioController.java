package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Equipo;
import com.cjalfp.inventario.model.Estado;
import com.cjalfp.inventario.model.Inventario;
import com.cjalfp.inventario.repository.EquipoRepository;
import com.cjalfp.inventario.repository.EstadoRepository;
import com.cjalfp.inventario.repository.InventarioRepository;
import com.cjalfp.inventario.repository.UsuarioRepository;
import com.cjalfp.inventario.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final FileStorageService fileStorageService;

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
        
        List<Inventario> inventariosPaginados = (fromIndex < totalItems) 
            ? lista.subList(fromIndex, toIndex) 
            : new ArrayList<>();

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
    // Estados del sistema:
    // 1 = Disponible
    // 2 = Asignado
    // 3 = Averiado/Retirado
    @PostMapping("/guardar")
    public String guardarAsignacion(
            @ModelAttribute Inventario inventario,
            @RequestParam(value = "pdfFile", required = false) MultipartFile pdfFile,
            RedirectAttributes redirectAttributes) {
        
        try {
            // 1. Establecemos la fecha de asignación actual automáticamente
            inventario.setFechaAsignacion(LocalDateTime.now());
            
            // 2. Guardar PDF si se subió
            if (pdfFile != null && !pdfFile.isEmpty()) {
                String rutaPdf = fileStorageService.guardarPdf(pdfFile);
                inventario.setRutaPdf(rutaPdf);
            }
            
            // 3. CAMBIO DE ESTADO DEL EQUIPO AUTOMÁTICO
            // Recuperamos el equipo seleccionado
            Equipo equipo = equipoRepository.findById(inventario.getEquipo().getId())
                .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
            
            // Cambiar estado a "Asignado" (id=2)
            Estado estadoAsignado = estadoRepository.findById(2)
                .orElseThrow(() -> new IllegalStateException("Estado 'Asignado' no configurado en el sistema"));
            
            equipo.setEstado(estadoAsignado);
            equipoRepository.save(equipo);

            // 4. Guardamos el registro de inventario
            inventarioRepository.save(inventario);
            redirectAttributes.addFlashAttribute("mensaje", "✅ Equipo asignado correctamente");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "❌ " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Error al asignar equipo: " + e.getMessage());
        }
        
        return "redirect:/inventario";
    }

    // --- 4. DEVOLVER EQUIPO ---
    @GetMapping("/devolver/{id}")
    public String devolverEquipo(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Inventario inv = inventarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Asignación no encontrada"));
            
            if (inv.getFechaDevolucion() != null) {
                redirectAttributes.addFlashAttribute("error", "❌ Este equipo ya fue devuelto");
                return "redirect:/inventario";
            }
            
            // 1. Poner fecha de devolución
            inv.setFechaDevolucion(LocalDateTime.now());
            inventarioRepository.save(inv);

            // 2. Liberar el equipo - Cambiar estado a "Disponible" (id=1)
            Equipo equipo = inv.getEquipo();
            Estado estadoDisponible = estadoRepository.findById(1)
                .orElseThrow(() -> new IllegalStateException("Estado 'Disponible' no configurado en el sistema"));
            
            equipo.setEstado(estadoDisponible);
            equipoRepository.save(equipo);
            
            redirectAttributes.addFlashAttribute("mensaje", "✅ Equipo devuelto correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Error al devolver equipo: " + e.getMessage());
        }
        return "redirect:/inventario";
    }

    // --- 5. DESCARGAR PDF ---
    @GetMapping("/descargar-pdf/{id}")
    public ResponseEntity<Resource> descargarPdf(@PathVariable Integer id) {
        try {
            Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Asignación no encontrada"));
            
            if (inventario.getRutaPdf() == null || inventario.getRutaPdf().isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Path filePath = Paths.get(inventario.getRutaPdf()).normalize();
            
            // Validar que el archivo esté dentro del directorio permitido (prevenir path traversal)
            Path uploadDir = Paths.get("uploads/asignaciones/").toAbsolutePath().normalize();
            if (!filePath.toAbsolutePath().normalize().startsWith(uploadDir)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }
            
            Resource resource = new UrlResource(filePath.toUri());
            
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment; filename=\"asignacion_" + inventario.getId() + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
                
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}