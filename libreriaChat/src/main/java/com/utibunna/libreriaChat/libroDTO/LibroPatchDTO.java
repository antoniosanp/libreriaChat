package com.utibunna.libreriaChat.libroDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LibroPatchDTO {

    @Size(min = 1, max = 150)
    private String titulo;

    @Size(min = 1, max = 100)
    private String autor;

    @Size(min = 1, max = 20)
    private String isbn;

    @Min(-3000)
    @Max(3000)
    private Integer anioPublicacion;
}
