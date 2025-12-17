package com.cjalfp.inventario.repository;

import com.cjalfp.inventario.model.Pantalla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PantallaRepository extends JpaRepository<Pantalla, Integer> { }
