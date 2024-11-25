package com.example.proyectopartepablo.Controller;

import com.example.proyectopartepablo.Dao.AlumnosDAO;
import com.example.proyectopartepablo.Dao.GruposDAO;
import com.example.proyectopartepablo.clases.Alumnos;
import com.example.proyectopartepablo.clases.Grupos;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    AlumnosDAO alumnosDAO = new AlumnosDAO();
    GruposDAO gruposDAO = new GruposDAO();

    ObservableList <Alumnos> alumnosObservableList;
    ObservableList <Grupos> alumnoObservableList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tv_nombreAlumno.setCellValueFactory(new PropertyValueFactory<>("nombre_alumno"));
        tv_nombreGrupo.setCellValueFactory(new PropertyValueFactory<>("nombre_grupo"));
        tv_numeroExpediente.setCellValueFactory(new PropertyValueFactory<>("numero_expediente"));
        tv_puntosAcumulados.setCellValueFactory(new PropertyValueFactory<>("puntos_acumulados"));

        ArrayList<Alumnos> listaAlumnos = alumnosDAO.getAlumnosHibernate();
        alumnosObservableList = FXCollections.observableArrayList(listaAlumnos);
        tableView.setItems(alumnosObservableList);

        /*
        ArrayList<Grupos> listaGrupos = gruposDAO.getGruposHibernate();
        alumnoObservableList = FXCollections.observableArrayList(listaGrupos);
        tableView.setItems(alumnosObservableList);
         */

    }
}
