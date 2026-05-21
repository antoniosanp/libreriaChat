package com.utibunna.libreriaChat.controllerUI;

import com.utibunna.libreriaChat.libroDTO.LibroDTO;
import com.utibunna.libreriaChat.model.Libro;
import com.utibunna.libreriaChat.service.LibroService;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ui/libros")
public class LibroUIController {

    private final LibroService libroService;

    public LibroUIController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public String listarLibros(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Libro> librosPage = libroService.obtenerTodos(page);

        model.addAttribute("librosPage", librosPage);
        model.addAttribute("paginaActual", page);
        model.addAttribute("paginas", obtenerPaginas(librosPage.getTotalPages()));

        return "libros/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("libroDTO", new LibroDTO());
        return "libros/formulario";
    }

    @PostMapping
    public String crearLibro(@Valid @ModelAttribute("libroDTO") LibroDTO libroDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "libros/formulario";
        }

        Libro libro = libroService.crearLibro(libroDTO);
        redirectAttributes.addFlashAttribute("mensajeExito", "Libro creado con id " + libro.getId());
        return "redirect:/ui/libros";
    }

    private List<Integer> obtenerPaginas(int totalPages) {
        if (totalPages <= 0) {
            return Collections.emptyList();
        }
        return IntStream.range(0, totalPages).boxed().toList();
    }
}
