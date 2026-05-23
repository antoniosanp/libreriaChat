package com.utibunna.libreriaChat.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@NoArgsConstructor
public class Mensaje {

    @Id
    private String id;
    private String remitente;
    private String contenido;
    private LocalDateTime fechaEnvio = LocalDateTime.now();

}
