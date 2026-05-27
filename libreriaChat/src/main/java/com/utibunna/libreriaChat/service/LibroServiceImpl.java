package com.utibunna.libreriaChat.service;

import com.utibunna.libreriaChat.libroDTO.LibroDTO;
import com.utibunna.libreriaChat.libroDTO.LibroPatchDTO;
import com.utibunna.libreriaChat.libroDTO.LibroResumenDTO;
import com.utibunna.libreriaChat.model.Editorial;
import com.utibunna.libreriaChat.model.Genero;
import com.utibunna.libreriaChat.model.Libro;
import com.utibunna.libreriaChat.repository.EditorialRepository;
import com.utibunna.libreriaChat.repository.GeneroRepository;
import com.utibunna.libreriaChat.repository.LibroRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class LibroServiceImpl implements LibroService {

    private static final int PAGE_SIZE = 10;

    private final LibroRepository libroRepository;
    private final EditorialRepository editorialRepository;
    private final GeneroRepository generoRepository;

    public LibroServiceImpl(
            LibroRepository libroRepository,
            EditorialRepository editorialRepository,
            GeneroRepository generoRepository
    ) {
        this.libroRepository = libroRepository;
        this.editorialRepository = editorialRepository;
        this.generoRepository = generoRepository;
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
        return libroRepository.findByDisponibleTrue();
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
        if (libroPatchDTO.getFechaPublicacion() != null) {
            libro.setFechaPublicacion(libroPatchDTO.getFechaPublicacion());
        }
        if (libroPatchDTO.getPrecio() != null) {
            libro.setPrecio(libroPatchDTO.getPrecio());
        }
        if (libroPatchDTO.getEditorialId() != null) {
            libro.setEditorial(buscarEditorial(libroPatchDTO.getEditorialId()));
        }
        if (libroPatchDTO.getGeneroIds() != null) {
            libro.setGeneros(buscarGeneros(libroPatchDTO.getGeneroIds()));
        }

        return guardar(libro);
    }

    @Override
    public void descatalogarLibro(Long id) {
        Libro libro = buscarLibro(id);
        libro.softDelete();
        libroRepository.save(libro);
    }

    @Override
    public void eliminarTodo() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(Libro::softDelete);
        libroRepository.saveAll(libros);
    }



    private Libro buscarLibro(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));
    }

    private Editorial buscarEditorial(Long editorialId) {
        return editorialRepository.findById(editorialId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editorial no encontrada"));
    }

    private Set<Genero> buscarGeneros(Set<Long> generoIds) {
        List<Genero> generos = generoRepository.findAllById(generoIds);
        if (generos.size() != generoIds.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Uno o mas generos no existen");
        }
        return new HashSet<>(generos);
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
        libro.setFechaPublicacion(libroDTO.getFechaPublicacion());
        libro.setPrecio(libroDTO.getPrecio());
        libro.setEditorial(buscarEditorial(libroDTO.getEditorialId()));
        libro.setGeneros(buscarGeneros(libroDTO.getGeneroIds()));
    }


    //=========slice========

    @Override
    public Slice<LibroResumenDTO> getCatalogo(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return libroRepository.findAllLibroResumenes(pageable);
    }

    public Libro getLibroWithRelations(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado: " + id));
    }

    @Override
    public LibroResumenDTO getResumenById(Long id) {
        return libroRepository.findResumenById(id);
    }
}
