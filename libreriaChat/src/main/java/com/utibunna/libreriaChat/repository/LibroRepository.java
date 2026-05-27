package com.utibunna.libreriaChat.repository;

import com.utibunna.libreriaChat.libroDTO.LibroResumenDTO;
import com.utibunna.libreriaChat.model.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Page<Libro> findByAutorContainingIgnoreCase(String autor, Pageable pageable);

    @EntityGraph(attributePaths = {"editorial", "generos"})
    List<Libro> findByDisponibleTrue();

    @Query("""
    SELECT new com.utibunna.libreriaChat.libroDTO.LibroResumenDTO(
    l.id,
    l.titulo,
    l.fechaPublicacion,
    l.precio,
    l.editorial.nombre,
    l.editorial.pais
)
    FROM Libro l
    ORDER BY l.fechaPublicacion DESC


""") Slice<LibroResumenDTO> findAllLibroResumenes(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"editorial", "generos"})
    Optional<Libro> findById(Long id);

    @EntityGraph(attributePaths = {"editorial", "generos"})
    @Query("SELECT l FROM Libro l ORDER BY l.fechaPublicacion DESC")
    List<Libro> findAllWithRelations();

    @Query("""
    SELECT new com.utibunna.libreriaChat.libroDTO.LibroResumenDTO(
    l.id,
    l.titulo,
    l.fechaPublicacion,
    l.precio,
    l.editorial.nombre,
    l.editorial.pais
)
    FROM Libro l
    WHERE l.id = ?1


""") LibroResumenDTO findResumenById(long id);


    @Override
    @EntityGraph(attributePaths = "editorial")
    List<Libro> findAll();

}
