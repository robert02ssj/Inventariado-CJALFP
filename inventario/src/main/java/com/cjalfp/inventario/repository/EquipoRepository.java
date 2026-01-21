package com.cjalfp.inventario.repository;

import com.cjalfp.inventario.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipoRepository extends JpaRepository<Equipo, Integer> {
    // NUEVO MÉTODO: Busca equipos por el ID de su estado (1=Disponible)
    List<Equipo> findByEstadoId(Integer estadoId);
    
    // Contar equipos por tipo (para estadísticas)
    @Query("SELECT COUNT(e) FROM Equipo e WHERE e.tipoObjeto.id = :tipoId")
    long countByTipoObjetoId(@Param("tipoId") Integer tipoId);
}