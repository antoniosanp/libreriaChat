package com.utibunna.libreriaChat.mapper;


import com.utibunna.libreriaChat.libroDTO.LibroCreateDTO;
import com.utibunna.libreriaChat.libroDTO.LibroResponseDTO;
import com.utibunna.libreriaChat.model.Genero;
import com.utibunna.libreriaChat.model.Libro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LibroMapper {

    @Mapping(source = "editorial.nombre" , target = "nombreEditorial")
    @Mapping(source = "generos", target = "generos", qualifiedByName = "mapGenerosToNombres")
    LibroResponseDTO toResponseDTO(Libro libro);

    @Named("mapGenerosToNombres")
    default Set<String> mapGenerosToNombre(Set<Genero> generos){
        if (generos == null) return null;
        return generos.stream().map(Genero::getNombre).collect(Collectors.toSet());


    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disponible", constant = "true")
    @Mapping(target = "editorial", ignore = true)
    @Mapping(target = "generos", ignore = true)
    Libro toEntity(LibroCreateDTO dto);

}
