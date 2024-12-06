package com.example.proyecto_hibernate.classes;

import javax.persistence.*;

@Entity
@Table(name = "profesores")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor")
    private int idProfesor;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "numero_asignado")
    private String numeroAsignado;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoProfesor tipo;

    public Profesor() {
    }

    public Profesor(String contrasena, String nombre, String numeroAsignado, TipoProfesor tipo) {
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.numeroAsignado = numeroAsignado;
        this.tipo = tipo;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroAsignado() {
        return numeroAsignado;
    }

    public void setNumeroAsignado(String numeroAsignado) {
        this.numeroAsignado = numeroAsignado;
    }

    public TipoProfesor getTipo() {
        return tipo;
    }

    public void setTipo(TipoProfesor tipo) {
        this.tipo = tipo;
    }
}
