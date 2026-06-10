# Anotaciones y arquitectura de LibreriaChat

## Alcance
Este documento se construyo leyendo el arbol del proyecto, el codigo fuente, los recursos SQL/HTML y el archivo generado por MapStruct en `target/generated-sources/annotations`. No modifica ningun otro archivo.

## Dependencias observadas
- `spring-boot-starter-webmvc`: controladores HTTP, vistas Thymeleaf, validacion de request y manejo de errores.
- `spring-boot-starter-websocket`: WebSocket STOMP para el chat.
- `spring-boot-starter-data-jpa`: entidades, repositorios y consultas de libros/editoriales/generos.
- `spring-boot-starter-data-mongodb`: persistencia de mensajes del chat.
- `spring-boot-starter-validation`: anotaciones de validacion Jakarta.
- `spring-ai-starter-model-google-genai`: cliente de IA usado por `BotIAService`.
- `lombok`: reduccion de boilerplate.
- `mapstruct`: mapeo entre entidades y DTO.
- `spring-boot-starter-flyway`: migraciones SQL de PostgreSQL.
- `hibernate` transitivo por JPA: restricciones adicionales como `@SQLRestriction`.
- `jackson` transitivo por Spring Web: `@JsonIgnore`.

## Inventario de anotaciones

### Spring Boot y Spring Framework
- `@SpringBootApplication` (`LibreriaChatApplication`): arranca la aplicacion y activa el auto-configurado de Spring Boot.
- `@SpringBootTest` (`LibreriaChatApplicationTests`): levanta el contexto completo para pruebas de integracion.
- `@Configuration` (`WebSocketConfig`): declara una clase de configuracion de Spring.
- `@Service` (`BotIAService`, `LibroServiceImpl`, `MensajeService`): marca la capa de negocio como bean administrado por Spring.
- `@Controller` (`ChatSocketController`, `ChatUIController`, `LibroUIController`): expone controladores MVC o de mensajeria.
- `@RestController` (`LibroController`, `MensajeController`): expone endpoints REST con respuesta serializada.
- `@RestControllerAdvice` (`GlobalExceptionHandler`): centraliza el manejo de excepciones para APIs REST.
- `@Repository` (`MensajeRepository`): marca la capa de acceso a datos y traduce excepciones de persistencia.
- `@Component` (`target/generated-sources/.../LibroMapperImpl.java`): registra el mapper generado como bean de Spring.

### Spring Web MVC
- `@RequestMapping` (`LibroController`, `ChatUIController`, `LibroUIController`): define la ruta base de un controlador.
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@PatchMapping`, `@DeleteMapping`: mapean verbos HTTP a metodos concretos.
- `@PathVariable` (`LibroController`): extrae valores desde la URL.
- `@RequestParam` (`LibroController`): extrae parametros de query string.
- `@RequestBody` (`LibroController`): deserializa el cuerpo JSON de la request.
- `@ModelAttribute` (`LibroUIController`): vincula un formulario HTML con un objeto de modelo.
- `@ResponseStatus` (`LibroController`): fija el codigo HTTP de la respuesta.
- `@ExceptionHandler` (`GlobalExceptionHandler`): transforma excepciones en `ProblemDetail`.

### WebSocket y mensajeria STOMP
- `@EnableWebSocketMessageBroker` (`WebSocketConfig`): activa el soporte STOMP sobre WebSocket.
- `@MessageMapping` (`ChatSocketController`): mapea mensajes entrantes del cliente STOMP.
- `@SendTo` (`ChatSocketController`): publica la respuesta en un destino STOMP.

### Persistencia JPA, Mongo y Hibernate
- `@Entity` (`Editorial`, `Genero`, `Libro`): define entidades JPA.
- `@Table` (`Editorial`, `Genero`, `Libro`): fija el nombre fisico de la tabla.
- `@Id` de `jakarta.persistence` (`Editorial`, `Genero`, `Libro`): clave primaria JPA.
- `@GeneratedValue` (`Editorial`, `Genero`, `Libro`): genera automaticamente el identificador.
- `@Column` (`Editorial`, `Genero`, `Libro`): configura columnas y restricciones.
- `@OneToMany` (`Editorial`): modela la relacion uno-a-muchos hacia `Libro`.
- `@ManyToOne` (`Libro`): modela la relacion muchos-a-uno hacia `Editorial`.
- `@ManyToMany` (`Libro`, `Genero`): modela la relacion muchos-a-muchos entre libros y generos.
- `@JoinColumn` (`Libro`): define la FK hacia editorial o la tabla intermedia.
- `@JoinTable` (`Libro`): define la tabla puente `libros_generos`.
- `@Query` (`LibroRepository`): declara consultas JPQL personalizadas.
- `@EntityGraph` (`LibroRepository`, `EditorialRepository`): fuerza la carga de relaciones asociadas.
- `@SQLRestriction` (`Libro`): agrega un filtro SQL de Hibernate para aplicar soft delete con `disponible = true`.
- `@Document` (`Mensaje`): define un documento MongoDB.
- `org.springframework.data.annotation.Id` (`Mensaje`): clave primaria del documento Mongo.

### Validacion Jakarta
- `@Valid` (`LibroController`, `LibroUIController`): dispara validacion declarativa sobre DTOs.
- `@NotBlank` (`LibroCreateDTO`, `LibroDTO`, `LibroPatchDTO`): exige texto no vacio.
- `@NotNull` (`LibroCreateDTO`, `LibroDTO`): exige valor presente.
- `@NotEmpty` (`LibroCreateDTO`, `LibroDTO`): exige coleccion no vacia.
- `@Size` (`LibroCreateDTO`, `LibroDTO`, `LibroPatchDTO`): limita longitud de cadenas.
- `@PastOrPresent` (`LibroCreateDTO`): obliga fecha actual o pasada.
- `@Positive` (`LibroCreateDTO`): obliga valor mayor que cero.
- `@DecimalMin` (`LibroDTO`, `LibroPatchDTO`): obliga un minimo decimal.
- `@Min` (`LibroController`): valida paginacion no negativa.
- `@Constraint` (`ValidISBN`): conecta la anotacion custom con su validador.
- `@ValidISBN` (`LibroCreateDTO`): valida el formato de ISBN con la regla del proyecto.

### Lombok
- `@Data` (`LibroDTO`, `LibroPatchDTO`, `Mensaje`): genera getters, setters, `toString`, `equals` y `hashCode`.
- `@NoArgsConstructor` (`LibroDTO`, `LibroPatchDTO`, `Mensaje`): genera constructor sin argumentos.
- `@AllArgsConstructor` (`LibroDTO`): genera constructor con todos los campos.
- `@RequiredArgsConstructor` (`ChatSocketController`, `ChatUIController`, `MensajeService`): genera constructor para campos `final`.

### MapStruct
- `@Mapper` (`LibroMapper`): declara una interfaz de mapeo generable por MapStruct.
- `@Mapping` (`LibroMapper`): define mapeos explicitos entre campos de entidades y DTO.
- `@Named` (`LibroMapper`): etiqueta un metodo auxiliar para ser reutilizado en mapeos.
- `@Generated` (`target/generated-sources/.../LibroMapperImpl.java`): marca el codigo como generado automaticamente.

### Jackson y Java
- `@JsonIgnore` (`Editorial`, `Genero`): evita recursion o payloads innecesarios al serializar relaciones bidireccionales.
- `@Override` (`WebSocketConfig`, `EditorialRepository`, `LibroRepository`): indica que un metodo sobrescribe otro de la superclase o interfaz.
- `@Documented` (`ValidISBN`): hace que la anotacion custom aparezca en la documentacion generada.
- `@Target` (`ValidISBN`): limita donde puede usarse la anotacion custom.
- `@Retention` (`ValidISBN`): conserva la anotacion en tiempo de ejecucion.

### Anotacion propia del proyecto
- `@ValidISBN` (`LibroCreateDTO`): anotacion propia que delega en `IsbnValidator`; sirve para validar que el ISBN siga el formato esperado por el proyecto.

## Arquitectura de endpoints

### Chat / IA
- La interfaz web del chat se sirve en `GET /api/chat` desde `ChatUIController`, que carga `templates/chat/sala.html`.
- El navegador abre el WebSocket STOMP en `/chat-websocket` mediante SockJS.
- El cliente envia mensajes a `/app/enviar`; ese prefijo lo define `WebSocketConfig`.
- `ChatSocketController` recibe el mensaje, lo guarda en MongoDB con `MensajeService` y responde de inmediato al canal `/tema/mensajes`.
- En paralelo, `BotIAService` construye un prompt con el historial del chat, agrega contexto del catalogo si la pregunta lo amerita, llama a `ChatClient` y guarda la respuesta de la IA en MongoDB.
- El frontend se suscribe a `/tema/mensajes`, por lo que cada mensaje guardado o generado aparece en la sala sin recargar la pagina.
- `MensajeController` no expone una ruta HTTP utilizable en su estado actual, porque `@RestController("/api/mensajes")` no define un path; solo define el nombre del bean.

### Libros
- La API principal de libros vive en `LibroController` bajo `/libros`.
- `POST /libros` crea un libro a partir de `LibroCreateDTO`, valida los datos y devuelve `LibroResponseDTO`.
- `GET /libros` devuelve una pagina de `Libro` completa.
- `GET /libros/{id}` devuelve un libro por id.
- `GET /libros/autor/{autor}` filtra por autor con paginacion.
- `PUT /libros/{id}` hace actualizacion completa con `LibroDTO`.
- `PATCH /libros/{id}` hace actualizacion parcial con `LibroPatchDTO`.
- `DELETE /libros/{id}` no borra fisicamente: ejecuta soft delete marcando `disponible = false`.
- `DELETE /libros` aplica soft delete a todo el catalogo.
- `GET /libros/slice` devuelve un resumen paginado con `LibroResumenDTO` y metadatos de paginacion.
- `GET /libros/dto/{id}` devuelve una proyeccion resumida por id.
- `GET /libros/entityAll` devuelve la lista completa de entidades.
- `LibroServiceImpl` coordina la logica: resuelve `Editorial`, `Genero`, aplica mapeo con `LibroMapper`, controla conflictos de ISBN y usa transacciones.
- La persistencia de libros usa PostgreSQL con Flyway: `editoriales`, `generos`, `libros` y la tabla puente `libros_generos`.
- La UI de libros vive en `GET /ui/libros`, `GET /ui/libros/nuevo` y `POST /ui/libros`, renderizando `templates/libros/lista.html` y `templates/libros/formulario.html`.

## Persistencia por dominio
- PostgreSQL guarda el catalogo de libros, editoriales y generos.
- MongoDB guarda el historial de mensajes del chat.
- La IA usa `spring-ai` con Google GenAI, configurado en `application.properties`.
