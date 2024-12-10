package com.example.proyecto_hibernate.util;

import com.example.proyecto_hibernate.classes.Profesores;

public class GuardarProfesor {
    private static Profesores profesores_guardado;

    public static Profesores getProfesor() {
        return profesores_guardado;
    }


    public static void setProfesor(Profesores profesores) {
        profesores_guardado = profesores;
    }
}
