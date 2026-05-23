package com.utibunna.libreriaChat.controllerUI;

import com.utibunna.libreriaChat.controller.MensajeController;
import com.utibunna.libreriaChat.model.Mensaje;
import com.utibunna.libreriaChat.service.MensajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatUIController {

    private final MensajeService mensajeService;

    @GetMapping
    public String showChat(Model model){
        List<Mensaje> historial = mensajeService.getLastMensajes();

        model.addAttribute(historial);

        return "chat/sala";
    }
}
