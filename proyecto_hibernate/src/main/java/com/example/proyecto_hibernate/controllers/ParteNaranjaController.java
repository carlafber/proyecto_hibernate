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
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ParteNaranjaController implements Initializable, Configurable {

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

    private ListaPartesController listaPartesController = GuardarController.getController();

    @FXML
    void onActualizarClick(ActionEvent event) {
        ParteIncidencia parte = GuardarParte.getParte();
        parte.setFecha(dp_fechaParte.getValue());
        parte.setHora(cb_horaParte.getValue());
        parte.setDescripcion(txt_descripcion.getText());
        parte.setSancion(txt_sancion.getText());
        parte.setColor(ColorParte.NARANJA);
        parte.setPuntos_parte(parte.getColor().getPuntos());

        alumno = parte.getAlumno();
        alumnoCRUD.actualizarPuntosAlumno(alumno, parte, false); //actualizar los puntos, al modificar el parte
        if(parteCRUD.actualizarParte(parte)){
            Alerta.mensajeInfo("ÉXITO", null,"Parte actualizado correctamente.");
            // Cerrar la ventana actual
            Stage stage = (Stage) bt_actualizar.getScene().getWindow();
            stage.close();

            // Notificar a la lista de partes para que se recargue
            listaPartesController.recargarListaPartes();
        } else {
            Alerta.mensajeError(null, "No se pudo actualizar el parte.");
        }
    }

    @FXML
    void onCrearClick(ActionEvent event) {
        if (txt_expedienteAlumno.getText().isEmpty() || dp_fechaParte.getValue() == null || txt_descripcion.getText().isEmpty() || cb_horaParte.getValue().isEmpty() || txt_sancion.getText().isEmpty()){
            Alerta.mensajeError("Campos vacíos", "Por favor, completa todos los campos.");
        } else { //si todos los campos están correctos -> creo el parte y lo introduzco en la BD
            ParteIncidencia parte = new ParteIncidencia(alumno, GuardarProfesor.getProfesor(), alumno.getGrupo(), dp_fechaParte.getValue(), cb_horaParte.getValue(), txt_descripcion.getText(), txt_sancion.getText(), ColorParte.NARANJA);
            alumnoCRUD.actualizarPuntosAlumno(alumno, parte, true);
            //puntuacion falta
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
    void onParteRojoClick(ActionEvent event) {
        resetParte(reset);
        CambioEscena.cambiarEscena(bt_parteRojo, "parte-rojo.fxml");
    }//onParteRojoClick

    @FXML
    void onParteVerdeClick(ActionEvent event) {
        resetParte(reset);
        CambioEscena.cambiarEscena(bt_parteRojo, "parte-verde.fxml");
    }//onParteVerdeClick


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

        nombre_profesor.setText(GuardarProfesor.getProfesor().getNombre());

        if(GuardarParte.getParte() != null){
            txt_expedienteAlumno.setText(GuardarParte.getParte().getAlumno().getNumero_expediente());
            grupo_alumno.setText(GuardarParte.getParte().getGrupo().getNombreGrupo());
            dp_fechaParte.setValue(GuardarParte.getParte().getFecha());
            cb_horaParte.setValue(GuardarParte.getParte().getHora());
            txt_descripcion.setText(GuardarParte.getParte().getDescripcion());
            txt_sancion.setText(GuardarParte.getParte().getSancion());
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
        txt_sancion.setText("");
    }

    @Override
    public void configurarBotones(Boolean estado) {// Deshabilita o habilita el botón según el estado.
        bt_parteVerde.setDisable(estado);
        bt_parteRojo.setDisable(estado);
        bt_crear.setDisable(!estado);
        bt_actualizar.setDisable(estado);
        reset = estado;
    }

    private void resetParte(Boolean reset) {
        if(reset){
            GuardarParte.resetParte();
        }
    }
}//class
