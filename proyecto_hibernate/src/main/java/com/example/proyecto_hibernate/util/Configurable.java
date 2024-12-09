package com.example.proyecto_hibernate.util;

import com.example.proyecto_hibernate.controllers.ListaPartesController;

public interface Configurable {
    void configurarBotones(Boolean estado);

    void setListaPartesController(ListaPartesController listaPartesController);
}
