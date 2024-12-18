package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.classes.TipoProfesor;
import com.example.proyecto_hibernate.util.CambioEscena;
import com.example.proyecto_hibernate.util.GuardarParte;
import com.example.proyecto_hibernate.util.GuardarProfesor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

//Clase que controla el inicio de sesión de un profesor
public class InicioProfesorController implements Initializable {

    //ATRIBUTOS
    @FXML
    private Button bt_crearParte;

    @FXML
    private Button bt_estadisticas;

    @FXML
    private Button bt_crearProfesor;

    @FXML
    private Button bt_listaAlumnos;

    @FXML
    private Button bt_listaPartes;

    @FXML
    private StackPane fondo;

    @FXML
    private ImageView imagen_fondo;

    private TipoProfesor tipoProfesor;


    //MÉTODOS
    //método que se ejecuta cuando se da al botón 'Crear parte' del navegador
    @FXML
    void onCrearParteClick(ActionEvent event) {
        GuardarParte.resetParte(); //llamar a la clase 'GuardarParte' para borrar el parte que tenga almacenado
        CambioEscena.abrirEscena("parte-verde.fxml", "Crear parte"); //cambiar de escena para crear un nuevo parte. Por defecto, se abre el verde
    }//onCrearParteClick


    //método que se ejecuta cuando se da al botón 'Crear profesor'
    @FXML
    void onCrearProfesorClick(ActionEvent event) {
        CambioEscena.abrirEscena("crear-profesor.fxml", "Crear profesor"); //cambiar de escena para crear un nuevo profesor
    }//onCrearProfesorClick


    //método que se ejecuta cuando se da al botón 'Estadísticas'
    @FXML
    public void onEstadisticasClick(ActionEvent event) {
        CambioEscena.abrirEscena("estadisticas.fxml", "Estadísticas"); //cambiar de escena para crear un nuevo profesor
    }//onEstadisticaClick


    //método que se ejecuta cuando se da al botón 'Listar alumnos'
    @FXML
    void onListaAlumnosClick(ActionEvent event) {
        CambioEscena.abrirEscena("lista-alumnos.fxml", "Lista alumnos"); //cambiar de escena para listar los alumnos
    }//onListaAlumnosClick


    //método que se ejecuta cuando se da al botón 'Listar partes'
    @FXML
    void onListaPartesClick(ActionEvent event) {
        CambioEscena.abrirEscena("lista-partes.fxml", "Lista partes"); //cambiar de escena para listar los partes
    }//onListaPartesClick


    //método que inicializa la pantalla al abrirse
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //adaptar la imagen al tamaño de la pantalla
        imagen_fondo.fitWidthProperty().bind(fondo.widthProperty());
        imagen_fondo.fitHeightProperty().bind(fondo.heightProperty());

        //obtener el tipo de profesor a través de los datos que se almacenaron al iniciar sesión
        tipoProfesor = GuardarProfesor.getProfesor().getTipo();
        //si el 'tipoProfesor' es profesor, no tiene acceso a la mayoria de métodos, por lo que se ocultan los botones a los que solo tiene acceso el jefe de estudios
        if(tipoProfesor.equals(TipoProfesor.profesor)){
            ocultarBotones();
        }//if
    }//initialize


    //método para ocultar los botones
    private void ocultarBotones(){
        bt_listaPartes.setVisible(false);
        bt_listaAlumnos.setVisible(false);
        bt_crearProfesor.setVisible(false);
        bt_estadisticas.setVisible(false);
    }//ocultarBotones


}//class

