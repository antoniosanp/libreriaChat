package com.utibunna.libreriaChat.repository;

import com.utibunna.libreriaChat.model.Mensaje;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends MongoRepository<Mensaje, String> {
}
