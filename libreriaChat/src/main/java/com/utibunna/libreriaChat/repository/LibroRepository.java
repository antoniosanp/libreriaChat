package com.utibunna.libreriaChat.repository;

import com.utibunna.libreriaChat.model.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Page<Libro> findByAutorContainingIgnoreCase(String autor, Pageable pageable);
}
