package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.CRUD.PartesCRUD;
import com.example.proyecto_hibernate.classes.Alumnos;
import com.example.proyecto_hibernate.classes.Grupos;
import com.example.proyecto_hibernate.classes.ParteIncidencia;
import com.example.proyecto_hibernate.classes.Profesor;
import com.example.proyecto_hibernate.util.Alerta;
import com.example.proyecto_hibernate.util.HibernateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import org.hibernate.Session;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ListaPartesController implements Initializable {

    @FXML
    private Button bt_borrar;

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
    private TableColumn<ParteIncidencia, Button> tc_botones;

    @FXML
    private TableColumn<ParteIncidencia, String> tc_descripcion;

    @FXML
    private TableColumn<ParteIncidencia, String> tc_expediente;

    @FXML
    private TableColumn<ParteIncidencia, String> tc_fecha;

    @FXML
    private TableColumn<ParteIncidencia, String> tc_grupo;

    @FXML
    private TableColumn<ParteIncidencia, String> tc_nombreAlumno;

    @FXML
    private TableColumn<ParteIncidencia, String> tc_profesor;

    @FXML
    private TableColumn<ParteIncidencia, String> tc_sancion;

    @FXML
    private TableView<ParteIncidencia> tv_partes;

    @FXML
    private TextField txt_numExpediente;

    private PartesCRUD partesCRUD = new PartesCRUD();

    private  ParteIncidencia parteIncidencia = new ParteIncidencia();

    private FilteredList<ParteIncidencia> filteredList;

    private Session session;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        session = HibernateUtil.getSessionFactory().openSession();

        tc_expediente.setCellValueFactory(cellData -> {
            Alumnos alumnos = cellData.getValue().getAlumno();
            return new SimpleStringProperty(alumnos.getNumero_expediente());
        });

        tc_nombreAlumno.setCellValueFactory(cellData -> {
            Alumnos alumnos = cellData.getValue().getAlumno();
            return new SimpleStringProperty(alumnos.getNombre_alum());
        });

        tc_grupo.setCellValueFactory(cellData -> {
            Grupos grupo = cellData.getValue().getGrupo();
            return new SimpleStringProperty(grupo.getNombreGrupo());
        });

        tc_profesor.setCellValueFactory(cellData -> {
            Profesor profesor = cellData.getValue().getProfesor();
            return new SimpleStringProperty(profesor.getNombre());
        });

        tc_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tc_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tc_sancion.setCellValueFactory(new PropertyValueFactory<>("sancion"));

        tc_botones.setCellFactory(column -> new TableCell<>() {
            private final Button bt_verMas = new Button("Ver Más");
            {
                bt_verMas.setOnAction(event -> {
                    ParteIncidencia parte = getTableView().getItems().get(getIndex());

                    System.out.println("Botón clicado para: " + parte);
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(bt_verMas);
                }
            }
        });

        ArrayList<ParteIncidencia> listaPartesIncidencia = partesCRUD.getPartes();
        ObservableList<ParteIncidencia> parteIncidenciasObservable = FXCollections.observableArrayList(listaPartesIncidencia);

        filteredList = new FilteredList<>(parteIncidenciasObservable, alumno -> true);
        tv_partes.setItems(filteredList);
        configurarPaginacion(filteredList);
    }


    @FXML
    void onBorrarClick(ActionEvent event) {
        filteredList.setPredicate(alumno -> true);
        txt_numExpediente.clear();
        dt_fechaInicio.setValue(null);
        dt_fechaFin.setValue(null);
    }

    @FXML
    void onBuscarClick(ActionEvent event) {
        String numeroExpediente = txt_numExpediente.getText();

        if (numeroExpediente == null || numeroExpediente.isEmpty()) {
            Alerta.mensajeError("Campo vacío", "Por favor, introduce un número de expediente válido.");
            return;
        }

        try {
            filteredList.setPredicate(parte -> {
                Alumnos alumno = parte.getAlumno();
                if (alumno != null) {
                    return alumno.getNumero_expediente().equals(numeroExpediente);
                }
                return false;
            });

            if (filteredList.isEmpty()) {
                Alerta.mensajeError("Parte no encontrado", "No se encontró ninguna parte con el expediente: " + numeroExpediente);

                filteredList.setPredicate(parte -> true);
            }
        } catch (Exception e) {
            Alerta.mensajeError("Error inesperado", "Ocurrio un error al buscar: " + e.getMessage());
        }
    }

    @FXML
    void onBuscarFechaClick(ActionEvent event) {
        LocalDate fechaInicio = dt_fechaInicio.getValue();
        LocalDate fechaFin = dt_fechaFin.getValue();

        if (fechaInicio == null || fechaFin == null) {
            Alerta.mensajeError("Campos vacíos", "Por favor, selecciona ambas fechas para buscar.");
            return;
        }

        if (fechaInicio.isAfter(fechaFin)) {
            Alerta.mensajeError("Rango de fechas inválido", "La fecha inicial no puede ser posterior a la fecha final.");
            return;
        }

        try {
            filteredList.setPredicate(parte -> {
                if (parte.getFecha() != null) {
                    return !parte.getFecha().isBefore(fechaInicio) && !parte.getFecha().isAfter(fechaFin);
                }
                return false;
            });

            if (filteredList.isEmpty()) {
                Alerta.mensajeError("No se encontraron partes", "No se encontraron partes entre las fechas: " + fechaInicio + " y " + fechaFin);

                filteredList.setPredicate(parte -> true);
            }
        } catch (Exception e) {
            Alerta.mensajeError("Error inesperado", "Ocurrio un error al buscar por rango de fechas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configurarPaginacion(ObservableList<ParteIncidencia> listaCompleta) {
        int filasPorPagina = 5; // Número de filas por página

        // Configurar el Pagination
        pagination.setPageCount((int) Math.ceil((double) listaCompleta.size() / filasPorPagina));
        pagination.setCurrentPageIndex(0);

        // Listener para cambiar de página
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            cambiarPagina(listaCompleta, filasPorPagina, newValue.intValue());
        });

        // Mostrar la primera página
        cambiarPagina(listaCompleta, filasPorPagina, 0);
    }

    private void cambiarPagina(ObservableList<ParteIncidencia> listaCompleta, int filasPorPagina, int paginaActual) {
        int desdeIndice = paginaActual * filasPorPagina;
        int hastaIndice = Math.min(desdeIndice + filasPorPagina, listaCompleta.size());

        ObservableList<ParteIncidencia> paginaActualLista = FXCollections.observableArrayList(listaCompleta.subList(desdeIndice, hastaIndice));

        tv_partes.setItems(paginaActualLista);
    }



}
