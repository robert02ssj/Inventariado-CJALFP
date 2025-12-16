package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Inventario;
import com.cjalfp.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioRepository inventarioRepository;

    @GetMapping
    public List<Inventario> getAll() {
        return inventarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> getById(@PathVariable Integer id) {
        return inventarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Inventario create(@RequestBody Inventario inventario) {
        // La fecha de asignación se pone automática en el @PrePersist de la entidad si viene nula
        return inventarioRepository.save(inventario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventario> update(@PathVariable Integer id, @RequestBody Inventario inventarioDetails) {
        return inventarioRepository.findById(id)
                .map(inventario -> {
                    inventario.setUsuario(inventarioDetails.getUsuario());
                    inventario.setEquipo(inventarioDetails.getEquipo());
                    inventario.setFechaDevolucion(inventarioDetails.getFechaDevolucion());
                    // No solemos actualizar fechaAsignacion, pero podrías añadirlo si quieres
                    return ResponseEntity.ok(inventarioRepository.save(inventario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (inventarioRepository.existsById(id)) {
            inventarioRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
