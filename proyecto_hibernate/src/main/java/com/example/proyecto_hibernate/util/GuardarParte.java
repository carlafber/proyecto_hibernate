package com.example.proyecto_hibernate.util;

import com.example.proyecto_hibernate.classes.PartesIncidencia;

// Clase que almacena la información de un parte que se le pasa desde el controller

public class GuardarParte {
    private static PartesIncidencia parte_guardado;

    public static PartesIncidencia getParte() {
        return parte_guardado;
    }


    public static void setParte(PartesIncidencia parte) {
        parte_guardado = parte;
    }


    public static void resetParte() {
        parte_guardado = null;
    }
}
