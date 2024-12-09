package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.CRUD.AlumnosCRUD;
import com.example.proyecto_hibernate.CRUD.PartesCRUD;
import com.example.proyecto_hibernate.classes.Alumnos;
import com.example.proyecto_hibernate.classes.ColorParte;
import com.example.proyecto_hibernate.classes.ParteIncidencia;
import com.example.proyecto_hibernate.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import javafx.util.Callback;

public class ParteRojoController implements Initializable, Configurable {

    @FXML
    private Button bt_actualizar;

    @FXML
    private Button bt_crear;

    @FXML
    private Button bt_parteNaranja;

    @FXML
    private Button bt_parteRojo;

    @FXML
    private Button bt_parteVerde;

    @FXML
    private ComboBox<String> cb_horaParte;

    @FXML
    private ComboBox<String> cb_sancion;

    @FXML
    private DatePicker dp_fechaParte;

    @FXML
    private TextArea txt_sancion;

    @FXML
    private Label grupo_alumno;

    @FXML
    private Label nombre_profesor;

    @FXML
    private TextArea txt_descripcion;

    @FXML
    private TextField txt_expedienteAlumno;

    private PartesCRUD parteCRUD = new PartesCRUD();

    private AlumnosCRUD alumnoCRUD = new AlumnosCRUD();

    private Alumnos alumno;

    private Boolean reset = false;

    private List<String> sanciones = new ArrayList<>();

    private ListaPartesController listaPartesController;

    @FXML
    void onActualizarClick(ActionEvent event) {
        ParteIncidencia parte = GuardarParte.getParte();
        parte.setFecha(dp_fechaParte.getValue());
        parte.setHora(cb_horaParte.getValue());
        parte.setDescripcion(txt_descripcion.getText());
        if (cb_sancion.getValue().equals("Otra:")) {
            parte.setSancion(txt_sancion.getText());
        } else {
            parte.setSancion(cb_sancion.getValue());
        }
        parte.setColor(ColorParte.ROJO);
        parte.setPuntos_parte(parte.getColor().getPuntos());

        if(parteCRUD.actualizarParte(parte)){
            Alerta.mensajeInfo("ÉXITO", null,"Parte actualizado correctamente.");
            // Cerrar la ventana actual
            Stage stage = (Stage) bt_actualizar.getScene().getWindow();
            stage.close();

            setListaPartesController(listaPartesController);
            // Notificar a la lista de partes para que se recargue
            listaPartesController.recargarListaPartes();
        } else {
            Alerta.mensajeError(null, "No se pudo actualizar el parte.");
        }
    }

    @FXML
    void onCrearClick(ActionEvent event) {
        if (txt_expedienteAlumno.getText().isEmpty() || dp_fechaParte.getValue() == null || txt_descripcion.getText().isEmpty() || cb_horaParte.getValue() == null || cb_sancion.getValue().isEmpty()){
            Alerta.mensajeError("Campos vacíos", "Por favor, completa todos los campos.");
            if(cb_sancion.getValue().equals("Otra:")){
                if(txt_sancion.getText().isEmpty()){
                    Alerta.mensajeError("Campos vacíos", "Por favor, completa todos los campos.");
                }
            }
        } else { //si todos los campos están correctos -> creo el parte y lo introduzco en la BD
            String sancion = "";
            if (cb_sancion.getValue().equals("Otra:")) {
                sancion = txt_sancion.getText();
            } else {
                sancion = cb_sancion.getValue();
            }

            ParteIncidencia parte = new ParteIncidencia(alumno, GuardarProfesor.getProfesor(), alumno.getGrupo(), dp_fechaParte.getValue(), cb_horaParte.getValue(), txt_descripcion.getText(), sancion, ColorParte.ROJO);
            alumnoCRUD.actualizarPuntosAlumno(alumno, parte);
            parteCRUD.crearParte(parte);
            Alerta.mensajeInfo("ÉXITO", "Parte creado", "El parte ha sido creado correctamente.");
            limpiarCampos();
        }
    }

    @FXML
    void onExpedienteAlumnoChange(KeyEvent event) {
        String numExpediente = txt_expedienteAlumno.getText();

        if (!numExpediente.isEmpty()) {
            // Buscar el alumno con el número de expediente
            alumno = alumnoCRUD.buscarAlumnoPorExpediente(numExpediente);

            if (alumno != null) {
                // Si el alumno existe, mostrar el grupo en el Label
                grupo_alumno.setText(alumno.getGrupo().getNombreGrupo());
            } else {
                // Si no se encuentra el alumno, mostrar un mensaje de error
                grupo_alumno.setText("Alumno no encontrado.");
            }
        } else {
            // Si el campo está vacío, limpiar el Label
            grupo_alumno.setText("");
        }
    }

    @FXML
    void onParteNaranjaClick(ActionEvent event) {
        resetParte(reset);
        CambioEscena.cambiarEscena(bt_parteNaranja, "parte-naranja.fxml");
    }//onParteNaranjaClick

    @FXML
    void onParteVerdeClick(ActionEvent event) {
        resetParte(reset);
        CambioEscena.cambiarEscena(bt_parteRojo, "parte-verde.fxml");
    }//onParteVerdeClick


    //validación de lo que se selecciona en el comboBox del tipo de sanción recibida
    @FXML
    void onSancionChangeOtro(ActionEvent event) {
        String tipoSancion = cb_sancion.getValue();

        if (tipoSancion != null && tipoSancion.equals("Otra:")) {
            //si el valor es "Otro", mostramos el TextField
            txt_sancion.setVisible(true);
        } else {
            //si no es "otro" la opción seleccioanda, el TextField se mantiene oculto
            txt_sancion.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cb_horaParte.getItems().addAll(
                "8:30-9:20",
                "9:25-10:15",
                "10:20-11:10",
                "11:40-12:30",
                "12:35-13:25",
                "13:30-14:20",
                "16:00-16:50",
                "16:55-17:45",
                "17:50-18:40",
                "18:55-19:45",
                "19:50-20:40",
                "20:45-21:35"
        );

        sanciones = List.of(
                "Incoación de expediente o en su caso expediente abreviado",
                "Reunión con la Comisión de Convivencia",
                "Es obligatorio pedir disculpas a la persona/as contra las que ejerció daño físico o moral, y/o reparar los daños materiales causados",
                "Otra:"
        );

        cb_sancion.getItems().addAll(sanciones);

        // Establecer la fábrica de celdas para el ComboBox para que los elementos largos se desplieguen en varias líneas
        cb_sancion.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            // Crear un Label para cada celda
                            Label label = new Label(item);
                            label.setWrapText(true);  // Habilitar el salto de línea
                            label.setMaxWidth(400);   // Ajustar el ancho máximo
                            setGraphic(label);        // Establecer el Label como gráfico de la celda
                        } else {
                            setGraphic(null);  // Si la celda está vacía, no mostrar nada
                        }
                    }
                };
            }
        });

        nombre_profesor.setText(GuardarProfesor.getProfesor().getNombre());

        txt_sancion.setVisible(false);

        if(GuardarParte.getParte() != null){
            txt_expedienteAlumno.setText(GuardarParte.getParte().getAlumno().getNumero_expediente());
            grupo_alumno.setText(GuardarParte.getParte().getGrupo().getNombreGrupo());
            dp_fechaParte.setValue(GuardarParte.getParte().getFecha());
            cb_horaParte.setValue(GuardarParte.getParte().getHora());
            txt_descripcion.setText(GuardarParte.getParte().getDescripcion());
            if (comprobarSancion(GuardarParte.getParte().getSancion())) {
                txt_sancion.setVisible(true);
                cb_sancion.setValue("Otra:");
                txt_sancion.setText(GuardarParte.getParte().getSancion());
            } else {
                cb_sancion.setValue(GuardarParte.getParte().getSancion());
            }
        }

        bt_actualizar.setDisable(reset);
        bt_crear.setDisable(!reset);
    }

    private void limpiarCampos() {
        txt_expedienteAlumno.clear();
        grupo_alumno.setText("");
        dp_fechaParte.setValue(null);
        cb_horaParte.setValue(null);
        txt_descripcion.clear();
        cb_sancion.setValue(null);
        txt_sancion.setText("");
    }

    public boolean comprobarSancion(String sancion){
        for (String s : sanciones) {
            if (sancion.contains(s)) {
                return false; // Contiene al menos una de las cadenas
            }
        }
        return true; // No contiene ninguna de las cadenas
    }

    @Override
    public void configurarBotones(Boolean estado) {// Deshabilita o habilita el botón según el estado.
        bt_parteVerde.setDisable(estado);
        bt_parteNaranja.setDisable(estado);
        bt_crear.setDisable(!estado);
        bt_actualizar.setDisable(estado);
        reset = estado;
    }

    @Override
    public void setListaPartesController(ListaPartesController listaPartesController) {
        this.listaPartesController = GuardarController.getController();
    }

    private void resetParte(Boolean reset) {
        if(reset){
            GuardarParte.resetParte();
        }
    }
}//class
