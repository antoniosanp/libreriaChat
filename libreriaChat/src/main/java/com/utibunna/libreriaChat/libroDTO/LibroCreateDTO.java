package com.utibunna.libreriaChat.libroDTO;

import com.utibunna.libreriaChat.validation.ValidISBN;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Set;

public record LibroCreateDTO(

        @NotBlank(message = "loco, ponga algo")
        @Size(min = 2, max = 255, message = "debes cumplir con minimo 2 caracteres maximo tin")
        String titulo,

        @NotBlank(message = "el campo autor es obligatorio")
        String autor,

        @NotBlank
        @ValidISBN
        String isbn,

        @NotNull
        @PastOrPresent(message = "oe, ve el futuro o qué?")
        LocalDate fechaPublicacion,

        @NotNull
        @Positive(message = "regalado pues?")
        Double precio,

        @NotNull
        @Positive
        Long editorialId,

        @NotEmpty
        Set<Long> generosId
) {}
