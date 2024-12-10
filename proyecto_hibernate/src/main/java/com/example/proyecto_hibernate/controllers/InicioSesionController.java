package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.CRUD.ProfesorCRUD;
import com.example.proyecto_hibernate.classes.Profesores;
import com.example.proyecto_hibernate.util.Alerta;
import com.example.proyecto_hibernate.util.CambioEscena;
import com.example.proyecto_hibernate.util.GuardarProfesor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class InicioSesionController implements Initializable {

    @FXML
    private Button bt_iniciar_sesion;

    @FXML
    private StackPane fondo;

    @FXML
    private ImageView imagen_fondo;

    //@FXML
    //private VBox inicio;

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
            for(Profesores profe : profesorCRUD.obtenerProfesores()){
                if(txt_numero.getText().equals(profe.getNumero_asignado())){
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imagen_fondo.fitWidthProperty().bind(fondo.widthProperty());
        imagen_fondo.fitHeightProperty().bind(fondo.heightProperty());
    }
}
