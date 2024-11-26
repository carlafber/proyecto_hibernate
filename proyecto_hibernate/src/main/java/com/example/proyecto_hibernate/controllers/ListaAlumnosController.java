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
            if (grupo != null) {
                return new SimpleStringProperty(grupo.getNombreGrupo());
            } else {
                return new SimpleStringProperty("Sin Grupo"); // Valor por defecto si no tiene grupo
            }
        });
        ArrayList<Alumnos> listaAlumnos = alumnosCRUD.getAlumnos();
        alumnosObservableList = FXCollections.observableArrayList(listaAlumnos);

        // Crear el FilteredList basado en la lista original
        filteredList = new FilteredList<>(alumnosObservableList, alumno -> true);
        tv_alumnos.setItems(filteredList);
    }

    @FXML
    void onBorrarClick(ActionEvent event) {
        filteredList.setPredicate(alumno -> true);
        txt_numExpediente.clear();
    }


    @FXML
    void onBuscarClick(ActionEvent event) {
        String numeroExpediente = txt_numExpediente.getText(); // Recojo el número de expediente

        if(numeroExpediente == null || numeroExpediente.isEmpty()){
            Alerta.mensajeError("Campo vacío", "Por favor, introduce un numero valido");
            return;
        }

        try {
            // Actualizar el filtro para mostrar solo el alumno con el ID buscado
            filteredList.setPredicate(alumno -> alumno.getNumero_expediente().equals(numeroExpediente));

            // Comprobar si hay algún resultado
            if (filteredList.isEmpty()) {
                Alerta.mensajeError("Alumno no encontrado", "No se encontró ningún alumno con el expediente: " + numeroExpediente);
                txt_numExpediente.clear();

                // Restablecer el filtro si no hay resultados
                filteredList.setPredicate(alumno -> true);
            }
        } catch (NumberFormatException e) {
            Alerta.mensajeError("Formato no válido", "Por favor, introduce un número de expediente válido.");
        }
    }
}
