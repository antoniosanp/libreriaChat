package com.utibunna.libreriaChat.config;

import com.utibunna.libreriaChat.model.Libro;
import com.utibunna.libreriaChat.repository.LibroRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner seedDatabase(LibroRepository libroRepository) {
        return args -> {
            if (libroRepository.count() > 0) {
                return;
            }

            libroRepository.saveAll(List.of(
                    libro("Cien anos de soledad", "Gabriel Garcia Marquez", "9780307474728", 1967),
                    libro("Don Quijote de la Mancha", "Miguel de Cervantes", "9788424119474", 1605),
                    libro("1984", "George Orwell", "9780451524935", 1949),
                    libro("Rebelion en la granja", "George Orwell", "9780451526342", 1945),
                    libro("El principito", "Antoine de Saint-Exupery", "9780156012195", 1943),
                    libro("Crimen y castigo", "Fiodor Dostoyevski", "9780143058144", 1866),
                    libro("Los hermanos Karamazov", "Fiodor Dostoyevski", "9780374528379", 1880),
                    libro("Orgullo y prejuicio", "Jane Austen", "9780141439518", 1813),
                    libro("Matar a un ruisenor", "Harper Lee", "9780061120084", 1960),
                    libro("El gran Gatsby", "F. Scott Fitzgerald", "9780743273565", 1925),
                    libro("Ulises", "James Joyce", "9780199535675", 1922),
                    libro("La Odisea", "Homero", "9780140268867", -700),
                    libro("La Iliada", "Homero", "9780140275360", -750),
                    libro("Rayuela", "Julio Cortazar", "9788437604578", 1963),
                    libro("Pedro Paramo", "Juan Rulfo", "9786073140249", 1955),
                    libro("La sombra del viento", "Carlos Ruiz Zafon", "9788408172178", 2001),
                    libro("El amor en los tiempos del colera", "Gabriel Garcia Marquez", "9780307389732", 1985),
                    libro("Fahrenheit 451", "Ray Bradbury", "9781451673319", 1953),
                    libro("Dracula", "Bram Stoker", "9780486411095", 1897),
                    libro("Frankenstein", "Mary Shelley", "9780486282114", 1818),
                    libro("Jane Eyre", "Charlotte Bronte", "9780142437209", 1847),
                    libro("Cumbres borrascosas", "Emily Bronte", "9780141439556", 1847),
                    libro("Moby Dick", "Herman Melville", "9780142437247", 1851),
                    libro("Hamlet", "William Shakespeare", "9780743477123", 1603),
                    libro("Macbeth", "William Shakespeare", "9780743477109", 1606),
                    libro("Romeo y Julieta", "William Shakespeare", "9780743477116", 1597),
                    libro("La metamorfosis", "Franz Kafka", "9788491050292", 1915),
                    libro("El proceso", "Franz Kafka", "9780805209990", 1925),
                    libro("En busca del tiempo perdido", "Marcel Proust", "9780142437964", 1913),
                    libro("Madame Bovary", "Gustave Flaubert", "9780140449129", 1856),
                    libro("Anna Karenina", "Leon Tolstoi", "9780143035008", 1877),
                    libro("Guerra y paz", "Leon Tolstoi", "9780199232765", 1869),
                    libro("El retrato de Dorian Gray", "Oscar Wilde", "9780141439570", 1890),
                    libro("Las aventuras de Sherlock Holmes", "Arthur Conan Doyle", "9780141034355", 1892),
                    libro("El senor de los anillos", "J.R.R. Tolkien", "9780618640157", 1954),
                    libro("El hobbit", "J.R.R. Tolkien", "9780547928227", 1937),
                    libro("Harry Potter y la piedra filosofal", "J.K. Rowling", "9788478884452", 1997),
                    libro("Harry Potter y la camara secreta", "J.K. Rowling", "9788478884957", 1998),
                    libro("Harry Potter y el prisionero de Azkaban", "J.K. Rowling", "9788478885190", 1999),
                    libro("Harry Potter y el caliz de fuego", "J.K. Rowling", "9788478886456", 2000),
                    libro("Harry Potter y la Orden del Fenix", "J.K. Rowling", "9788478887422", 2003),
                    libro("Harry Potter y el misterio del principe", "J.K. Rowling", "9788478889907", 2005),
                    libro("Harry Potter y las reliquias de la muerte", "J.K. Rowling", "9788478889952", 2007),
                    libro("Los juegos del hambre", "Suzanne Collins", "9780439023481", 2008),
                    libro("En llamas", "Suzanne Collins", "9780439023498", 2009),
                    libro("Sinsajo", "Suzanne Collins", "9780439023511", 2010),
                    libro("Dune", "Frank Herbert", "9780441172719", 1965),
                    libro("Fundacion", "Isaac Asimov", "9780553293357", 1951),
                    libro("Yo, robot", "Isaac Asimov", "9780553382563", 1950),
                    libro("Neuromante", "William Gibson", "9780441569595", 1984),
                    libro("Un mundo feliz", "Aldous Huxley", "9780060850524", 1932),
                    libro("La naranja mecanica", "Anthony Burgess", "9780393312836", 1962),
                    libro("El guardian entre el centeno", "J.D. Salinger", "9780316769488", 1951),
                    libro("Lolita", "Vladimir Nabokov", "9780679723165", 1955),
                    libro("El extranjero", "Albert Camus", "9780679720201", 1942),
                    libro("La peste", "Albert Camus", "9780679720218", 1947),
                    libro("Siddhartha", "Hermann Hesse", "9780553208849", 1922),
                    libro("Demian", "Hermann Hesse", "9788497594257", 1919),
                    libro("El lobo estepario", "Hermann Hesse", "9788420674205", 1927),
                    libro("El alquimista", "Paulo Coelho", "9780061122415", 1988),
                    libro("Veronika decide morir", "Paulo Coelho", "9780061124266", 1998),
                    libro("El codigo Da Vinci", "Dan Brown", "9780307474278", 2003),
                    libro("Angeles y demonios", "Dan Brown", "9781416524793", 2000),
                    libro("Inferno", "Dan Brown", "9781400079155", 2013),
                    libro("La chica del tren", "Paula Hawkins", "9781594634024", 2015),
                    libro("Perdida", "Gillian Flynn", "9780307588371", 2012),
                    libro("It", "Stephen King", "9781501142970", 1986),
                    libro("El resplandor", "Stephen King", "9780307743657", 1977),
                    libro("Misery", "Stephen King", "9781501143106", 1987),
                    libro("Carrie", "Stephen King", "9780307743664", 1974),
                    libro("La torre oscura I: El pistolero", "Stephen King", "9780451210845", 1982),
                    libro("El nombre de la rosa", "Umberto Eco", "9780156001311", 1980),
                    libro("Baudolino", "Umberto Eco", "9780156029063", 2000),
                    libro("Ensayo sobre la ceguera", "Jose Saramago", "9780156007757", 1995),
                    libro("El evangelio segun Jesucristo", "Jose Saramago", "9780156005494", 1991),
                    libro("La casa de los espiritus", "Isabel Allende", "9780553383805", 1982),
                    libro("Eva Luna", "Isabel Allende", "9780553383829", 1987),
                    libro("Como agua para chocolate", "Laura Esquivel", "9780385420174", 1989),
                    libro("El tunel", "Ernesto Sabato", "9788437602215", 1948),
                    libro("Sobre heroes y tumbas", "Ernesto Sabato", "9788437602239", 1961),
                    libro("La tregua", "Mario Benedetti", "9786073128827", 1960),
                    libro("El coronel no tiene quien le escriba", "Gabriel Garcia Marquez", "9780307475152", 1961),
                    libro("Del amor y otros demonios", "Gabriel Garcia Marquez", "9780307387639", 1994),
                    libro("El perfume", "Patrick Suskind", "9780375725845", 1985),
                    libro("La historia interminable", "Michael Ende", "9780140386332", 1979),
                    libro("Momo", "Michael Ende", "9786077350835", 1973),
                    libro("El psicoanalista", "John Katzenbach", "9788466320405", 2002),
                    libro("La ladrona de libros", "Markus Zusak", "9780375842207", 2005),
                    libro("Bajo la misma estrella", "John Green", "9780142424179", 2012),
                    libro("Ciudades de papel", "John Green", "9780142414934", 2008),
                    libro("Buscando a Alaska", "John Green", "9780142402511", 2005),
                    libro("Ready Player One", "Ernest Cline", "9780307887443", 2011),
                    libro("Los pilares de la Tierra", "Ken Follett", "9780451166890", 1989),
                    libro("Un saco de canicas", "Joseph Joffo", "9788499086262", 1973),
                    libro("El diario de Ana Frank", "Ana Frank", "9780553296983", 1947),
                    libro("La carretera", "Cormac McCarthy", "9780307387899", 2006),
                    libro("Noches blancas", "Fiodor Dostoyevski", "9788491050513", 1848),
                    libro("El viejo y el mar", "Ernest Hemingway", "9780684801223", 1952),
                    libro("Por quien doblan las campanas", "Ernest Hemingway", "9780684803357", 1940),
                    libro("La divina comedia", "Dante Alighieri", "9780142437223", 1320),
                    libro("El conde de Montecristo", "Alexandre Dumas", "9780140449266", 1844),
                    libro("Los miserables", "Victor Hugo", "9780451419439", 1862),
                    libro("Veinte mil leguas de viaje submarino", "Jules Verne", "9780140447965", 1870),
                    libro("Viaje al centro de la Tierra", "Jules Verne", "9780141441979", 1864),
                    libro("La isla del tesoro", "Robert Louis Stevenson", "9780141321004", 1883),
                    libro("Alicia en el pais de las maravillas", "Lewis Carroll", "9780141321073", 1865)
            ));
        };
    }

    private static Libro libro(String titulo, String autor, String isbn, int anioPublicacion) {
        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setIsbn(isbn);
        libro.setAnioPublicacion(anioPublicacion);
        return libro;
    }
}
