package com.example.proyecto_hibernate.util;

import com.example.proyecto_hibernate.controllers.ListaPartesController;

public class GuardarController {
    private static ListaPartesController listaPartesController;

    public static ListaPartesController getController() {
        return listaPartesController;
    }


    public static void setController(ListaPartesController controller) {
        listaPartesController = controller;
    }
}
