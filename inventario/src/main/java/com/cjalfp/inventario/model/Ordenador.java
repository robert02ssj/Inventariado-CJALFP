package com.cjalfp.inventario.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Ordenadores")
@PrimaryKeyJoinColumn(name = "id_equipo")
@DiscriminatorValue("1")
public class Ordenador extends Equipo {

    @Column(name = "codigo_crija", unique = true, length = 50)
    private String codigoCrija;

    @ManyToOne
    @JoinColumn(name = "id_linea")
    private Linea linea;

    @Column(name = "movilidad")
    private Boolean movilidad;
}