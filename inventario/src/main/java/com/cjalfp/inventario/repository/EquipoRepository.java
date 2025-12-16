package com.cjalfp.inventario.repository;

import com.cjalfp.inventario.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {
    // Si necesitas buscar por número de serie en el futuro, solo descomenta esto:
    // java.util.Optional<Equipo> findBySnEmei(String snEmei);
}
