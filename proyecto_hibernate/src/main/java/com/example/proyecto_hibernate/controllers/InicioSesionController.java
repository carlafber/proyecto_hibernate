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
import org.mindrot.jbcrypt.BCrypt;

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
    @FXML
    void onIniciarSesionClick(ActionEvent event) {
        boolean encontrado = false; // Variable para controlar si se ha encontrado

        // Comprobar que los campos no estén vacíos
        if (txt_numero.getText().isEmpty() || pwd_contrasena.getText().isEmpty()) {
            Alerta.mensajeError("Campos vacíos", "Por favor, completa todos los campos.");
        } else { // Si no están vacíos los campos
            // Recorrer todos los profesores de la BD llamando al método 'obtenerProfesores'
            for (Profesores profe : profesoresCRUD.obtenerProfesores()) {
                // Comprobar que el ID introducido coincide con alguno de la BD
                if (txt_numero.getText().equals(profe.getNumero_asignado())) {
                    // Si coincide, comprobar la contraseña de ese ID usando SHA-256
                    String contrasenaEncriptada = profesoresCRUD.encriptarSHA256(pwd_contrasena.getText());
                    if (contrasenaEncriptada.equals(profe.getContrasena())) {
                        GuardarProfesor.setProfesor(profe); // Guardar los datos de ese profesor en la clase 'GuardarProfesor' para un futuro uso
                        CambioEscena.abrirEscena("inicio-profesor.fxml", null); // Abrir la pantalla principal
                    } else { // Si no coincide la contraseña con el ID, mostrar error de no coincidencia
                        Alerta.mensajeError("Contraseña incorrecta", "Por favor, introduce la contraseña correcta.");
                    }
                    encontrado = true; // Actualizar la variable si existe el ID en la BD
                    break; // Salir del bucle, ya que encontramos el profesor
                }
            }

            // Si no se encuentra el ID introducido por el usuario, mostrar error
            if (!encontrado) {
                Alerta.mensajeError("Profesor no existe", "Por favor, introduce un número de profesor válido.");
            }
        }

        // Limpiar los campos
        txt_numero.clear();
        pwd_contrasena.clear();
    }

    //método que se ejecuta cuando se abre la pantalla
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //adaptar la imagen al tamaño de la pantalla
        imagen_fondo.fitWidthProperty().bind(fondo.widthProperty());
        imagen_fondo.fitHeightProperty().bind(fondo.heightProperty());
    }//initialize
}//class
