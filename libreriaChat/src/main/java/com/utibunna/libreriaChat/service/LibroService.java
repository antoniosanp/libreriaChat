package com.utibunna.libreriaChat.service;

import com.utibunna.libreriaChat.libroDTO.LibroDTO;
import com.utibunna.libreriaChat.libroDTO.LibroPatchDTO;
import com.utibunna.libreriaChat.model.Libro;
import org.springframework.data.domain.Page;

public interface LibroService {

    Libro crearLibro(LibroDTO libroDTO);

    Page<Libro> obtenerTodos(int page);

    Libro obtenerPorId(Long id);

    Page<Libro> obtenerPorAutor(String autor, int page);

    Libro actualizarLibro(Long id, LibroDTO libroDTO);

    Libro actualizarParcialLibro(Long id, LibroPatchDTO libroPatchDTO);

    void eliminarLibro(Long id);

    void eliminarTodo();
}
