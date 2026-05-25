package com.utibunna.libreriaChat.libroDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
public class LibroPatchDTO {

    @Size(min = 1, max = 150)
    private String titulo;

    @Size(min = 1, max = 100)
    private String autor;

    @Size(min = 1, max = 20)
    private String isbn;

    private LocalDate fechaPublicacion;

    @DecimalMin(value = "0.0", inclusive = false)
    private Double precio;

    private Long editorialId;

    private Set<Long> generoIds;
}
