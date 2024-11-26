package com.example.proyecto_hibernate.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class CrearProfesorController {

    @FXML
    private Button bt_crear;

    @FXML
    private ComboBox<?> cb_tipo;

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

}
