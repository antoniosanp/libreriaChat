package com.utibunna.libreriaChat.config;

import com.utibunna.libreriaChat.repository.LibroRepository;
import com.utibunna.libreriaChat.repository.MensajeRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = LibroRepository.class)
@EnableMongoRepositories(basePackageClasses = MensajeRepository.class)
public class PersistenceConfig {
}
