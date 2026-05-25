package com.utibunna.libreriaChat.model;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "libros")
@SQLRestriction("disponible = true")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String autor;

    @Column(unique = true, length = 20)
    private String isbn;

    @Column(nullable = false)
    private LocalDate fechaPublicacion;

    private Double precio;


    //====SOFT DELETE====

    @Column(nullable = false)
    private boolean disponible = true;


    // === RELACIÓN MANY-TO-ONE: Cada libro pertenece a UNA editorial ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editorial_id", nullable = false)
    private Editorial editorial;

    // === RELACIÓN MANY-TO-MANY: Un libro tiene MUCHOS géneros ===
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "libros_generos",                                // Nombre explícito de tabla intermedia
            joinColumns = @JoinColumn(name = "libro_id"),           // FK hacia Libro
            inverseJoinColumns = @JoinColumn(name = "genero_id")    // FK hacia Genero
    )

    private Set<Genero> generos = new HashSet<>();

    public Libro() {
    }

    public Libro(String titulo, String autor, String isbn, LocalDate fechaPublicacion, Double precio, Editorial editorial) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.fechaPublicacion = fechaPublicacion;
        this.precio = precio;
        this.editorial = editorial;

    }

    //-------------------------------
    public void softDelete() {
        this.disponible = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public Set<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(Set<Genero> generos) {
        this.generos = generos;
    }

}
