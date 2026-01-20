package com.literalura1.repository;

import com.literalura1.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {
        long countByLanguage(String language);
}
