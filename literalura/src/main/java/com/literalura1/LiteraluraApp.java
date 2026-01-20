package com.literalura1;

import com.google.gson.Gson;
import com.literalura1.Dto.AutorDTO;
import com.literalura1.Dto.LibroDTO;
import com.literalura1.model.*;
import com.literalura1.repository.AutorRepository;
import com.literalura1.repository.LibroRepository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApp implements CommandLineRunner {

@Autowired
private LibroRepository libroRepository;

@Autowired
private AutorRepository autorRepository;


    private static final String URL_BASE = "https://gutendex.com/books/?search=";

public static void main(String[] args) {
    SpringApplication.run(LiteraluraApp.class, args);
}

@Override
public void run(String... args) throws Exception {
            Scanner scanner = new Scanner(System.in);

        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> buscarLibro(scanner);
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivos(scanner);
                case 5 -> mostrarEstadisticasPorIdioma();
                case 0 -> {
                    System.out.println("👋 Saliendo del programa...");
                    return;
                }
                default -> System.out.println("❌ Opción inválida");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("""
                \n📚 LITERALURA
                1 - Buscar libro por título
                2 - Listar libros buscados
                3 - Listar autores
                4 - Autores vivos en un año
                5 - Mostrar estadisticas por idioma
                0 - Salir
                """);
    }

    private void mostrarEstadisticasPorIdioma() {
    List<String> idiomas = List.of("en", "es");

    System.out.println("\n📊 Estadísticas de libros por idioma:\n");

    idiomas.stream()
            .forEach(idioma -> {
                long cantidad = libroRepository.countByLanguage(idioma);
                System.out.printf("Idioma %s → %d libros%n", idioma.toUpperCase(), cantidad);
            });
}


private void buscarLibro(Scanner scanner) throws Exception {
    System.out.print("Buscar libro: ");
    String titulo = scanner.nextLine();

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(URL_BASE + titulo))
            .GET()
            .build();

    HttpResponse<String> response =
            client.send(request, HttpResponse.BodyHandlers.ofString());

    Gson gson = new Gson();
    RespuestaAPI datos = gson.fromJson(response.body(), RespuestaAPI.class);

    if (datos.getResults().isEmpty()) {
        System.out.println("❌ No se encontraron resultados.");
        return;
    }

LibroDTO libroDTO = datos.getResults().get(0);

// ===== AUTOR =====
Autor autor = null;
if (libroDTO.getAuthors() != null && !libroDTO.getAuthors().isEmpty()) {

    AutorDTO autorDTO = libroDTO.getAuthors().get(0);

    autor = autorRepository
            .findByName(autorDTO.getName())
            .orElseGet(() -> {
                Autor nuevo = new Autor();
                nuevo.setName(autorDTO.getName());
                nuevo.setBirthYear(autorDTO.getBirthYear());
                nuevo.setDeathYear(autorDTO.getDeathYear());
                return autorRepository.save(nuevo);
            });
}

// ===== LIBRO =====
Libro libro = new Libro();
libro.setTitle(libroDTO.getTitle());
libro.setAutor(autor);
libro.setLanguage(
        libroDTO.getLanguages() != null && !libroDTO.getLanguages().isEmpty()
                ? libroDTO.getLanguages().get(0)
                : "N/A"
);
libro.setDownloadCount(libroDTO.getDownloadCount());

libroRepository.save(libro);

System.out.println("\n✅ Libro guardado en la base de datos:\n");
System.out.println(libro);

}

private void listarLibros() {
    List<Libro> libros = libroRepository.findAll();

    if (libros.isEmpty()) {
        System.out.println("No hay libros registrados.");
        return;
    }

    libros.forEach(System.out::println);
}


private void listarAutores() {
    List<Autor> autores = autorRepository.findAll();

    if (autores.isEmpty()) {
        System.out.println("No hay autores registrados.");
        return;
    }

    autores.forEach(System.out::println);
}
private void listarAutoresVivos(Scanner scanner) {
    System.out.print("Ingrese el año: ");
    int anio = scanner.nextInt();
    scanner.nextLine();

    autorRepository.findAll().stream()
            .filter(a ->
                    a.getNacimiento() != null &&
                    a.getNacimiento() <= anio &&
                    (a.getFallecimiento() == null || a.getFallecimiento() > anio)
            )
            .forEach(System.out::println);
}


}
