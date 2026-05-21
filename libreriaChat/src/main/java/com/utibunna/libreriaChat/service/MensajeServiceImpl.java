package com.utibunna.libreriaChat.service;

import com.utibunna.libreriaChat.model.Mensaje;
import com.utibunna.libreriaChat.repository.MensajeRepository;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MensajeServiceImpl implements MensajeService {

    private final MensajeRepository mensajeRepository;

    public MensajeServiceImpl(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

    @Override
    public Mensaje guardarMensaje(Mensaje mensaje) {
        if (mensaje.getFechaEnvio() == null) {
            mensaje.setFechaEnvio(LocalDateTime.now());
        }
        return mensajeRepository.save(mensaje);
    }

    @Override
    public List<Mensaje> obtenerHistorial() {
        return mensajeRepository.findAllByOrderByFechaEnvioAsc();
    }

    @Override
    public List<Mensaje> obtenerUltimosMensajes() {
        return mensajeRepository.findTop10ByOrderByFechaEnvioDesc()
                .stream()
                .sorted(Comparator.comparing(Mensaje::getFechaEnvio))
                .toList();
    }
}
