package com.example.proyecto_hibernate.CRUD;

import com.example.proyecto_hibernate.classes.*;
import java.util.*;


//Interfaz que define los métodos necesarios para realizar operaciones CRUD (en este caso solo, Leer y Actualizar) sobre los Alumnos
public interface IAlumnosCRUD {

    //método para obtener una lista de todos los alumnos de la BD
    ArrayList<Alumnos> obtenerAlumnos();


    //método para buscar un alumno por su número de expediente
    Alumnos buscarAlumnoPorExpediente(String expediente);


    //método para actualizar los puntos acumulados de un alumno según un parte de incidencia
    void actualizarPuntosAlumno(Alumnos alumno, PartesIncidencia parte, boolean sumar);
}
