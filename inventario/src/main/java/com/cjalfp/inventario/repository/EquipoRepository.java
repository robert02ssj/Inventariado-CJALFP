package com.cjalfp.inventario.repository;

import com.cjalfp.inventario.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EquipoRepository extends JpaRepository<Equipo, Integer> {
    // NUEVO MÉTODO: Busca equipos por el ID de su estado (1=Disponible)
    List<Equipo> findByEstadoId(Integer estadoId);
}