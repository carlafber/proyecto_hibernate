package com.example.proyecto_hibernate.classes;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "partes_incidencia")
public class ParteIncidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parte")
    private int id_parte;

    @ManyToOne
    @JoinColumn(name = "id_alum")
    private Alumnos alumno;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupos grupo;

    @ManyToOne
    @JoinColumn(name = "id_profesor")
    private Profesor profesor;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    private String hora;

    @Column(name = "sancion")
    private String sancion;

    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private ColorParte color;

    @Column(name = "puntos_parte")
    private int puntos_parte;


    public ParteIncidencia() {
    }

    public ParteIncidencia(Alumnos alumno, Profesor profesor, Grupos grupo, LocalDate fecha, String hora, String descripcion, String sancion, ColorParte color) {
        this.alumno = alumno;
        this.profesor = profesor;
        this.grupo = grupo;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.sancion = sancion;
        this.color = color;
        this.puntos_parte = color.getPuntos(); // Inicializar los puntos basados en el color
    }


    public int getId_parte() {
        return id_parte;
    }

    public void setId_parte(int id_parte) {
        this.id_parte = id_parte;
    }

    public Alumnos getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumnos alumno) {
        this.alumno = alumno;
    }

    public Grupos getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupos grupo) {
        this.grupo = grupo;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getSancion() {
        return sancion;
    }

    public void setSancion(String sancion) {
        this.sancion = sancion;
    }

    public ColorParte getColor() {
        return color;
    }

    public void setColor(ColorParte color) {
        this.color = color;
        this.puntos_parte = color.getPuntos(); // Al cambiar el color, actualizar los puntos
    }

    public int getPuntos_parte() {
        return puntos_parte;
    }

    public void setPuntos_parte(int puntos_parte) {
        this.puntos_parte = puntos_parte;
    }
}