package com.example.proyecto_hibernate.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;


public class ListaPartesController {

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



}
