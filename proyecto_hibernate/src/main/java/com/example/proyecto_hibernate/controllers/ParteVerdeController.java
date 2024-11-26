package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.util.ComprobacionesYcambioEscena;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ParteVerdeController implements Initializable {

    //ATRIBUTOS
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
    private TextArea txt_descripcion;

    @FXML
    private TextField txt_expedienteAlumno;

    @FXML
    private TextField txt_nombreGrupo;

    @FXML
    private TextField txt_profesor;


    //MÉTODOS
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }//initialize


    @FXML
    void onParteNaranjaClick(ActionEvent event) {
        ComprobacionesYcambioEscena.cambiarEscena(bt_parteNaranja, "parte-naranja.fxml");
    }//onParteNaranjaClick

    @FXML
    void onParteRojoClick(ActionEvent event) {
        ComprobacionesYcambioEscena.cambiarEscena(bt_parteRojo, "parte-rojo.fxml");
    }//onParteRojoClick
}//class
