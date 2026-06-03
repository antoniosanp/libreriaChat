package com.utibunna.libreriaChat.libroDTO;

import com.utibunna.libreriaChat.validation.ValidISBN;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Set;

public record LibroCreateDTO(

        @NotBlank(message = "no puede tar vacio")
        @Size(min = 2, max = 255, message = "ponga algo, pero tampoco tanto")
        String titulo,
        @NotBlank(message = "ponga algo")
        String autor,

        @NotBlank(message = "ponga algo en el isbn")
        @ValidISBN
        String isbn,

        @NotNull(message = "ponga algo en la fecha")
        @PastOrPresent(message = "si pa, cual es esa?")
        LocalDate fechaPublicacion,

        @NotNull(message = "gratis pues?")
        @PositiveOrZero(message = "que valga alguito")
        Double precio,

        @NotNull(message = "que haiga editorial")
        Long editorialId,

        @NotEmpty(message = "pa, ponele género a la vuelta")
        Set<Long> generosId
) {}
