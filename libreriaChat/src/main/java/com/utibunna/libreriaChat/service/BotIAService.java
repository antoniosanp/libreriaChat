package com.utibunna.libreriaChat.service;

import com.utibunna.libreriaChat.model.Libro;
import com.utibunna.libreriaChat.model.Mensaje;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BotIAService {

    private static final List<String> PALABRAS_CLAVE_CATALOGO = List.of("libro", "autor", "catalogo");

    private  final ChatClient chatClient;

    private  final MensajeService mensajeService;

    private final LibroService libroService;

    public BotIAService(ChatClient.Builder chatClientBuilder, MensajeService mensajeService, LibroService libroService ){
        this.chatClient = chatClientBuilder.build();
        this.mensajeService = mensajeService;
        this.libroService = libroService;
    }

    public Mensaje generarRespuestaIA(String preguntaUsuario){

        String historiaMongo = mensajeService.getLastMensajes().stream()
                .map(m -> m.getRemitente() + ": " + m.getContenido())
                .collect(Collectors.joining("\n"));

        String contextoCatalogo = construirContextoCatalogo(preguntaUsuario);

        String prompt = "Eres el asistente de LibroTech. Usa este historial de chat como contexto "
                + historiaMongo
                + contextoCatalogo
                + "\n\nResponde a: " + preguntaUsuario;

        String respuestaTexto = chatClient.prompt().user(prompt).call().content();

        Mensaje mensajeBot = new Mensaje();
        mensajeBot.setRemitente("LibroBot IA");
        mensajeBot.setContenido(respuestaTexto);
        return mensajeService.guardarMensaje(mensajeBot);


    }

    private String construirContextoCatalogo(String preguntaUsuario) {
        if (!debeConsultarCatalogo(preguntaUsuario)) {
            return "";
        }

        List<Libro> catalogo = libroService.obtenerCatalogo();
        if (catalogo.isEmpty()) {
            return "\n\nCatalogo disponible en la base de datos: no hay libros registrados.";
        }

        String catalogoTexto = catalogo.stream()
                .map(libro -> String.format(
                        "ID: %d, Titulo: %s, Autor: %s, ISBN: %s, Anio: %d",
                        libro.getId(),
                        libro.getTitulo(),
                        libro.getAutor(),
                        libro.getIsbn(),
                        libro.getAnioPublicacion()))
                .collect(Collectors.joining("\n"));

        return "\n\nCatalogo disponible en la base de datos:\n" + catalogoTexto;
    }

    private boolean debeConsultarCatalogo(String preguntaUsuario) {
        String textoNormalizado = Normalizer.normalize(preguntaUsuario, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();

        return PALABRAS_CLAVE_CATALOGO.stream().anyMatch(textoNormalizado::contains);
    }

}
