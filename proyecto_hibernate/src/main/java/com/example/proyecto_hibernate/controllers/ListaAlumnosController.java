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

//Clase que controla el listado de alumnos
public class ListaAlumnosController implements Initializable {

    //ATRIBUTOS
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

    private AlumnosCRUD alumnosCRUD = new AlumnosCRUD(); //instancia de la clase CRUD para realizar operaciones

    private PartesCRUD partesCRUD = new PartesCRUD(); //instancia de la clase CRUD para realizar operaciones

    private ObservableList<Alumnos> alumnosObservableList; //lista para mostrar los alumnos en la tabla

    private FilteredList<Alumnos> filteredList; //lista para filtrar por el criterio de búsqueda


    //MÉTODOS
    //método que se ejecuta cuando se presiona sobre el botón 'Borrar' de los filtros
    @FXML
    void onBorrarClick(ActionEvent event) {
        restablecerFiltro(); //se restablece el filtro de búsqueda

        restablecerPaginacion(); //se restablece la paginación para reflejar los cambios
    }//onBorrarClick


    //método que se ejecuta cuando se presiona sobre el botón 'Buscar' de los filtros
    @FXML
    void onBuscarClick(ActionEvent event) {
        String numeroExpediente = txt_numExpediente.getText(); //obtener el número de expediente introducido en el filtro

        //comprobar que no está vacio el campo
        if (numeroExpediente == null || numeroExpediente.isEmpty()) {
            Alerta.mensajeError("Campo vacío", "Por favor, introduce un número válido.");
            return;
        }//if

        try {
            //se actualiza el filtro para mostrar solo el alumno con el número expediente buscado
            filteredList.setPredicate(alumno -> alumno.getNumero_expediente().equals(numeroExpediente));

            //comprobar si hay algún resultado
            if (filteredList.isEmpty()) {
                Alerta.mensajeError("Alumno no encontrado", "No se encontró ningún alumno con el expediente: " + numeroExpediente);

                //se restablece el filtro de búsqueda
                restablecerFiltro();
            }//if

            //se restablece la paginación para reflejar los cambios
            restablecerPaginacion();

        } catch (NumberFormatException e) {
            Alerta.mensajeError("Formato no válido", "Por favor, introduce un número de expediente válido.");
        }//try-catch
    }//onBuscarClick


    //método que se ejecuta al abrirse la pantalla
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //inicializar las columnas de la tabla
        tc_nombreAlumno.setCellValueFactory(new PropertyValueFactory<>("nombre_alum"));
        tc_numExpediente.setCellValueFactory(new PropertyValueFactory<>("numero_expediente"));
        tc_puntosAcumulados.setCellValueFactory(new PropertyValueFactory<>("puntos_acumulados"));
        //obtener solo el nombre de grupo porque el alumno almacena el grupo como objeto
        tc_nombreGrupo.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getGrupo().getNombreGrupo());
        });

        //dar color a las columnas de la tabla, dependiendo del color del parte de más gravedad
        tv_alumnos.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Alumnos alumno, boolean empty) {
                super.updateItem(alumno, empty);

                if (alumno == null || empty) {
                    setStyle(""); //restablecer estilo si la fila está vacía o el alumno es null
                    return; //salir para evitar el procesamiento adicional
                }

                //obtener todos los pates del alumno por el id
                List<PartesIncidencia> partes = partesCRUD.obtenerPartesAlumno(alumno.getId_alum());

                String estilo = "-fx-background-color: "; //variable para ir concatenando el estilo
                ColorParte color = colorMasGrave(partes); //obtener el color de l parte de mayor gravedad
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
        });//tv_alumnos.setRowFactory


        ArrayList<Alumnos> listaAlumnos = alumnosCRUD.obtenerAlumnos(); //obtener la lista de alumnos
        alumnosObservableList = FXCollections.observableArrayList(listaAlumnos); //mostrar la lista

        //crear el FilteredList basado en la lista original
        filteredList = new FilteredList<>(alumnosObservableList, alumno -> true);
        tv_alumnos.setItems(filteredList);
        configurarPaginacion(filteredList);
    }//initialize


    //método para configurar la paginación de la tabla
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
    }//configurarPaginacion


    //método para cambiar de página (de la paginación) y actualizar los datos visibles en la tabla
    private void cambiarPagina(ObservableList<Alumnos> listaCompleta, int filasPorPagina, int paginaActual) {
        //variable para guardar el inicio del índice
        int desdeIndice = paginaActual * filasPorPagina;
        //variable para guardar el fin del índice
        int hastaIndice = Math.min(desdeIndice + filasPorPagina, listaCompleta.size());

        //lista que contiene solo un número de datos
        ObservableList<Alumnos> paginaActualLista = FXCollections.observableArrayList(listaCompleta.subList(desdeIndice, hastaIndice));

        tv_alumnos.setItems(paginaActualLista); //mostrar la lista
    }//cambiarPagina


    //método que selecciona cuál es el color del parte más grave
    private ColorParte colorMasGrave(List<PartesIncidencia> partes) {
        if (partes == null || partes.isEmpty()) {
            return ColorParte.VERDE; //valor por defecto si no hay partes
        }

        ColorParte color = ColorParte.VERDE; //color menos grave por defecto

        for (PartesIncidencia parte : partes) {
            if (parte.getColor() != null) {
                switch (parte.getColor()) {
                    case ROJO:
                        return ColorParte.ROJO; //gravedad máxima encontrada
                    case NARANJA:
                        color = ColorParte.NARANJA; //actualizar si no hay ROJO
                        break;
                }
            }
        }
        return color;
    }//colorMasGrave


    //método para restablecer el filtro de búsqueda
    private void restablecerFiltro(){
        //se restablece el filtro para mostrar todos los alumnos
        filteredList.setPredicate(alumno -> true);

        //se borra el texto del campo de búsqueda
        txt_numExpediente.clear();
    }//restablecerFiltro


    //método para restablecer la paginación para reflejar los cambios
    private void restablecerPaginacion() {
        //se recalcula la paginación para reflejar todos los datos
        configurarPaginacion(filteredList);

        //volver a la primera página
        pagination.setCurrentPageIndex(0);
    }//restablecerPaginacion
}//class