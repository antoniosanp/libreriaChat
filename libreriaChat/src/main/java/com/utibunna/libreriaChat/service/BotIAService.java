package com.utibunna.libreriaChat.service;

import com.utibunna.libreriaChat.model.Genero;
import com.utibunna.libreriaChat.model.Libro;
import com.utibunna.libreriaChat.model.Mensaje;
import com.utibunna.libreriaChat.repository.GeneroRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BotIAService {

    private static final List<String> PALABRAS_CLAVE_CATALOGO =
            List.of("libro", "autor", "catalogo", "género", "genero", "géneros", "generos", "editorial");

    private  final ChatClient chatClient;

    private  final MensajeService mensajeService;

    private final LibroService libroService;
    private final GeneroRepository generoRepository;

    public BotIAService(
            ChatClient.Builder chatClientBuilder,
            MensajeService mensajeService,
            LibroService libroService,
            GeneroRepository generoRepository
    ){
        this.chatClient = chatClientBuilder.build();
        this.mensajeService = mensajeService;
        this.libroService = libroService;
        this.generoRepository = generoRepository;
    }

    public Mensaje generarRespuestaIA(String preguntaUsuario){

        String historiaMongo = mensajeService.getLastMensajes().stream()
                .map(m -> m.getRemitente() + ": " + m.getContenido())
                .collect(Collectors.joining("\n"));

        String contextoCatalogo = construirContextoCatalogo(preguntaUsuario);

        String prompt = "Eres el asistente de LibroTech. Usa este historial " +
                "de chat como contexto, solo puedes responder preguntas relacionadas " +
                "con libros del catálogo, no tienes conocimiento que no esté relacionado " +
                "con libros o autores "
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

        List<Genero> generos = generoRepository.findAll();
        List<Libro> catalogo = libroService.obtenerCatalogo();

        String generosTexto = generos.isEmpty()
                ? "no hay generos registrados."
                : generos.stream()
                .map(genero -> genero.getNombre() + (genero.getDescripcion() != null ? ": " + genero.getDescripcion() : ""))
                .collect(Collectors.joining("\n"));

        if (catalogo.isEmpty()) {
            return "\n\nGeneros disponibles en la base de datos:\n"
                    + generosTexto
                    + "\n\nCatalogo disponible en la base de datos: no hay libros registrados.";
        }

        String catalogoTexto = catalogo.stream()
                .map(libro -> String.format(
                        "ID: %d, Titulo: %s, Autor: %s, ISBN: %s, Fecha publicacion: %s, Precio: %.2f, Editorial: %s, Generos: %s",
                        libro.getId(),
                        libro.getTitulo(),
                        libro.getAutor(),
                        libro.getIsbn(),
                        libro.getFechaPublicacion(),
                        libro.getPrecio(),
                        libro.getEditorial().getNombre(),
                        libro.getGeneros().stream()
                                .map(Genero::getNombre)
                                .sorted()
                                .collect(Collectors.joining(", "))))
                .collect(Collectors.joining("\n"));

        return "\n\nGeneros disponibles en la base de datos:\n"
                + generosTexto
                + "\n\nCatalogo disponible en la base de datos:\n"
                + catalogoTexto;
    }

    private boolean debeConsultarCatalogo(String preguntaUsuario) {
        String textoNormalizado = Normalizer.normalize(preguntaUsuario, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();

        return PALABRAS_CLAVE_CATALOGO.stream().anyMatch(textoNormalizado::contains);
    }

}
