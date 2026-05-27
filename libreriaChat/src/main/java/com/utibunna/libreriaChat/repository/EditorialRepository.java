package com.utibunna.libreriaChat.repository;

import com.utibunna.libreriaChat.model.Editorial;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EditorialRepository extends JpaRepository<Editorial, Long> {

    @Override
    @EntityGraph(attributePaths = "libros")
    Optional<Editorial> findById(Long id);

}
