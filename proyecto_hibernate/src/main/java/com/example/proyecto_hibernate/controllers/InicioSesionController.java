package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.CRUD.ProfesorCRUD;
import com.example.proyecto_hibernate.classes.Profesor;
import com.example.proyecto_hibernate.util.Alerta;
import com.example.proyecto_hibernate.util.CambioEscena;
import com.example.proyecto_hibernate.util.GuardarProfesor;
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

    private ProfesorCRUD profesorCRUD = new ProfesorCRUD();

    @FXML
    void onIniciarSesionClick(ActionEvent event) {
        boolean encontrado = false;
        if(txt_numero.getText().isEmpty() || pwd_contrasena.getText().isEmpty()){
            Alerta.mensajeError("Campos vacíos", "Por favor, completa todos los campos.");
        } else {
            for(Profesor profe : profesorCRUD.getProfesores()){
                if(txt_numero.getText().equals(profe.getNumeroAsignado())){
                    if(pwd_contrasena.getText().equals(profe.getContrasena())) {
                        GuardarProfesor.setProfesor(profe);
                        CambioEscena.abrirEscena("inicio-profesor.fxml", null);
                    } else {
                        Alerta.mensajeError("Contrasena incorrecta", "Por favor, introduce la contrasena correcta.");
                    }
                    encontrado = true;
                }
            }

            if(!encontrado){
                Alerta.mensajeError("Profesor no existe", "Por favor, introduce un número de profesor valido.");
            }
        }
        txt_numero.clear();
        pwd_contrasena.clear();
    }
}
