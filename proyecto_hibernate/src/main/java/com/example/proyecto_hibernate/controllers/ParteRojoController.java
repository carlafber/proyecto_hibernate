package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.CRUD.AlumnosCRUD;
import com.example.proyecto_hibernate.CRUD.PartesCRUD;
import com.example.proyecto_hibernate.classes.Alumnos;
import com.example.proyecto_hibernate.classes.ColorParte;
import com.example.proyecto_hibernate.classes.ParteIncidencia;
import com.example.proyecto_hibernate.util.Alerta;
import com.example.proyecto_hibernate.util.CambioEscena;
import com.example.proyecto_hibernate.util.GuardarParte;
import com.example.proyecto_hibernate.util.GuardarProfesor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ParteRojoController implements Initializable {

    @FXML
    private Button bt_crear;

    @FXML
    private Button bt_actualizar;

    @FXML
    private Button bt_parteNaranja;

    @FXML
    private Button bt_parteRojo;

    @FXML
    private Button bt_parteVerde;

    @FXML
    private TextArea txt_sancionRecibida;

    @FXML
    private ComboBox<String> cb_horaParte;

    @FXML
    private ComboBox<String> cb_tipoSancion;

    @FXML
    private DatePicker dp_fechaParte;

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

    @FXML
    void onCrearClick(ActionEvent event) {
        if (txt_expedienteAlumno.getText().isEmpty() || dp_fechaParte.getValue() == null || txt_descripcion.getText().isEmpty() || cb_horaParte.getValue().isEmpty()){
            Alerta.mensajeError("Campos vacíos", "Por favor, completa todos los campos.");
        } else { //si todos los campos están correctos -> creo el parte y lo introduzco en la BD
            ParteIncidencia parte = new ParteIncidencia(alumno, GuardarProfesor.getProfesor(), alumno.getGrupo(), dp_fechaParte.getValue(), cb_horaParte.getValue(), txt_descripcion.getText(), "sancion", ColorParte.ROJO);
            //puntuacion falta
            //sancion hacer
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
        GuardarParte.resetParte();
        CambioEscena.cambiarEscena(bt_parteNaranja, "parte-naranja.fxml");
    }//onParteNaranjaClick

    @FXML
    void onParteVerdeClick(ActionEvent event) {
        GuardarParte.resetParte();
        CambioEscena.cambiarEscena(bt_parteRojo, "parte-verde.fxml");
    }//onParteVerdeClick

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cb_horaParte.getItems().addAll(
                "8:30-9:20",
                "9:25-10:15",
                "10:15-11:10",
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

        cb_tipoSancion.getItems().addAll(
                "Incoación de expediente o en su caso expediente abreviado",
                "Reunión con la Comisión de Convivencia",
                "Es obligatorio pedir disculpas a la persona/as contra las que\nejerció daño físico o moral, y/o reparar los daños materiales causados",
                "Otro"
        );

        nombre_profesor.setText(" " + GuardarProfesor.getProfesor().getNombre());

        txt_sancionRecibida.setVisible(false);

        if(GuardarParte.getParte() != null){
            txt_expedienteAlumno.setText(GuardarParte.getParte().getAlumno().getNumero_expediente());
            grupo_alumno.setText(GuardarParte.getParte().getGrupo().getNombreGrupo());
            dp_fechaParte.setValue(GuardarParte.getParte().getFecha());
            cb_horaParte.setValue(GuardarParte.getParte().getHora());
            txt_descripcion.setText(GuardarParte.getParte().getDescripcion());
        }
    }

    @FXML
    void onActualizarClick(ActionEvent event) {

    }

    //validación de lo que se selecciona en el comboBox del tipo de sanción recibida
    @FXML
    void onSancionChangeOtro(ActionEvent event) {
        String tipoSancion = cb_tipoSancion.getValue();

        if (tipoSancion != null && tipoSancion.equals("Otro")) {
            //si el valor es "Otro", mostramos el TextField
            txt_sancionRecibida.setVisible(true);
        } else {
            //si no es "otro" la opción seleccioanda, el TextField se mantiene oculto
            txt_sancionRecibida.setVisible(false);
        }
    }

    private void limpiarCampos() {
        txt_expedienteAlumno.clear();
        grupo_alumno.setText("");
        dp_fechaParte.setValue(null);
        cb_horaParte.setValue(null);
        txt_descripcion.clear();
    }
}//class
