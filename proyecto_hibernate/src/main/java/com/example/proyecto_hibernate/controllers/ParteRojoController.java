package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.util.ComprobacionesYcambioEscena;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ParteRojoController {

    @FXML
    private Button bt_crear;

    @FXML
    private Button bt_parteNaranja;

    @FXML
    private Button bt_parteRojo;

    @FXML
    private Button bt_parteVerde;

    @FXML
    private DatePicker dp_fechaParte;

    @FXML
    private Label nombre_profesor;

    @FXML
    private TextArea txt_descripcion;

    @FXML
    private TextField txt_expedienteAlumno;

    @FXML
    private TextField txt_nombreGrupo;

    @FXML
    void onCrearClick(ActionEvent event) {

    }

    @FXML
    void onParteNaranjaClick(ActionEvent event) {
        ComprobacionesYcambioEscena.cambiarEscena(bt_parteNaranja, "parte-naranja.fxml");
    }//onParteNaranjaClick

    @FXML
    void onParteVerdeClick(ActionEvent event) {
        ComprobacionesYcambioEscena.cambiarEscena(bt_parteRojo, "parte-verde.fxml");
    }//onParteVerdeClick
}//class
