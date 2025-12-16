package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Tipo;
import com.cjalfp.inventario.repository.TipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos")
@RequiredArgsConstructor
public class TipoController {

    private final TipoRepository tipoRepository;

    @GetMapping
    public List<Tipo> getAll() {
        return tipoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tipo> getById(@PathVariable Integer id) {
        return tipoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tipo create(@RequestBody Tipo tipo) {
        return tipoRepository.save(tipo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tipo> update(@PathVariable Integer id, @RequestBody Tipo tipoDetails) {
        return tipoRepository.findById(id)
                .map(tipo -> {
                    tipo.setNombre(tipoDetails.getNombre());
                    return ResponseEntity.ok(tipoRepository.save(tipo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (tipoRepository.existsById(id)) {
            tipoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
