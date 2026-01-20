package com.literalura1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String language;

    private Integer downloadCount;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    // 🔹 Constructor vacío OBLIGATORIO para JPA
    public Libro() {}

    // ===== getters =====
    public String getTitulo() {
        return title;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

public void setTitle(String title) {
    this.title = title;
}

public void setLanguage(String language) {
    this.language = language;
}

public void setDownloadCount(Integer downloadCount) {
    this.downloadCount = downloadCount;
}

    public String getIdioma() {
        return language;
    }

    public Integer getDescargas() {
        return downloadCount;
    }

    @Override
    public String toString() {
        return """
               📘 Título: %s
               ✍️ Autor: %s
               🌍 Idioma: %s
               ⬇️ Descargas: %d
               """.formatted(
                title,
                autor != null ? autor.getName() : "Desconocido",
                language,
                downloadCount
        );
    }
}
