package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.CRUD.ProfesoresCRUD;
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

//Clase que controla el inicio de sesión
public class InicioSesionController implements Initializable {

    //ATRIBUTOS
    @FXML
    private Button bt_iniciar_sesion;

    @FXML
    private StackPane fondo;

    @FXML
    private ImageView imagen_fondo;

    @FXML
    private PasswordField pwd_contrasena;

    @FXML
    private TextField txt_numero;

    private ProfesoresCRUD profesoresCRUD = new ProfesoresCRUD(); //instancia de la clase CRUD para realizar operaciones


    //MÉTODOS
    //método que se ejecuta cuando se clica sobre el botón 'Iniciar sesión'
    @FXML
    void onIniciarSesionClick(ActionEvent event) {
        boolean encontrado = false; //variable para controlar si se ha enocntrado

        //comprobar que los campos no estén vacíos
        if(txt_numero.getText().isEmpty() || pwd_contrasena.getText().isEmpty()){
            Alerta.mensajeError("Campos vacíos", "Por favor, completa todos los campos.");
        } else { //si no están vacíos los campos
            //recorrer todos los profesores de la BD llamando al método 'obtenerProfesores'
            for(Profesores profe : profesoresCRUD.obtenerProfesores()){
                //comprobar que el id introducido coincide con alguno de la BD
                if(txt_numero.getText().equals(profe.getNumero_asignado())){
                    //si coinicde, comprobar la contraseña de ese id
                    if(pwd_contrasena.getText().equals(profe.getContrasena())) {
                        GuardarProfesor.setProfesor(profe); //guadar los datos de ese profesor en la clase 'GuardarProfesor' para un futuro uso
                        CambioEscena.abrirEscena("inicio-profesor.fxml", null); //abrir la pantalla principal
                    } else { //si no coincide la contraseña con el id, mostrar error de no coincidencia
                        Alerta.mensajeError("Contrasena incorrecta", "Por favor, introduce la contrasena correcta.");
                    }//if-else
                    encontrado = true; //actualiar la variabe si existe el id en la BD
                }//if
            }//for

            //si no se encuentra el id introducido por el usuario, mostrar error
            if(!encontrado){
                Alerta.mensajeError("Profesor no existe", "Por favor, introduce un número de profesor valido.");
            }
        }//if-else

        //limpiar los campos
        txt_numero.clear();
        pwd_contrasena.clear();
    }//onIniciarSesionClick


    //método que se ejecuta cuando se abre la pantalla
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //adaptar la imagen al tamaño de la pantalla
        imagen_fondo.fitWidthProperty().bind(fondo.widthProperty());
        imagen_fondo.fitHeightProperty().bind(fondo.heightProperty());
    }//initialize
}//class
