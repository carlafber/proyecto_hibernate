package com.example.proyecto_hibernate.classes;

import javax.persistence.*;

@Entity
@Table(name = "grupos")
public class Grupos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_grupo;
    private String nombre_grupo;

    public Grupos(int id_grupo, String nombre_grupo) {
        this.id_grupo = id_grupo;
        this.nombre_grupo = nombre_grupo;
    }

    public Grupos() {
    }

    public int getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }

    public String getNombre_grupo() {
        return nombre_grupo;
    }

    public void setNombre_grupo(String nombre_grupo) {
        this.nombre_grupo = nombre_grupo;
    }
}
