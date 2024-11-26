package com.example.proyecto_hibernate.controllers;

//import com.example.proyectopartepablo.Dao.AlumnosDAO;
import com.example.proyecto_hibernate.classes.Alumnos;
import com.example.proyecto_hibernate.classes.ParteIncidencia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListaPartesController implements Initializable {

    @FXML
    private Button bt_buscar;

    @FXML
    private Button bt_buscarFecha;

    @FXML
    private DatePicker dt_fechaFin;

    @FXML
    private DatePicker dt_fechaInicio;

    @FXML
    private ImageView imagen_fondo;

    @FXML
    private Pagination pagination;

    @FXML
    private TableColumn<?, ?> tc_botones;

    @FXML
    private TableColumn<?, ?> tc_descripcion;

    @FXML
    private TableColumn<?, ?> tc_expediente;

    @FXML
    private TableColumn<?, ?> tc_fecha;

    @FXML
    private TableColumn<?, ?> tc_grupo;

    @FXML
    private TableColumn<?, ?> tc_nombreAlumno;

    @FXML
    private TableColumn<?, ?> tc_profesor;

    @FXML
    private TableColumn<?, ?> tc_sancion;

    @FXML
    private TableView<?> tv_partes;

    @FXML
    private TextField txt_numExpediente;


    @FXML
    void onBuscarClick(ActionEvent event) {

    }

    @FXML
    void onBuscarFechaClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*tv_alumnoColumn.setCellValueFactory(new PropertyValueFactory<>("id_alumn"));
        tv_descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tv_expedienteColumn.setCellValueFactory(new PropertyValueFactory<>("numero_expediente"));
        tv_fechaColumn.setCellValueFactory(new PropertyValueFactory<>("puntos_acumulados"));
        tv_grupoColumn.setCellValueFactory(new PropertyValueFactory<>("id_grupo"));
        tv_profesorColumn.setCellValueFactory(new PropertyValueFactory<>("id_profesor"));
        tv_sancionColumn.setCellValueFactory(new PropertyValueFactory<>("sancion"));

        ArrayList<Alumnos> listaAlumnos = alumnosDAO.getAlumnos();
        alumnosObservableList = FXCollections.observableArrayList(listaAlumnos);
        incidentTable.setItems(alumnosObservableList);*/
    }


}
