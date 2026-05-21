package com.utibunna.libreriaChat.service;

import com.utibunna.libreriaChat.model.Mensaje;
import java.util.List;

public interface MensajeService {

    Mensaje guardarMensaje(Mensaje mensaje);

    List<Mensaje> obtenerHistorial();

    List<Mensaje> obtenerUltimosMensajes();
}
