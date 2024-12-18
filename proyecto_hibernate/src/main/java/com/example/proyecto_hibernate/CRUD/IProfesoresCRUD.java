package com.example.proyecto_hibernate.CRUD;

import com.example.proyecto_hibernate.classes.*;
import java.util.*;


//Interfaz que define los métodos necesarios para realizar operaciones CRUD (en este caso solo, Crear y Leer) sobre los Profesores
public interface IProfesoresCRUD {

    //método para obtener todos los profesores de la BD
    ArrayList<Profesores> obtenerProfesores();


    //método para crear un nuevo profesor en la base de datos
    boolean crearProfesor(Profesores profesores);

    // método para encriptar una cadena de texto
    String encriptarSHA256(String contrasena);
}
