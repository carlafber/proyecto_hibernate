package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.util.ComprobacionesYcambioEscena;
import com.example.proyecto_hibernate.util.GuardarProfesor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ParteNaranjaController implements Initializable {

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
    void onParteRojoClick(ActionEvent event) {
        ComprobacionesYcambioEscena.cambiarEscena(bt_parteRojo, "parte-rojo.fxml");
    }//onParteRojoClick

    @FXML
    void onParteVerdeClick(ActionEvent event) {
        ComprobacionesYcambioEscena.cambiarEscena(bt_parteRojo, "parte-verde.fxml");
    }//onParteVerdeClick

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nombre_profesor.setText(" " + GuardarProfesor.getProfesor().getNombre());
    }
}//class
