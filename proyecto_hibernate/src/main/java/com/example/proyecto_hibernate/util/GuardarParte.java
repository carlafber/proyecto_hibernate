package com.example.proyecto_hibernate.util;

import com.example.proyecto_hibernate.classes.PartesIncidencia;

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
