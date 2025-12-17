package com.cjalfp.inventario.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Ratones")
@PrimaryKeyJoinColumn(name = "id_equipo")
@DiscriminatorValue("4")
public class Raton extends Equipo {
}