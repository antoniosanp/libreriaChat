package com.utibunna.libreriaChat.controller;


import com.utibunna.libreriaChat.model.Mensaje;
import com.utibunna.libreriaChat.service.MensajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/mensajes")
@RequiredArgsConstructor
public class MensajeController {
    private final MensajeService mensajeService;

    public List<Mensaje> getMensajes(){
        return mensajeService.getLastMensajes();
    }


}
