package com.example.proyecto_hibernate.CRUD;

import com.example.proyecto_hibernate.classes.*;
import java.util.*;


//Interfaz que define los métodos necesarios para realizar operaciones CRUD (en este caso, Crear, Leer y Actualizar) sobre los Partes
public interface IPartesCRUD {

    //método para obtener una lista de todos los partes de incidencia de la BD
    ArrayList<PartesIncidencia> obtenerPartes();


    //método para comprobar si se crea un nuevo parte de incidencia en la BD
    boolean crearParte(PartesIncidencia partesIncidencia);


    //método para obtener los partes de incidencia asociados a un alumno, a través de su id
    List<PartesIncidencia> obtenerPartesAlumno(int id_alumno);


    //método para comprobar si se actualiza un parte de incidencia existente en la BD
    boolean actualizarParte(PartesIncidencia parte);


    //método para obtener estadísticas por el tipo de color
    List<Object[]> obtenerEstadisticasPartesPorColor();


    //método para obtener estadísticas por el expediente del alumnoa
    List<Object[]> obtenerEstadisticasPartesPorAlumno(String numeroExpediente);


    //método para obtener estadísticas por el tipo de curso
    List<Object[]> obtenerEstadisticasPartesPorCurso(String curso);
}
