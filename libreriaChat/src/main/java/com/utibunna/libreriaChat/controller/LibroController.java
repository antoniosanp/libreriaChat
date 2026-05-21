package com.utibunna.libreriaChat.controller;

import com.utibunna.libreriaChat.libroDTO.LibroDTO;
import com.utibunna.libreriaChat.libroDTO.LibroPatchDTO;
import com.utibunna.libreriaChat.model.Libro;
import com.utibunna.libreriaChat.service.LibroService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Libro crear(@Valid @RequestBody LibroDTO libroDTO) {
        return libroService.crearLibro(libroDTO);
    }

    @GetMapping
    public Page<Libro> obtenerTodos(@RequestParam(defaultValue = "0") @Min(0) int page) {
        return libroService.obtenerTodos(page);
    }

    @GetMapping("/{id}")
    public Libro obtenerPorId(@PathVariable Long id) {
        return libroService.obtenerPorId(id);
    }

    @GetMapping("/autor/{autor}")
    public Page<Libro> obtenerPorAutor(@PathVariable String autor,
                                       @RequestParam(defaultValue = "0") @Min(0) int page) {
        return libroService.obtenerPorAutor(autor, page);
    }

    @PutMapping("/{id}")
    public Libro actualizar(@PathVariable Long id, @Valid @RequestBody LibroDTO libroDTO) {
        return libroService.actualizarLibro(id, libroDTO);
    }

    @PatchMapping("/{id}")
    public Libro actualizarParcial(@PathVariable Long id, @Valid @RequestBody LibroPatchDTO libroPatchDTO) {
        return libroService.actualizarParcialLibro(id, libroPatchDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        libroService.eliminarLibro(id);
    }

    @DeleteMapping()
    public void eliminarTodo(){libroService.eliminarTodo();}
}
