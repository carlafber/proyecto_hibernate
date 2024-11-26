package com.example.proyecto_hibernate.controllers;

//import com.example.proyectopartepablo.Dao.AlumnosDAO;
import com.example.proyecto_hibernate.classes.Alumnos;

import com.example.proyecto_hibernate.util.Alerta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListaAlumnosController implements Initializable {

    @FXML
    private Pagination Pg_pagination;

    @FXML
    private Button bt_Buscar;

    @FXML
    private TableView<Alumnos> tableView;

    @FXML
    private TableColumn<?, ?> tv_nombreAlumno;

    @FXML
    private TableColumn<?, ?> tv_nombreGrupo;

    @FXML
    private TableColumn<?, ?> tv_numeroExpediente;

    @FXML
    private TableColumn<?, ?> tv_puntosAcumulados;

    @FXML
    private TextField txt_NumeroExpediente;

    //AlumnosDAO alumnosDAO = new AlumnosDAO();

    //ObservableList <Alumnos> alumnosObservableList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*tv_nombreAlumno.setCellValueFactory(new PropertyValueFactory<>("nombre_alum"));
        tv_nombreGrupo.setCellValueFactory(new PropertyValueFactory<>("nombre_grupo"));
        tv_numeroExpediente.setCellValueFactory(new PropertyValueFactory<>("numero_expediente"));
        tv_puntosAcumulados.setCellValueFactory(new PropertyValueFactory<>("puntos_acumulados"));

        ArrayList<Alumnos> listaAlumnos = alumnosDAO.getAlumnosHibernate();
        alumnosObservableList = FXCollections.observableArrayList(listaAlumnos);
        tableView.setItems(alumnosObservableList);*/
    }

    public void onClickBuscar(javafx.event.ActionEvent actionEvent) {
        /*String textoNumeroExpediente = txt_NumeroExpediente.getText();

        if(textoNumeroExpediente == null || textoNumeroExpediente.isEmpty()) {
            Alerta.mensajeError("Campo vacío", "Por favor, introduce un numero valido");
        } else {
            int numeroExpediente = Integer.parseInt(textoNumeroExpediente);


            Alumnos alumnos = alumnosDAO.buscarPorExpediente(numeroExpediente);

            if (alumnos != null) {
                Alerta.mensajeInfo("ÉXITO!", "Alumno encontrado","Detalles del alumno:\n" + alumnos);
            } else {
                Alerta.mensajeError("Alumno no encontrado", "No se encontró ningúno alumno con el expediente: " + numeroExpediente);
            }
        }*/
    }
}
