package com.cjalfp.inventario.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Telefonos")
@PrimaryKeyJoinColumn(name = "id_equipo")
@DiscriminatorValue("2")
public class Telefono extends Equipo {

    @ManyToOne
    @JoinColumn(name = "id_linea")
    private Linea linea;

    private Boolean movilidad;
}