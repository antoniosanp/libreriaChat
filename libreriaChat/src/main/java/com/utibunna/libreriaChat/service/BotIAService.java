package com.utibunna.libreriaChat.service;

import com.utibunna.libreriaChat.model.Mensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;

import java.util.stream.Collectors;


public class BotIAService {


    private  final ChatClient chatClient;

    private  MensajeService mensajeService;

    public BotIAService(ChatClient.Builder chatClientBuilder, MensajeService mensajeService ){
        this.chatClient = chatClientBuilder.build();
        this.mensajeService = mensajeService;
    }

    public Mensaje generarRespuestaIA(String preguntaUsuario){

        String historiaMongo = mensajeService.getLastMensajes().stream()
                .map(m -> m.getRemitente() + ": " + m.getContenido())
                .collect(Collectors.joining("\n"));

        String prompt = "Eres el asistente de LibroTech. Usa este historial de chat como contexto "
                + historiaMongo + "\n\nResponde a: " + preguntaUsuario;

        String respuestaTexto = chatClient.prompt().user(prompt).call().content();

        Mensaje mensajeBot = new Mensaje();
        mensajeBot.setRemitente("LibroBot IA");
        mensajeBot.setContenido(respuestaTexto);
        return mensajeService.guardarMensaje(mensajeBot);


    }

}
