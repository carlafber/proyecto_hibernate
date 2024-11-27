package com.example.proyecto_hibernate.classes;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Table(name = "partes_incidencia")
public class ParteIncidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parte")
    private int idParte;

    @ManyToOne
    @JoinColumn(name = "id_alum")
    private Alumnos alumno;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupos grupo;

    @ManyToOne
    @JoinColumn(name = "id_profesor")
    private Profesor profesor;

    @ManyToOne
    @JoinColumn(name = "id_punt_partes")
    private PuntuacionPartes puntuacionParte;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    private String hora;

    @Column(name = "sancion")
    private String sancion;


    public ParteIncidencia() {
    }

    public ParteIncidencia(Alumnos alumno, Profesor profesor, Grupos grupo, LocalDate fecha, String hora, String descripcion, String sancion) {
        this.alumno = alumno;
        this.profesor = profesor;
        this.grupo = grupo;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.sancion = sancion;
    }


    public int getIdParte() {
        return idParte;
    }

    public void setIdParte(int idParte) {
        this.idParte = idParte;
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

    public PuntuacionPartes getPuntuacionParte() {
        return puntuacionParte;
    }

    public void setPuntuacionParte(PuntuacionPartes puntuacionParte) {
        this.puntuacionParte = puntuacionParte;
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
}