package com.utibunna.libreriaChat.service;

import com.utibunna.libreriaChat.libroDTO.*;
import com.utibunna.libreriaChat.model.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public interface LibroService {


    LibroResponseDTO crearLibro(LibroCreateDTO dto);

    Page<Libro> obtenerTodos(int page);

    LibroResponseDTO obtenerPorId(Long id);

    Page<Libro> obtenerPorAutor(String autor, int page);

    List<Libro> obtenerCatalogo();

    Libro actualizarLibro(Long id, LibroDTO libroDTO);

    Libro actualizarParcialLibro(Long id, LibroPatchDTO libroPatchDTO);

    void descatalogarLibro(Long id);

    void eliminarTodo();


    //slice, dto projection &  entiryGraph------------------

    Slice<LibroResumenDTO> getCatalogo(int page);

    Libro getLibroWithRelations(Long id);

    LibroResumenDTO getResumenById(Long id);

    List<Libro> getAllEntity();


}
