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

    // Inicializamos la tabla con los datos de los Alumnos
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
            @Override
            protected void updateItem(Alumnos alumno, boolean empty) {
                super.updateItem(alumno, empty);

                if (alumno == null || empty) {
                    setStyle(""); // Restablecer estilo si la fila está vacía o el alumno es null
                    return; // Salir para evitar el procesamiento adicional
                }

                List<PartesIncidencia> partes = partesCRUD.obtenerPartesAlumno(alumno.getId_alum());
                String estilo = "-fx-background-color: ";
                ColorParte color = colorMasGrave(partes);
                if (partes.isEmpty()) {
                    estilo = "";
                    setStyle(""); // Restablecer estilo si la fila está vacía
                } else {
                    // Aplicar colores según el color del parte
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

        // Crear el FilteredList basado en la lista original
        filteredList = new FilteredList<>(alumnosObservableList, alumno -> true);
        tv_alumnos.setItems(filteredList);
        configurarPaginacion(filteredList);
    }


    // Método borrar/establecer si hemos filtrado por un número en la tabla
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


    // Método que busca por el número de expediente de un alúmno
    @FXML
    void onBuscarClick(ActionEvent event) {
        String numeroExpediente = txt_numExpediente.getText(); // Recojo el número de expediente

        if (numeroExpediente == null || numeroExpediente.isEmpty()) {
            Alerta.mensajeError("Campo vacío", "Por favor, introduce un número válido.");
            return;
        }

        try {
            // Actualizamos el filtro para mostrar solo el alumno con el número buscado
            filteredList.setPredicate(alumno -> alumno.getNumero_expediente().equals(numeroExpediente));

            // Comprobamos si hay algún resultado
            if (filteredList.isEmpty()) {
                Alerta.mensajeError("Alumno no encontrado", "No se encontró ningún alumno con el expediente: " + numeroExpediente);
                txt_numExpediente.clear();

                // Restablecemos el filtro si no hay resultados
                filteredList.setPredicate(alumno -> true);
            }

            // Recalculamos la paginación para reflejar el nuevo contenido
            configurarPaginacion(filteredList);

            // Ir a la primera página después de aplicar el filtro
            pagination.setCurrentPageIndex(0);

        } catch (NumberFormatException e) {
            Alerta.mensajeError("Formato no válido", "Por favor, introduce un número de expediente válido.");
        }
    }


    // Método de paginación
    private void configurarPaginacion(ObservableList<Alumnos> listaCompleta) {
        int filasPorPagina = 7; // Número de filas por página

        // Configurar el Pagination
        pagination.setPageCount((int) Math.ceil((double) listaCompleta.size() / filasPorPagina));

        // Listener para cambiar de página
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            cambiarPagina(listaCompleta, filasPorPagina, newValue.intValue());
        });

        // Mostrar la primera página
        cambiarPagina(listaCompleta, filasPorPagina, pagination.getCurrentPageIndex());
    }


    // Método para poder cambiar de página en el pagination
    private void cambiarPagina(ObservableList<Alumnos> listaCompleta, int filasPorPagina, int paginaActual) {
        int desdeIndice = paginaActual * filasPorPagina;
        int hastaIndice = Math.min(desdeIndice + filasPorPagina, listaCompleta.size());

        ObservableList<Alumnos> paginaActualLista = FXCollections.observableArrayList(listaCompleta.subList(desdeIndice, hastaIndice));

        tv_alumnos.setItems(paginaActualLista);
    }


    // Método que colorea al alumno depediendo del parte que ha recibido
    private ColorParte colorMasGrave(List<PartesIncidencia> partes) {
        ColorParte color = ColorParte.VERDE;

        for (PartesIncidencia parte : partes) {
            if (parte.getColor() != null) {
                if (parte.getColor() == ColorParte.ROJO) {
                    return ColorParte.ROJO; // Si hay algún ROJO, retornamos ROJO
                }
                if (parte.getColor() == ColorParte.NARANJA && color != ColorParte.ROJO) {
                    color = ColorParte.NARANJA; // Si hay NARANJA y no hay ROJO, ponemos NARANJA
                }
                if (parte.getColor() == ColorParte.VERDE && color == ColorParte.VERDE) {
                    color = ColorParte.VERDE; // Si hay VERDE y es el color más leve, mantenemos VERDE
                }
            }
        }
        return color;
    }
}
