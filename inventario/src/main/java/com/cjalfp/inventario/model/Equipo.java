package com.cjalfp.inventario.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Equipos")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "id_tipo", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    private Integer id;

    // Relación solo lectura para ver el tipo
    @ManyToOne
    @JoinColumn(name = "id_tipo", insertable = false, updatable = false)
    private Tipo tipoObjeto;

    @ManyToOne
    @JoinColumn(name = "id_modelo", nullable = false)
    private Modelo modelo;

    @ManyToOne
    @JoinColumn(name = "id_estado", nullable = false)
    private Estado estado;

    @Column(name = "numero_serie", unique = true, length = 100)
    private String numeroSerie;

    @Column(columnDefinition = "TEXT")
    private String observaciones;
}