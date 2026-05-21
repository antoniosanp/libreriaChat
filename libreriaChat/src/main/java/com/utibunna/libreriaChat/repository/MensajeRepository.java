package com.utibunna.libreriaChat.repository;

import com.utibunna.libreriaChat.model.Mensaje;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MensajeRepository extends MongoRepository<Mensaje, String> {

    List<Mensaje> findTop10ByOrderByFechaEnvioDesc();

    List<Mensaje> findAllByOrderByFechaEnvioAsc();
}
