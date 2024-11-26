package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.util.ComprobacionesYcambioEscena;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class InicioSesionController {

    @FXML
    private Button bt_iniciar_sesion;

    @FXML
    private ImageView imagen_fondo;

    @FXML
    private PasswordField pwd_contrasena;

    @FXML
    private TextField txt_numero;

    @FXML
    void onIniciarSesionClick(ActionEvent event) {
        //comprobar que no este vacio
        //comprobar que exista
        //comprobar si es profesor o jefe de estudios
        ComprobacionesYcambioEscena.abrirEscena("inicio-jefe-estudios.fxml", "Jefe de estudios");
    }

}
