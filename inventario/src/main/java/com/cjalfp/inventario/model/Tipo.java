package com.cjalfp.inventario.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Tipo")
public class Tipo {
    @Id
    @Column(name = "id_tipo")
    private Integer id; // No es autoincremental en BD, lo definimos nosotros (1,2,3...)

    @Column(nullable = false, length = 50)
    private String nombre;
}