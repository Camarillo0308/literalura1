package com.literalura1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer birthYear;
    private Integer deathYear;

    public Autor() {}

    public String getName() {
        return name;
    }

    public Integer getNacimiento() {
        return birthYear;
    }

public void setName(String name) {
    this.name = name;
}

public void setBirthYear(Integer birthYear) {
    this.birthYear = birthYear;
}

public void setDeathYear(Integer deathYear) {
    this.deathYear = deathYear;
}


    public Integer getFallecimiento() {
        return deathYear;
    }

    @Override
    public String toString() {
        return name + " (" + birthYear + " - " + deathYear + ")";
    }
}
