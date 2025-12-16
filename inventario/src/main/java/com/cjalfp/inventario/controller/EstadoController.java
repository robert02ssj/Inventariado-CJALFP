package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Estado;
import com.cjalfp.inventario.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoRepository estadoRepository;

    @GetMapping
    public List<Estado> getAll() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> getById(@PathVariable Integer id) {
        return estadoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Estado create(@RequestBody Estado estado) {
        return estadoRepository.save(estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> update(@PathVariable Integer id, @RequestBody Estado estadoDetails) {
        return estadoRepository.findById(id)
                .map(estado -> {
                    estado.setNombreEstado(estadoDetails.getNombreEstado());
                    return ResponseEntity.ok(estadoRepository.save(estado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (estadoRepository.existsById(id)) {
            estadoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
