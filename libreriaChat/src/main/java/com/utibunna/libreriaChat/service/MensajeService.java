package com.utibunna.libreriaChat.service;

import com.utibunna.libreriaChat.model.Mensaje;
import com.utibunna.libreriaChat.repository.MensajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MensajeService {
    private  final MensajeRepository mensajeRepository;

    public List<Mensaje> getLastMensajes(){
        List<Mensaje> mensajes = mensajeRepository.findAll();
        return mensajes;
    }

    public Mensaje guardarMensaje(Mensaje mensaje){
        return mensajeRepository.insert(mensaje);
    }

}
