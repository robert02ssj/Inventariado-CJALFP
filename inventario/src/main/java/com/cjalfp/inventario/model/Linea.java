package com.cjalfp.inventario.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Linea")
public class Linea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_linea")
    private Integer id;

    @Column(name = "numero_corto", length = 20)
    private String numeroCorto;

    @Column(name = "numero_largo", length = 20)
    private String numeroLargo;

    @Column(length = 20)
    private String puk;

    @Column(name = "tiene_datos")
    private Boolean tieneDatos;
}