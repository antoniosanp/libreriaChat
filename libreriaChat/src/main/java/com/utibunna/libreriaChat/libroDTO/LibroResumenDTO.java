package com.utibunna.libreriaChat.libroDTO;

import java.time.LocalDate;

public record LibroResumenDTO(
        Long id,
        String titulo,
        LocalDate fechaPublicacion,
        Double precio,
        String editorialNombre,
        String pais

        ) {
}