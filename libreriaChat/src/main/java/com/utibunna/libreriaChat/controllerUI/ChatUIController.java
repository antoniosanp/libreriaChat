package com.utibunna.libreriaChat.controllerUI;

import com.utibunna.libreriaChat.service.MensajeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui/chat")
public class ChatUIController {

    private final MensajeService mensajeService;

    public ChatUIController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    @GetMapping
    public String verChat(Model model) {
        model.addAttribute("mensajes", mensajeService.obtenerHistorial());
        return "chat/sala";
    }
}
