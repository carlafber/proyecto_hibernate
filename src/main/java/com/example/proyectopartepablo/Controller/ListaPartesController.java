package com.example.proyectopartepablo.Controller;

import com.example.proyectopartepablo.Dao.AlumnosDAO;
import com.example.proyectopartepablo.clases.Alumnos;
import com.example.proyectopartepablo.clases.ParteIncidencia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListaPartesController implements Initializable {

    @FXML
    private Pagination Pg_pagination;

    @FXML
    private Button bt_Buscar;

    @FXML
    private Button bt_BuscarFecha;

    @FXML
    private TableView<Alumnos> incidentTable;

    @FXML
    private TableColumn<?, ?> tv_alumnoColumn;

    @FXML
    private TableColumn<?, ?> tv_descripcionColumn;

    @FXML
    private TableColumn<?, ?> tv_expedienteColumn;

    @FXML
    private TableColumn<?, ?> tv_fechaColumn;

    @FXML
    private TableColumn<?, ?> tv_grupoColumn;

    @FXML
    private TableColumn<?, ?> tv_profesorColumn;

    @FXML
    private TableColumn<?, ?> tv_sancionColumn;

    @FXML
    private TextField txt_NumeroExpediente;

    @FXML
    private DatePicker txt_fecha;

    AlumnosDAO alumnosDAO = new AlumnosDAO();

    ObservableList<Alumnos> alumnosObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
