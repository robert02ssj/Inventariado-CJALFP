package com.cjalfp.inventario.repository;

import com.cjalfp.inventario.model.Raton;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatonRepository extends JpaRepository<Raton, Integer> { }
