package com.utibunna.libreriaChat.libroDTO;

import java.time.LocalDate;
import java.util.Set;

public record LibroResponseDTO(
        Long id,
        String titulo,
        String autor,
        LocalDate fechaPublicacion,
        Double precio,
        String nombreEditorial,
        Set<String> generos
) {
}
