package com.example.proyecto_hibernate.util;

//interfaz que define el comportamiento de la configuración de botones

public interface Configurable {
    //método para configurar botones activándolos o desactivándolos según si se trata de profesor o jefe de estudios
    void configurarBotones(Boolean estado);
}
