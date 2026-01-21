package com.cjalfp.inventario.repository;

import com.cjalfp.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    
    // Obtener todas las asignaciones activas (no devueltas)
    @Query("SELECT i FROM Inventario i WHERE i.fechaDevolucion IS NULL ORDER BY i.usuario.nombre, i.usuario.apellidos")
    List<Inventario> findAsignacionesActivas();
    
    // Obtener historial completo de un equipo por número de serie
    @Query("SELECT i FROM Inventario i WHERE i.equipo.numeroSerie = :serie ORDER BY i.fechaAsignacion DESC")
    List<Inventario> findHistorialByEquipo(@Param("serie") String serie);
    
    // Obtener asignaciones anteriores a una fecha (para detectar asignaciones antiguas)
    @Query("SELECT i FROM Inventario i WHERE i.fechaDevolucion IS NULL AND i.fechaAsignacion < :fecha ORDER BY i.fechaAsignacion")
    List<Inventario> findAsignacionesAnterioresA(@Param("fecha") LocalDateTime fecha);
    
    // Buscar asignaciones activas por nombre/apellidos de usuario o LDAP
    @Query("SELECT i FROM Inventario i WHERE i.fechaDevolucion IS NULL AND " +
           "(LOWER(i.usuario.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
           "LOWER(i.usuario.apellidos) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
           "LOWER(i.usuario.ldap) LIKE LOWER(CONCAT('%', :busqueda, '%'))) " +
           "ORDER BY i.usuario.nombre, i.usuario.apellidos")
    List<Inventario> findAsignacionesActivasByUsuario(@Param("busqueda") String busqueda);
}
