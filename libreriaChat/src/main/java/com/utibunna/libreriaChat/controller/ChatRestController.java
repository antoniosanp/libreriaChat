package com.utibunna.libreriaChat.controller;

import com.utibunna.libreriaChat.model.Mensaje;
import com.utibunna.libreriaChat.service.BotIAService;
import com.utibunna.libreriaChat.service.MensajeService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatRestController {

    private final MensajeService mensajeService;
    private final BotIAService botIAService;

    public ChatRestController(MensajeService mensajeService, BotIAService botIAService) {
        this.mensajeService = mensajeService;
        this.botIAService = botIAService;
    }

    @GetMapping("/mensajes")
    public List<Mensaje> obtenerMensajes() {
        return mensajeService.obtenerHistorial();
    }

    @GetMapping("/ia/status")
    public Map<String, String> verificarIA() {
        String respuesta = botIAService.verificarConexion();
        return Map.of(
                "status", "ok",
                "provider", "google-genai",
                "model", "gemini-2.0-flash",
                "respuesta", respuesta
        );
    }
}
