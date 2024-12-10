package com.example.proyecto_hibernate.classes;

//esta clase asocia a cada elemento (verde, naranja, rojo) un color y los puntos asociados.

public enum ColorParte {
    VERDE("#a0ffa0", 1),
    NARANJA("#ffce86", 6),
    ROJO("#ff6e6e", 12);

    //ATRIBUTOS
    private final String codigo_color;
    private final int puntos;

    //CONSTRUCTOR
    ColorParte(String codigo_color, int puntos) {
        this.codigo_color = codigo_color;
        this.puntos = puntos;
    }

    public String getCodigo_color() {
        return codigo_color;
    }

    public int getPuntos() {
        return puntos;
    }
}
