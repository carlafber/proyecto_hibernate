package com.example.proyectopartepablo.Controller;

import com.example.proyectopartepablo.Dao.AlumnosDAO;
import com.example.proyectopartepablo.Dao.PartesDAO;
import com.example.proyectopartepablo.clases.Alumnos;
import com.example.proyectopartepablo.clases.Grupos;
import com.example.proyectopartepablo.clases.ParteIncidencia;
import com.example.proyectopartepablo.clases.Profesor;
import com.example.proyectopartepablo.utils.Alerta;
import com.example.proyectopartepablo.utils.HibernateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;

import java.net.URL;
import java.time.LocalDate;
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
    private TableView<ParteIncidencia> tableView;

    @FXML
    private TableColumn<ParteIncidencia, String> tv_alumnoColumn;

    @FXML
    private TableColumn<ParteIncidencia, String> tv_descripcionColumn;

    @FXML
    private TableColumn<ParteIncidencia, String> tv_expedienteColumn;

    @FXML
    private TableColumn<ParteIncidencia, LocalDate> tv_fechaColumn;

    @FXML
    private TableColumn<ParteIncidencia, String> tv_grupoColumn;

    @FXML
    private TableColumn<ParteIncidencia, String> tv_profesorColumn;

    @FXML
    private TableColumn<ParteIncidencia, String> tv_sancionColumn;

    @FXML
    private TextField txt_NumeroExpediente;

    @FXML
    private DatePicker txt_fecha;

    @FXML
    private DatePicker txt_fecha2;

    private PartesDAO partesDAO = new PartesDAO();

    private  ParteIncidencia parteIncidencia = new ParteIncidencia();

    private FilteredList<ParteIncidencia> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        tv_expedienteColumn.setCellValueFactory(cellData -> {
            Alumnos alumnos = cellData.getValue().getAlumno();
            if (alumnos != null) {
                return new SimpleStringProperty(alumnos.getNumero_expediente());
            } else {
                return new SimpleStringProperty("Sin Expediente");
            }
        });
        tv_alumnoColumn.setCellValueFactory(cellData -> {
            Alumnos alumnos = cellData.getValue().getAlumno();
            if (alumnos != null) {
                return new SimpleStringProperty(alumnos.getNombre_alum());
            } else {
                return new SimpleStringProperty("Sin nombre");
            }
        });
        tv_grupoColumn.setCellValueFactory(cellData -> {
            Grupos grupo = cellData.getValue().getGrupo();
            if (grupo != null) {
                return new SimpleStringProperty(grupo.getNombreGrupo());
            } else {
                return new SimpleStringProperty("Sin Grupo");
            }
        });
        tv_profesorColumn.setCellValueFactory(cellData -> {
            Profesor profesor = cellData.getValue().getProfesor();
            if (profesor != null) {
                return new SimpleStringProperty(profesor.getNombre());
            } else {
                return new SimpleStringProperty("Sin Nombre");
            }
        });

        tv_sancionColumn.setCellValueFactory(new PropertyValueFactory<>("sancion"));
        tv_fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tv_descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));



        ArrayList <ParteIncidencia> listaPartesIncidencia = partesDAO.getPartesHibernate();
        ObservableList<ParteIncidencia> parteIncidenciasObservable = FXCollections.observableArrayList(listaPartesIncidencia);

        filteredList = new FilteredList<>(parteIncidenciasObservable, alumno -> true);
        tableView.setItems(filteredList);
        configurarPaginacion(filteredList);
    }

    private void configurarPaginacion(ObservableList<ParteIncidencia> listaCompleta) {
        int filasPorPagina = 5; // Número de filas por página

        // Configurar el Pagination
        Pg_pagination.setPageCount((int) Math.ceil((double) listaCompleta.size() / filasPorPagina));
        Pg_pagination.setCurrentPageIndex(0);

        // Listener para cambiar de página
        Pg_pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            cambiarPagina(listaCompleta, filasPorPagina, newValue.intValue());
        });

        // Mostrar la primera página
        cambiarPagina(listaCompleta, filasPorPagina, 0);
    }

    private void cambiarPagina(ObservableList<ParteIncidencia> listaCompleta, int filasPorPagina, int paginaActual) {
        int desdeIndice = paginaActual * filasPorPagina;
        int hastaIndice = Math.min(desdeIndice + filasPorPagina, listaCompleta.size());

        ObservableList<ParteIncidencia> paginaActualLista = FXCollections.observableArrayList(listaCompleta.subList(desdeIndice, hastaIndice));

        tableView.setItems(paginaActualLista);
    }
    @FXML
    public void onClickBorrar(javafx.event.ActionEvent actionEvent) {
        filteredList.setPredicate(alumno -> true);
        txt_NumeroExpediente.clear();
        txt_fecha.setValue(null);
        txt_fecha2.setValue(null);
    }

    @FXML
    public void onClickBuscarFecha(ActionEvent actionEvent) {
        LocalDate fechaInicio = txt_fecha.getValue();
        LocalDate fechaFin = txt_fecha2.getValue();

        if (fechaInicio == null || fechaFin == null) {
            Alerta.alertaWarning("Error");
            Alerta.alertaError("Campos vacíos");
            Alerta.alertaInfo("Por favor, selecciona ambas fechas para buscar.");
            return;
        }

        if (fechaInicio.isAfter(fechaFin)) {
            Alerta.alertaWarning("Error");
            Alerta.alertaError("Rango de fechas inválido");
            Alerta.alertaInfo("La fecha inicial no puede ser posterior a la fecha final.");
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
                Alerta.alertaWarning("Resultado");
                Alerta.alertaError("No se encontraron partes");
                Alerta.alertaInfo("No se encontraron partes entre las fechas: " + fechaInicio + " y " + fechaFin);

                filteredList.setPredicate(parte -> true);
            }
        } catch (Exception e) {
            Alerta.alertaWarning("Error");
            Alerta.alertaError("Error inesperado");
            Alerta.alertaInfo("Ocurrió un error al buscar por rango de fechas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onClickBuscar(ActionEvent actionEvent) {
        String numeroExpediente = txt_NumeroExpediente.getText();

        if (numeroExpediente == null || numeroExpediente.isEmpty()) {
            Alerta.alertaWarning("Error");
            Alerta.alertaError("Campo vacío");
            Alerta.alertaInfo("Por favor, introduce un número de expediente válido.");
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
                Alerta.alertaWarning("Resultado");
                Alerta.alertaError("Parte no encontrado");
                Alerta.alertaInfo("No se encontró ningún parte con el expediente: " + numeroExpediente);

                filteredList.setPredicate(parte -> true);
            }
        } catch (Exception e) {
            Alerta.alertaWarning("Error");
            Alerta.alertaError("Error inesperado");
            Alerta.alertaInfo("Ocurrió un error al buscar: " + e.getMessage());
        }
    }
}