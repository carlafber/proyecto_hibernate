package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.classes.PartesIncidencia;
import com.example.proyecto_hibernate.classes.Alumnos;
import com.example.proyecto_hibernate.classes.ColorParte;
import com.example.proyecto_hibernate.classes.Grupos;
import com.example.proyecto_hibernate.CRUD.PartesCRUD;
import com.example.proyecto_hibernate.CRUD.AlumnosCRUD;

import com.example.proyecto_hibernate.util.*;
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
import java.util.List;
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

    private PartesCRUD partesCRUD = new PartesCRUD();

    private ObservableList<Alumnos> alumnosObservableList;

    private FilteredList<Alumnos> filteredList;

    private Session session;

    @FXML
    void onBorrarClick(ActionEvent event) {
        //se restablece el filtro de búsqueda
        restablecerFiltro();

        //se restablece la paginación para reflejar los cambios
        restablecerPaginacion();
    }


    @FXML
    void onBuscarClick(ActionEvent event) {
        String numeroExpediente = txt_numExpediente.getText(); //se recoge el número de expediente

        if (numeroExpediente == null || numeroExpediente.isEmpty()) {
            Alerta.mensajeError("Campo vacío", "Por favor, introduce un número válido.");
            return;
        }

        try {
            //se actualiza el filtro para mostrar solo el alumno con el número buscado
            filteredList.setPredicate(alumno -> alumno.getNumero_expediente().equals(numeroExpediente));

            //comprobar si hay algún resultado
            if (filteredList.isEmpty()) {
                Alerta.mensajeError("Alumno no encontrado", "No se encontró ningún alumno con el expediente: " + numeroExpediente);

                //se restablece el filtro de búsqueda
                restablecerFiltro();
            }

            //se restablece la paginación para reflejar los cambios
            restablecerPaginacion();

        } catch (NumberFormatException e) {
            Alerta.mensajeError("Formato no válido", "Por favor, introduce un número de expediente válido.");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        session = HibernateUtil.getSessionFactory().openSession();

        tc_nombreAlumno.setCellValueFactory(new PropertyValueFactory<>("nombre_alum"));
        tc_numExpediente.setCellValueFactory(new PropertyValueFactory<>("numero_expediente"));
        tc_puntosAcumulados.setCellValueFactory(new PropertyValueFactory<>("puntos_acumulados"));

        tc_nombreGrupo.setCellValueFactory(cellData -> {
            Grupos grupo = cellData.getValue().getGrupo(); //se obteniene el grupo del alumno
            return new SimpleStringProperty(grupo.getNombreGrupo());
        });

        tv_alumnos.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Alumnos alumno, boolean empty) {
                super.updateItem(alumno, empty);

                if (alumno == null || empty) {
                    setStyle(""); //restablecer estilo si la fila está vacía o el alumno es null
                    return; //salir para evitar el procesamiento adicional
                }

                List<PartesIncidencia> partes = partesCRUD.obtenerPartesAlumno(alumno.getId_alum());
                String estilo = "-fx-background-color: ";
                ColorParte color = colorMasGrave(partes);
                if (partes.isEmpty()) {
                    estilo = "";
                    setStyle(""); //restablecer estilo si la fila está vacía
                } else {
                    //aplicar colores según el color del parte
                    if(color.equals(ColorParte.VERDE)){
                        estilo += ColorParte.VERDE.getCodigo_color() + ";";
                    } else if(color.equals(ColorParte.NARANJA)){
                        estilo += ColorParte.NARANJA.getCodigo_color() + ";";
                    } else if(color.equals(ColorParte.ROJO)){
                        estilo += ColorParte.ROJO.getCodigo_color() + ";";
                    }
                }
                setStyle(estilo);
            }
        });

        ArrayList<Alumnos> listaAlumnos = alumnosCRUD.obtenerAlumnos();
        alumnosObservableList = FXCollections.observableArrayList(listaAlumnos);

        //crear el FilteredList basado en la lista original
        filteredList = new FilteredList<>(alumnosObservableList, alumno -> true);
        tv_alumnos.setItems(filteredList);
        configurarPaginacion(filteredList);
    }


    private void configurarPaginacion(ObservableList<Alumnos> listaCompleta) {
        int filasPorPagina = 7; //número de filas por página

        //configurar el Pagination
        pagination.setPageCount((int) Math.ceil((double) listaCompleta.size() / filasPorPagina));

        //listener para cambiar de página
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            cambiarPagina(listaCompleta, filasPorPagina, newValue.intValue());
        });

        //mostrar la primera página
        cambiarPagina(listaCompleta, filasPorPagina, pagination.getCurrentPageIndex());
    }


    private void cambiarPagina(ObservableList<Alumnos> listaCompleta, int filasPorPagina, int paginaActual) {
        int desdeIndice = paginaActual * filasPorPagina;
        int hastaIndice = Math.min(desdeIndice + filasPorPagina, listaCompleta.size());

        ObservableList<Alumnos> paginaActualLista = FXCollections.observableArrayList(listaCompleta.subList(desdeIndice, hastaIndice));

        tv_alumnos.setItems(paginaActualLista);
    }


    private ColorParte colorMasGrave(List<PartesIncidencia> partes) {
        ColorParte color = ColorParte.VERDE;

        for (PartesIncidencia parte : partes) {
            if (parte.getColor() != null) {
                if (parte.getColor() == ColorParte.ROJO) {
                    return ColorParte.ROJO; //si hay algún ROJO, retornamos ROJO
                }
                if (parte.getColor() == ColorParte.NARANJA && color != ColorParte.ROJO) {
                    color = ColorParte.NARANJA; //si hay NARANJA y no hay ROJO, ponemos NARANJA
                }
                if (parte.getColor() == ColorParte.VERDE && color == ColorParte.VERDE) {
                    color = ColorParte.VERDE; //si hay VERDE y es el color más leve, mantenemos VERDE
                }
            }
        }
        return color;
    }


    //método para restablecer el filtro de búsqueda
    private void restablecerFiltro(){
        //se restablece el filtro para mostrar todos los alumnos
        filteredList.setPredicate(alumno -> true);

        //se borra el texto del campo de búsqueda
        txt_numExpediente.clear();
    }


    //método para restablecer la paginación para reflejar los cambios
    private void restablecerPaginacion() {
        //se recalcula la paginación para reflejar todos los datos
        configurarPaginacion(filteredList);

        //volver a la primera página
        pagination.setCurrentPageIndex(0);
    }

}