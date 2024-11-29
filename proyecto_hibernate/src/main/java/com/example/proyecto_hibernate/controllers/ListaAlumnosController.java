package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.classes.Alumnos;
import com.example.proyecto_hibernate.CRUD.AlumnosCRUD;

import com.example.proyecto_hibernate.classes.Grupos;
import com.example.proyecto_hibernate.util.Alerta;
import com.example.proyecto_hibernate.util.HibernateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListaAlumnosController implements Initializable {

    @FXML
    private Button bt_buscar;

    @FXML
    private ImageView imagen_fondo;

    @FXML
    private Pagination pagination;

    @FXML
    private TableColumn<Alumnos, String> tc_nombreAlumno;

    @FXML
    private TableColumn<Alumnos, String> tc_nombreGrupo;

    @FXML
    private TableColumn<Alumnos, String> tc_numExpediente;

    @FXML
    private TableColumn<Alumnos, String> tc_puntosAcumulados;

    @FXML
    private TableView<Alumnos> tv_alumnos;

    @FXML
    private TextField txt_numExpediente;

    private AlumnosCRUD alumnosCRUD = new AlumnosCRUD();

    private ObservableList<Alumnos> alumnosObservableList;

    private FilteredList<Alumnos> filteredList;

    private Session session;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        session = HibernateUtil.getSessionFactory().openSession();

        tc_nombreAlumno.setCellValueFactory(new PropertyValueFactory<>("nombre_alum"));
        tc_numExpediente.setCellValueFactory(new PropertyValueFactory<>("numero_expediente"));
        tc_puntosAcumulados.setCellValueFactory(new PropertyValueFactory<>("puntos_acumulados"));

        tc_nombreGrupo.setCellValueFactory(cellData -> {
            Grupos grupo = cellData.getValue().getGrupo(); // Obtener el grupo del alumno
            return new SimpleStringProperty(grupo.getNombreGrupo());
        });

        tv_alumnos.setRowFactory(tv -> new TableRow<>() {
            protected void updateItem(Alumnos alumnos, boolean empty) {
                super.updateItem(alumnos, empty);
                if (alumnos == null || empty) {
                    setStyle(""); // Restablecer estilo si la fila está vacía
                } else {
                    int puntos = alumnos.getPuntos_acumulados();
                    if (puntos > 25) {
                        setStyle("-fx-background-color: #ff9a9a;");
                    } else if (puntos < 25 && puntos >= 12 ) {
                        setStyle("-fx-background-color: #fff6a0;");
                    } else if (puntos < 12) {
                        setStyle("-fx-background-color: #a0ffa0;");
                    } else {
                        setStyle(""); // Sin estilo si no hay sanción
                    }
                }
            }
        });
        ArrayList<Alumnos> listaAlumnos = alumnosCRUD.obtenerAlumnos();
        alumnosObservableList = FXCollections.observableArrayList(listaAlumnos);

        // Crear el FilteredList basado en la lista original
        filteredList = new FilteredList<>(alumnosObservableList, alumno -> true);
        tv_alumnos.setItems(filteredList);
        configurarPaginacion(filteredList);
    }

    @FXML
    void onBorrarClick(ActionEvent event) {
        // Restablecer el filtro para mostrar todos los alumnos
        filteredList.setPredicate(alumno -> true);

        // Borrar el texto del campo de búsqueda
        txt_numExpediente.clear();

        // Recalcular la paginación para reflejar todos los datos
        configurarPaginacion(filteredList);

        // Volver a la primera página
        pagination.setCurrentPageIndex(0);
    }


    @FXML
    void onBuscarClick(ActionEvent event) {
        String numeroExpediente = txt_numExpediente.getText(); // Recojo el número de expediente

        if (numeroExpediente == null || numeroExpediente.isEmpty()) {
            Alerta.mensajeError("Campo vacío", "Por favor, introduce un número válido.");
            return;
        }

        try {
            // Actualizar el filtro para mostrar solo el alumno con el número buscado
            filteredList.setPredicate(alumno -> alumno.getNumero_expediente().equals(numeroExpediente));

            // Comprobar si hay algún resultado
            if (filteredList.isEmpty()) {
                Alerta.mensajeError("Alumno no encontrado", "No se encontró ningún alumno con el expediente: " + numeroExpediente);
                txt_numExpediente.clear();

                // Restablecer el filtro si no hay resultados
                filteredList.setPredicate(alumno -> true);
            }

            // Recalcular la paginación para reflejar el nuevo contenido
            configurarPaginacion(filteredList);

            // Ir a la primera página después de aplicar el filtro
            pagination.setCurrentPageIndex(0);

        } catch (NumberFormatException e) {
            Alerta.mensajeError("Formato no válido", "Por favor, introduce un número de expediente válido.");
        }
    }


    private void configurarPaginacion(ObservableList<Alumnos> listaCompleta) {
        int filasPorPagina = 6; // Número de filas por página

        // Configurar el Pagination
        pagination.setPageCount((int) Math.ceil((double) listaCompleta.size() / filasPorPagina));

        // Listener para cambiar de página
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            cambiarPagina(listaCompleta, filasPorPagina, newValue.intValue());
        });

        // Mostrar la primera página
        cambiarPagina(listaCompleta, filasPorPagina, pagination.getCurrentPageIndex());
    }


    private void cambiarPagina(ObservableList<Alumnos> listaCompleta, int filasPorPagina, int paginaActual) {
        int desdeIndice = paginaActual * filasPorPagina;
        int hastaIndice = Math.min(desdeIndice + filasPorPagina, listaCompleta.size());

        ObservableList<Alumnos> paginaActualLista = FXCollections.observableArrayList(listaCompleta.subList(desdeIndice, hastaIndice));

        tv_alumnos.setItems(paginaActualLista);
    }
}