package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.CRUD.ProfesorCRUD;
import com.example.proyecto_hibernate.classes.Profesor;
import com.example.proyecto_hibernate.classes.TipoProfesor;
import com.example.proyecto_hibernate.util.Alerta;
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

    private ProfesorCRUD profesorCRUD = new ProfesorCRUD();

    @FXML
    void onCrearClick(ActionEvent event) {
        TipoProfesor tipo = null;
        if (txt_nombre.getText().isEmpty() || txt_numero.getText().isEmpty() || pwd_contrasena.getText().isEmpty() || cb_tipo.getSelectionModel().getSelectedItem() == null) {
            Alerta.mensajeError("Campos vacíos", "Por favor, completa todos los campos.");
        } else {
            if(cb_tipo.getSelectionModel().getSelectedItem().equals("Profesor")){
                tipo = TipoProfesor.profesor;
            } else if (cb_tipo.getSelectionModel().getSelectedItem().equals("Jefe de estudios")) {
                tipo = TipoProfesor.jefe_de_estudios;
            }
            Profesor profesor = new Profesor(pwd_contrasena.getText(), txt_nombre.getText(), txt_numero.getText(), tipo);
            profesorCRUD.crearProfesor(profesor);
            Alerta.mensajeInfo("ÉXITO", "Profesor creado", "El profesor ha sido creado correctamente.");
            limpiarCampos();
            //FALTA ENCRIPTAR CONTRASEÑAS
            //FALTA COMPROBAR QUE EXISTE EL PROFESOR
        }
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

    private void limpiarCampos() {
        txt_nombre.clear();
        txt_numero.clear();
        pwd_contrasena.clear();
        cb_tipo.setValue(null);
    }
}
