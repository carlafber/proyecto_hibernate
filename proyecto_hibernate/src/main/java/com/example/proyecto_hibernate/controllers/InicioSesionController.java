package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.CRUD.AlumnosCRUD;
import com.example.proyecto_hibernate.CRUD.ProfesorCRUD;
import com.example.proyecto_hibernate.classes.Alumnos;
import com.example.proyecto_hibernate.classes.Profesor;
import com.example.proyecto_hibernate.classes.TipoProfesor;
import com.example.proyecto_hibernate.util.Alerta;
import com.example.proyecto_hibernate.util.ComprobacionesYcambioEscena;
import com.example.proyecto_hibernate.util.GuardarProfesor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

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
            for(Profesor p : profesorCRUD.getProfesores()){
                if(txt_numero.getText().equals(p.getNumeroAsignado())){
                    if(pwd_contrasena.getText().equals(p.getContrasena())) {
                        if(p.getTipo().equals(TipoProfesor.jefe_de_estudios)){
                            ComprobacionesYcambioEscena.abrirEscena("inicio-jefe-estudios.fxml", "Jefe de estudios");
                        } else {
                            ComprobacionesYcambioEscena.abrirEscena("inicio-profesor.fxml", "Profesor");
                        }
                    } else {
                        Alerta.mensajeError("Contrasena incorrecta", "Por favor, introduce la contrasena correcta.");
                    }
                    encontrado = true;
                    GuardarProfesor.setProfesor(p);
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
