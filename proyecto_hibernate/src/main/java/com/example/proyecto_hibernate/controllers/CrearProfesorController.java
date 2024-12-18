package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.CRUD.*;
import com.example.proyecto_hibernate.classes.*;
import com.example.proyecto_hibernate.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;

import java.net.URL;
import java.util.*;

//Clase que controla la creación de un nuevo profesor
public class CrearProfesorController implements Initializable {

    //ATRIBUTOS
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

    private ProfesoresCRUD profesoresCRUD = new ProfesoresCRUD(); //instancia de la clase CRUD para realizar operaciones


    //MÉTODOS
    //método para crear profesor
    @FXML
    void onCrearClick(ActionEvent event) {
        TipoProfesor tipo = null; //declaración del tipo de profesor a nulo

        //comprobar que nigún campo está vacío
        if (txt_nombre.getText().isEmpty() || txt_numero.getText().isEmpty() || pwd_contrasena.getText().isEmpty() || cb_tipo.getSelectionModel().getSelectedItem() == null) {
            Alerta.mensajeError("Campos vacíos", "Por favor, completa todos los campos.");
        } else { //si todos los campos están rellenos
            //asignar a la variable 'tipo' el tipo de profesor que se ha seleccionado en el comboBox
            if (cb_tipo.getSelectionModel().getSelectedItem().equals("Profesor")) {
                tipo = TipoProfesor.profesor;
            } else if (cb_tipo.getSelectionModel().getSelectedItem().equals("Jefe de estudios")) {
                tipo = TipoProfesor.jefe_de_estudios;
            }//if-elseif

            //crear un nuevo profesor con los datos introducidos
            Profesores profesor = new Profesores(pwd_contrasena.getText(), txt_nombre.getText(), txt_numero.getText(), tipo);

            //si se ha creado correctamente, se muestra mensaje de éxito
            if (profesoresCRUD.crearProfesor(profesor)) {
                Alerta.mensajeInfo("ÉXITO", "Profesor creado", "El profesor ha sido creado correctamente.");
            } else { // si no, mensaje de error
                Alerta.mensajeError("Profesor duplicado", "No puede haber dos profesores con el mismo número asignado.");
            }//if-else

            limpiarCampos(); //limpiar los campos de texto
        }//if-else
    }//onCrearClick


    //método para inicializar la pantalla según se abre
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //inicializar el comboBox
        cb_tipo.getItems().add("Profesor");
        cb_tipo.getItems().add("Jefe de estudios");
    }//initialize


    //método para limpiar los campos que contienen valores
    private void limpiarCampos() {
        txt_nombre.clear();
        txt_numero.clear();
        pwd_contrasena.clear();
        cb_tipo.setValue(null);
    }//limpiarCampos
}//class
