package com.utibunna.libreriaChat.repository;

import com.utibunna.libreriaChat.model.Mensaje;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MensajeRepository extends MongoRepository<Mensaje, String> {
}
