package com.utibunna.libreriaChat.libroDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroDTO {

    @NotBlank
    @Size(max = 150)
    private String titulo;

    @NotBlank
    @Size(max = 100)
    private String autor;

    @NotBlank
    @Size(max = 20)
    private String isbn;

    @NotNull
    @Min(-3000)
    @Max(3000)
    private Integer anioPublicacion;
}
