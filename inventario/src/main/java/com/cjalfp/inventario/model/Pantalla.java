package com.cjalfp.inventario.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Pantallas")
@PrimaryKeyJoinColumn(name = "id_equipo")
@DiscriminatorValue("3")
public class Pantalla extends Equipo {

    @Column(name = "codigo_crija", unique = true, length = 50)
    private String codigoCrija;
}