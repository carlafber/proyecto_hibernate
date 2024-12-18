package com.example.proyecto_hibernate.util;

import com.example.proyecto_hibernate.classes.Profesores;

//Clase que almacena la información de un profesor que se le pasa desde el controller

public class GuardarProfesor {
    private static Profesores profesor_guardado;

    public static Profesores getProfesor() {
        return profesor_guardado;
    }


    public static void setProfesor(Profesores profesores) {
        profesor_guardado = profesores;
    }
}
