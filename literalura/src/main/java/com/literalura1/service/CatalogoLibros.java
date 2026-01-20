package com.literalura1.service;

import com.literalura1.model.Libro;

import java.util.ArrayList;
import java.util.List;

public class CatalogoLibros {

    private final List<Libro> libros = new ArrayList<>();

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public List<Libro> listarTodos() {
        return libros;
    }

    public List<Libro> listarPorIdioma(String idioma) {
        return libros.stream()
                .filter(libro -> libro.getIdioma().equalsIgnoreCase(idioma))
                .toList();
    }

    public boolean estaVacio() {
        return libros.isEmpty();
    }
}
