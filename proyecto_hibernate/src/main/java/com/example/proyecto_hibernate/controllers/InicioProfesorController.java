package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.util.ComprobacionesYcambioEscena;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

public class InicioProfesorController {

    @FXML
    private MenuItem cear_parte;

    @FXML
    private ImageView imagen_fondo;

    @FXML
    private MenuBar menu;

    @FXML
    void onCrearParteClick(ActionEvent event) {
        ComprobacionesYcambioEscena.abrirEscena("parte-verde.fxml", "Crear parte");
    }

}
