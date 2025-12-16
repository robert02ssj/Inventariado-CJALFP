package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Modelo;
import com.cjalfp.inventario.repository.ModeloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modelos")
@RequiredArgsConstructor
public class ModeloController {

    private final ModeloRepository modeloRepository;

    @GetMapping
    public List<Modelo> getAll() {
        return modeloRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modelo> getById(@PathVariable Integer id) {
        return modeloRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Modelo create(@RequestBody Modelo modelo) {
        return modeloRepository.save(modelo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Modelo> update(@PathVariable Integer id, @RequestBody Modelo modeloDetails) {
        return modeloRepository.findById(id)
                .map(modelo -> {
                    modelo.setNombre(modeloDetails.getNombre());
                    modelo.setMarca(modeloDetails.getMarca());
                    return ResponseEntity.ok(modeloRepository.save(modelo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (modeloRepository.existsById(id)) {
            modeloRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
