package com.cjalfp.inventario.repository;

import com.cjalfp.inventario.model.DockingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DockingStationRepository extends JpaRepository<DockingStation, Integer> { }
