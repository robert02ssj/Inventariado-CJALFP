package com.cjalfp.inventario.repository;

import com.cjalfp.inventario.model.Teclado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TecladoRepository extends JpaRepository<Teclado, Integer> { }
