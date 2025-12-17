package com.cjalfp.inventario.repository;

import com.cjalfp.inventario.model.Ordenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenadorRepository extends JpaRepository<Ordenador, Integer> { }
