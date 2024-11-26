package com.example.proyectopartepablo.clases;

import javax.persistence.*;

@Entity
@Table(name = "alumnos")
public class Alumnos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_alum;
    private String nombre_alum;
    private String numero_expediente;
    private int puntos_acumulados;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupos grupo;


    public int getId_alum() {
        return id_alum;
    }

    public void setId_alum(int id_alum) {
        this.id_alum = id_alum;
    }

    public String getNombre_alum() {
        return nombre_alum;
    }

    public void setNombre_alum(String nombre_alum) {
        this.nombre_alum = nombre_alum;
    }

    public String getNumero_expediente() {
        return numero_expediente;
    }

    public void setNumero_expediente(String numero_expediente) {
        this.numero_expediente = numero_expediente;
    }

    public int getPuntos_acumulados() {
        return puntos_acumulados;
    }

    public void setPuntos_acumulados(int puntos_acumulados) {
        this.puntos_acumulados = puntos_acumulados;
    }

    public Grupos getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupos grupo) {
        this.grupo = grupo;
    }
}
