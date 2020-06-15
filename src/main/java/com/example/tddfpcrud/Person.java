package com.example.tddfpcrud;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Person {
    private String id;
    private String nombre;

    public Person(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
