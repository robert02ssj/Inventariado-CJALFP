package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Equipo;
import com.cjalfp.inventario.repository.EquipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/equipos")
@RequiredArgsConstructor
public class EquipoController {

    private final EquipoRepository equipoRepository;

    @GetMapping
    public String listarEquipos(
            @RequestParam(required = false) Integer tipo,
            @RequestParam(required = false) String busqueda,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        int pageSize = 10;
        
        // 1. Obtenemos todos los equipos (en el futuro podemos optimizar esto en BD)
        List<Equipo> equipos = equipoRepository.findAll();

        // 2. Filtro por TIPO (Las pestañas de tu dibujo)
        if (tipo != null) {
            equipos = equipos.stream()
                    .filter(e -> e.getTipoObjeto() != null && e.getTipoObjeto().getId().equals(tipo))
                    .collect(Collectors.toList());
        }

        // 3. Filtro por BÚSQUEDA (La barra central)
        // Busca coincidencias en Serie, Modelo o Marca
        if (busqueda != null && !busqueda.isEmpty()) {
            String term = busqueda.toLowerCase();
            equipos = equipos.stream()
                    .filter(e -> (e.getNumeroSerie() != null && e.getNumeroSerie().toLowerCase().contains(term)) ||
                                 (e.getModelo().getNombre().toLowerCase().contains(term)) ||
                                 (e.getModelo().getMarca().getNombreFabricante().toLowerCase().contains(term)))
                    .collect(Collectors.toList());
        }

        // 4. Aplicar paginación manual
        int totalItems = equipos.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        int fromIndex = page * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);
        
        List<Equipo> equiposPaginados = equipos.subList(fromIndex, toIndex);

        // 5. Pasamos los datos a la vista
        model.addAttribute("equipos", equiposPaginados);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("tipoActivo", tipo); // Para saber qué pestaña pintar de verde
        model.addAttribute("busquedaActual", busqueda); // Para mantener el texto en el input

        return "equipos/lista"; // Renderiza templates/equipos/lista.html
    }
}