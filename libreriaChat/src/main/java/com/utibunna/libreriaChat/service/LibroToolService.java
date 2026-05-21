package com.utibunna.libreriaChat.service;

import com.utibunna.libreriaChat.libroDTO.LibroDTO;
import com.utibunna.libreriaChat.model.Libro;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class LibroToolService {

    private final LibroService libroService;

    public LibroToolService(LibroService libroService) {
        this.libroService = libroService;
    }

    @Tool(description = "Busca libros por autor y devuelve un resumen legible.")
    public String buscarLibrosPorAutor(@ToolParam(description = "Nombre del autor a buscar") String autor) {
        List<Libro> libros = libroService.obtenerPorAutor(autor, 0).getContent();
        if (libros.isEmpty()) {
            return "No se encontraron libros para el autor: " + autor;
        }
        return libros.stream()
                .map(this::resumenLibro)
                .collect(Collectors.joining("\n"));
    }

    @Tool(description = "Lista los libros registrados en el sistema.")
    public String listarLibros() {
        List<Libro> libros = libroService.obtenerTodos(0).getContent();
        if (libros.isEmpty()) {
            return "No hay libros registrados.";
        }
        return libros.stream()
                .map(this::resumenLibro)
                .collect(Collectors.joining("\n"));
    }

    @Tool(description = "Agrega un libro nuevo al catalogo y devuelve un resumen del registro creado.")
    public String agregarLibro(
            @ToolParam(description = "Titulo del libro") String titulo,
            @ToolParam(description = "Autor del libro") String autor,
            @ToolParam(description = "ISBN unico del libro") String isbn,
            @ToolParam(description = "Anio de publicacion del libro") int anioPublicacion) {
        LibroDTO libroDTO = new LibroDTO();
        libroDTO.setTitulo(titulo);
        libroDTO.setAutor(autor);
        libroDTO.setIsbn(isbn);
        libroDTO.setAnioPublicacion(anioPublicacion);

        Libro libro = libroService.crearLibro(libroDTO);
        return "Libro agregado correctamente: " + resumenLibro(libro);
    }

    private String resumenLibro(Libro libro) {
        return "ID " + libro.getId()
                + " | Titulo: " + libro.getTitulo()
                + " | Autor: " + libro.getAutor()
                + " | ISBN: " + libro.getIsbn()
                + " | Anio: " + libro.getAnioPublicacion();
    }
}
