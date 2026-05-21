package com.utibunna.libreriaChat.controller;

import com.utibunna.libreriaChat.model.Mensaje;
import com.utibunna.libreriaChat.service.BotIAService;
import com.utibunna.libreriaChat.service.MensajeService;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatSocketController {

    private static final Logger logger = LoggerFactory.getLogger(ChatSocketController.class);

    private final MensajeService mensajeService;
    private final BotIAService botIAService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatSocketController(MensajeService mensajeService,
                                BotIAService botIAService,
                                SimpMessagingTemplate messagingTemplate) {
        this.mensajeService = mensajeService;
        this.botIAService = botIAService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/enviar")
    @SendTo("/tema/mensajes")
    public Mensaje procesarMensajeUsuario(Mensaje mensajeRecibido) {
        Mensaje mensajeGuardado = mensajeService.guardarMensaje(mensajeRecibido);

        CompletableFuture.runAsync(() -> {
            try {
                Mensaje respuestaIA = botIAService.generarRespuestaIA(mensajeGuardado.getContenido());
                messagingTemplate.convertAndSend("/tema/mensajes", respuestaIA);
            } catch (Exception ex) {
                logger.error("Error generando respuesta de IA", ex);

                Mensaje mensajeError = new Mensaje();
                mensajeError.setRemitente("Sistema");
                mensajeError.setContenido("La IA no pudo responder: " + ex.getMessage());
                Mensaje mensajePersistido = mensajeService.guardarMensaje(mensajeError);
                messagingTemplate.convertAndSend("/tema/mensajes", mensajePersistido);
            }
        });

        return mensajeGuardado;
    }
}
