package com.cjalfp.inventario.repository;

import com.cjalfp.inventario.model.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefonoRepository extends JpaRepository<Telefono, Integer> { }
