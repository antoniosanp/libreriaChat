package com.utibunna.libreriaChat.service;

import com.utibunna.libreriaChat.libroDTO.LibroDTO;
import com.utibunna.libreriaChat.libroDTO.LibroPatchDTO;
import com.utibunna.libreriaChat.model.Libro;
import com.utibunna.libreriaChat.repository.LibroRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class LibroServiceImpl implements LibroService {

    private static final int PAGE_SIZE = 5;

    private final LibroRepository libroRepository;

    public LibroServiceImpl(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Override
    public Libro crearLibro(LibroDTO libroDTO) {
        Libro libro = new Libro();
        mapDtoToEntity(libroDTO, libro);
        return guardar(libro);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Libro> obtenerTodos(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return libroRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Libro obtenerPorId(Long id) {
        return buscarLibro(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Libro> obtenerPorAutor(String autor, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return libroRepository.findByAutorContainingIgnoreCase(autor, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Libro> obtenerCatalogo() {
        return libroRepository.findAll();
    }

    @Override
    public Libro actualizarLibro(Long id, LibroDTO libroDTO) {
        Libro libro = buscarLibro(id);
        mapDtoToEntity(libroDTO, libro);
        return guardar(libro);
    }

    @Override
    public Libro actualizarParcialLibro(Long id, LibroPatchDTO libroPatchDTO) {
        Libro libro = buscarLibro(id);

        if (libroPatchDTO.getTitulo() != null) {
            libro.setTitulo(libroPatchDTO.getTitulo().trim());
        }
        if (libroPatchDTO.getAutor() != null) {
            libro.setAutor(libroPatchDTO.getAutor().trim());
        }
        if (libroPatchDTO.getIsbn() != null) {
            libro.setIsbn(libroPatchDTO.getIsbn().trim());
        }
        if (libroPatchDTO.getAnioPublicacion() != null) {
            libro.setAnioPublicacion(libroPatchDTO.getAnioPublicacion());
        }

        return guardar(libro);
    }

    @Override
    public void eliminarLibro(Long id) {
        Libro libro = buscarLibro(id);
        libroRepository.delete(libro);
    }

    @Override
    public void eliminarTodo(){
        libroRepository.deleteAll();
    }

    private Libro buscarLibro(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));
    }

    private Libro guardar(Libro libro) {
        try {
            return libroRepository.save(libro);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El ISBN ya existe", ex);
        }
    }

    private void mapDtoToEntity(LibroDTO libroDTO, Libro libro) {
        libro.setTitulo(libroDTO.getTitulo().trim());
        libro.setAutor(libroDTO.getAutor().trim());
        libro.setIsbn(libroDTO.getIsbn().trim());
        libro.setAnioPublicacion(libroDTO.getAnioPublicacion());
    }
}
