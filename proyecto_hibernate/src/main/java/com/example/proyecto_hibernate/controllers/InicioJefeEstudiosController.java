package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.util.ComprobacionesYcambioEscena;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

public class InicioJefeEstudiosController {

    @FXML
    private MenuItem crear_parte;

    @FXML
    private MenuItem crear_profesor;

    @FXML
    private ImageView imagen_fondo;

    @FXML
    private MenuItem lista_alumnos;

    @FXML
    private MenuItem lista_partes;

    @FXML
    private MenuBar menu;

    @FXML
    void onCrearParteClick(ActionEvent event) {
        ComprobacionesYcambioEscena.abrirEscena("parte-verde.fxml", "Crear parte");
    }

    @FXML
    void onCrearProfesorClick(ActionEvent event) {
        ComprobacionesYcambioEscena.abrirEscena("crear-profesor.fxml", "Crear profesor");
    }

    @FXML
    void onListaAlumnosClick(ActionEvent event) {
        ComprobacionesYcambioEscena.abrirEscena("lista-alumnos.fxml", "Lista alumnos");
    }

    @FXML
    void onListaPartesClick(ActionEvent event) {
        ComprobacionesYcambioEscena.abrirEscena("lista-partes.fxml", "Lista partes");
    }

}

