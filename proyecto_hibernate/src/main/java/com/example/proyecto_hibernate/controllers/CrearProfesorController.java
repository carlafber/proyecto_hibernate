package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.classes.TipoProfesor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CrearProfesorController implements Initializable {

    @FXML
    private Button bt_crear;

    @FXML
    private ComboBox<String> cb_tipo;

    @FXML
    private ImageView imagen_fondo;

    @FXML
    private PasswordField pwd_contrasena;

    @FXML
    private TextField txt_nombre;

    @FXML
    private TextField txt_numero;

    @FXML
    void onCrearClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(TipoProfesor tipo : TipoProfesor.values()) {
            if(tipo == TipoProfesor.profesor){
                cb_tipo.getItems().add("Profesor");
            } else if (tipo == TipoProfesor.jefe_de_estudios) {
                cb_tipo.getItems().add("Jefe de estudios");
            }
        }
    }
}
