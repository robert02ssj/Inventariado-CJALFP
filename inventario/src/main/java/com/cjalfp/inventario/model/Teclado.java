package com.cjalfp.inventario.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Teclados")
@PrimaryKeyJoinColumn(name = "id_equipo")
@DiscriminatorValue("5")
public class Teclado extends Equipo {
}