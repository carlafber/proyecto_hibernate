package com.example.proyectopartepablo.Controller;

import com.example.proyectopartepablo.clases.ParteIncidencia;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ListaPartesController {

    @FXML
    private Pagination Pg_pagination;

    @FXML
    private Button bt_Buscar;

    @FXML
    private Button bt_BuscarFecha;

    @FXML
    private TableView<ParteIncidencia> incidentTable;

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

}
