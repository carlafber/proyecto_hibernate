package com.example.proyectopartepablo.Controller;

import com.example.proyectopartepablo.Dao.AlumnosDAO;
import com.example.proyectopartepablo.clases.Alumnos;
import com.example.proyectopartepablo.clases.Grupos;
import com.example.proyectopartepablo.clases.ParteIncidencia;
import com.example.proyectopartepablo.utils.Alerta;
import com.example.proyectopartepablo.utils.HibernateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListaAlumnosController implements Initializable {

    @FXML
    private Pagination Pg_pagination;

    @FXML
    private Button bt_Buscar;

    @FXML
    private Button bt_Reset;
    @FXML
    private TableView<Alumnos> tableView;

    @FXML
    private TableColumn<Alumnos, String> tv_nombreAlumno;

    @FXML
    private TableColumn<Alumnos, String> tv_nombreGrupo;

    @FXML
    private TableColumn<Alumnos, String> tv_numeroExpediente;

    @FXML
    private TableColumn<Alumnos, String> tv_puntosAcumulados;

    @FXML
    private TextField txt_NumeroExpediente;

    private AlumnosDAO alumnosDAO = new AlumnosDAO();

    private ObservableList<Alumnos> alumnosObservableList;

    private FilteredList<Alumnos> filteredList;

    private Session session;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        session = HibernateUtil.getSessionFactory().openSession();

        tv_nombreAlumno.setCellValueFactory(new PropertyValueFactory<>("nombre_alum"));
        tv_numeroExpediente.setCellValueFactory(new PropertyValueFactory<>("numero_expediente"));
        tv_puntosAcumulados.setCellValueFactory(new PropertyValueFactory<>("puntos_acumulados"));

        tv_nombreGrupo.setCellValueFactory(cellData -> {
            Grupos grupo = cellData.getValue().getGrupo(); // Obtener el grupo del alumno
            if (grupo != null) {
                return new SimpleStringProperty(grupo.getNombreGrupo());
            } else {
                return new SimpleStringProperty("Sin Grupo"); // Valor por defecto si no tiene grupo
            }
        });
        ArrayList<Alumnos> listaAlumnos = alumnosDAO.getAlumnosHibernate();
        alumnosObservableList = FXCollections.observableArrayList(listaAlumnos);

        // Crear el FilteredList basado en la lista original
        filteredList = new FilteredList<>(alumnosObservableList, alumno -> true);
        tableView.setItems(filteredList);
        configurarPaginacion(filteredList);
    }

    private void configurarPaginacion(ObservableList<Alumnos> listaCompleta) {
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

    private void cambiarPagina(ObservableList<Alumnos> listaCompleta, int filasPorPagina, int paginaActual) {
        int desdeIndice = paginaActual * filasPorPagina;
        int hastaIndice = Math.min(desdeIndice + filasPorPagina, listaCompleta.size());

        ObservableList<Alumnos> paginaActualLista = FXCollections.observableArrayList(listaCompleta.subList(desdeIndice, hastaIndice));

        tableView.setItems(paginaActualLista);
    }
    @FXML
    public void onClickBuscar(javafx.event.ActionEvent actionEvent) {
        String numeroExpediente = txt_NumeroExpediente.getText(); // Recojo el número de expediente

        if (numeroExpediente == null || numeroExpediente.isEmpty()) {
            Alerta.alertaWarning("Error");
            Alerta.alertaError("Campo Vacio");
            Alerta.alertaInfo("Por favor, introduce un número válido");
            return;
        }

        try {
            // Actualizar el filtro para mostrar solo el alumno con el ID buscado
            filteredList.setPredicate(alumno -> alumno.getNumero_expediente().equals(numeroExpediente));

            // Comprobar si hay algún resultado
            if (filteredList.isEmpty()) {
                Alerta.alertaWarning("Resultado");
                Alerta.alertaError("Alumno no encontrado");
                Alerta.alertaInfo("No se encontró ningún alumno con el expediente:"  + numeroExpediente);

                // Restablecer el filtro si no hay resultados
                filteredList.setPredicate(alumno -> true);
            }
        } catch (NumberFormatException e) {
            Alerta.alertaWarning("Error");
            Alerta.alertaError("Formato no válido");
            Alerta.alertaInfo("Por favor, introduce un número de expediente válido.");
        }
    }

    @FXML
    public void onResetClicked(javafx.event.ActionEvent actionEvent) {
        filteredList.setPredicate(alumno -> true);
        txt_NumeroExpediente.clear();
    }
}
