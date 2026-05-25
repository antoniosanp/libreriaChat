package com.utibunna.libreriaChat.libroDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

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
    private LocalDate fechaPublicacion;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Double precio;

    @NotNull
    private Long editorialId;

    @NotEmpty
    private Set<Long> generoIds;
}
