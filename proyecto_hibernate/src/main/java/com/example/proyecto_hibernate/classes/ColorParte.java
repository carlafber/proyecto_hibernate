package com.example.proyecto_hibernate.classes;

public enum ColorParte {
    VERDE("#a0ffa0", 1),
    NARANJA("#ffce86", 6),
    ROJO("#ff6e6e", 12);

    private final String codigo_color;
    private final int puntos;


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
