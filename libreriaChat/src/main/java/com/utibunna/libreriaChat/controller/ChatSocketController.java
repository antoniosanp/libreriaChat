package com.utibunna.libreriaChat.controller;


import com.utibunna.libreriaChat.model.Mensaje;
import com.utibunna.libreriaChat.service.BotIAService;
import com.utibunna.libreriaChat.service.MensajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {

    private final MensajeService mensajeService;
    private final BotIAService botIAService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/enviar")
    @SendTo("/tema/mensajes")
    public Mensaje procesarMensajeUsuario(Mensaje mensajeRecibido){
        Mensaje mensajeGuardado = mensajeService.guardarMensaje(mensajeRecibido);

        new Thread(()->{
            Mensaje respuestaIA = botIAService.generarRespuestaIA(mensajeGuardado.getContenido());

            simpMessagingTemplate.convertAndSend("/tema/mensajes", respuestaIA);
        }).start();

        return mensajeGuardado;
    }
}
