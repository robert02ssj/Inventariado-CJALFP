package com.cjalfp.inventario.controller;

import com.cjalfp.inventario.model.Equipo;
import com.cjalfp.inventario.repository.EquipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
@RequiredArgsConstructor
public class EquipoController {

    private final EquipoRepository equipoRepository;

    @GetMapping
    public List<Equipo> getAll() {
        return equipoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> getById(@PathVariable Integer id) {
        return equipoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Equipo create(@RequestBody Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> update(@PathVariable Integer id, @RequestBody Equipo equipoDetails) {
        return equipoRepository.findById(id)
                .map(equipo -> {
                    equipo.setSnEmei(equipoDetails.getSnEmei());
                    equipo.setLineaCrija(equipoDetails.getLineaCrija());
                    equipo.setModelo(equipoDetails.getModelo());
                    equipo.setTipo(equipoDetails.getTipo());
                    equipo.setEstado(equipoDetails.getEstado());
                    return ResponseEntity.ok(equipoRepository.save(equipo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (equipoRepository.existsById(id)) {
            equipoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
