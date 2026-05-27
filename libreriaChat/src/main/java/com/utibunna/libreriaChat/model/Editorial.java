package com.utibunna.libreriaChat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "editoriales")
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    // Nuevo campo: país de la editorial
    @Column(nullable = false)
    private String pais;

    private Integer fundadoEn;


    // Lado inverso de la relación: una Editorial tiene muchos Libros
    @JsonIgnore
    @OneToMany(mappedBy = "editorial")
    private List<Libro> libros = new ArrayList<>();

    // === Constructores ===
    public Editorial() {}

    public Editorial(String nombre, String direccion, String pais, Integer fundadoEn) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.pais = pais;
        this.fundadoEn = fundadoEn;
    }
}