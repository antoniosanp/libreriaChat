package com.utibunna.libreriaChat.service;

import com.utibunna.libreriaChat.model.Mensaje;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class BotIAService {

    private static final Logger logger = LoggerFactory.getLogger(BotIAService.class);

    private final ChatClient chatClient;
    private final MensajeService mensajeService;
    private final LibroToolService libroToolService;

    public BotIAService(ChatClient.Builder chatClientBuilder,
                        MensajeService mensajeService,
                        LibroToolService libroToolService) {
        this.chatClient = chatClientBuilder.build();
        this.mensajeService = mensajeService;
        this.libroToolService = libroToolService;
    }

    public Mensaje generarRespuestaIA(String preguntaUsuario) {
        logger.info("Generando respuesta IA para mensaje: {}", preguntaUsuario);

        String historialMongo = mensajeService.obtenerUltimosMensajes()
                .stream()
                .map(m -> m.getRemitente() + ": " + m.getContenido())
                .collect(Collectors.joining("\n"));

        String prompt = """
                Eres el asistente de LibroTech.
                Usa el historial del chat como contexto cuando aporte valor.
                Si el usuario quiere consultar o agregar libros, usa las herramientas disponibles.
                Si no necesitas una herramienta, responde de forma directa y breve.

                Historial:
                %s

                Responde a la siguiente solicitud del usuario:
                %s
                """.formatted(historialMongo, preguntaUsuario);

        String respuestaTexto = chatClient.prompt()
                .tools(libroToolService)
                .user(prompt)
                .call()
                .content();

        logger.info("Respuesta IA generada correctamente");

        Mensaje mensajeBot = new Mensaje();
        mensajeBot.setRemitente("LibroBot IA");
        mensajeBot.setContenido(respuestaTexto);
        return mensajeService.guardarMensaje(mensajeBot);
    }

    public String verificarConexion() {
        logger.info("Verificando conexion con Google GenAI");
        String respuesta = chatClient.prompt()
                .user("Responde solo con la palabra OK")
                .call()
                .content();
        logger.info("Conexion con Google GenAI verificada. Respuesta: {}", respuesta);
        return respuesta;
    }
}
