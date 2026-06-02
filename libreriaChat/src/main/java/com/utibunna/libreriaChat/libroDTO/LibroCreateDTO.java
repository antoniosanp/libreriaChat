package com.utibunna.libreriaChat.libroDTO;

import java.time.LocalDate;
import java.util.Set;

public record LibroCreateDTO(
        String titulo,
        String autor,
        String isbn,
        LocalDate fechaPublicacion,
        Double precio,
        Long editorialId,
        Set<Long> generosId
) {}
