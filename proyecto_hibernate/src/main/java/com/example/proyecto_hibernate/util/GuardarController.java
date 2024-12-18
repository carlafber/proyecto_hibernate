package com.example.proyecto_hibernate.util;

import com.example.proyecto_hibernate.controllers.ListaPartesController;

//Clase que gestiona una instancia de listapartescontroller

public class GuardarController {
    private static ListaPartesController listaPartesController;

    public static ListaPartesController getController() {
        return listaPartesController;
    }


    public static void setController(ListaPartesController controller) {
        listaPartesController = controller;
    }
}
