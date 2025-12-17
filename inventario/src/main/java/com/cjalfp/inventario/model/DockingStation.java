package com.cjalfp.inventario.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "DockingStations")
@PrimaryKeyJoinColumn(name = "id_equipo")
@DiscriminatorValue("6")
public class DockingStation extends Equipo {

    @Column(name = "mac_address", unique = true, length = 50)
    private String macAddress;
}