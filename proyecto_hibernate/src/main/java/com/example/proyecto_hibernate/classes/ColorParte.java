package com.example.proyecto_hibernate.classes;

public enum ColorParte {
    VERDE("#a0ffa0"),
    NARANJA("#ffce86"),
    ROJO("#ff6e6e");

    private final String codigo_color;

    ColorParte(String codigo_color) {
        this.codigo_color = codigo_color;
    }

    public String getCodigo_color() {
        return codigo_color;
    }
}
