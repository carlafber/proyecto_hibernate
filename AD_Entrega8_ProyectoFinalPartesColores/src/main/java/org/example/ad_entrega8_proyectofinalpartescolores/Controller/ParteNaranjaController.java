package org.example.ad_entrega8_proyectofinalpartescolores.Controller;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.ad_entrega8_proyectofinalpartescolores.DAO.ParteIncidenciasDAO;
import org.example.ad_entrega8_proyectofinalpartescolores.Model.ParteIncidencia;
import org.example.ad_entrega8_proyectofinalpartescolores.util.ComprobacionesYcambioEscena;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ParteNaranjaController implements Initializable {

    @FXML
    private Button bt_crear;

    @FXML
    private Button bt_parteNaranja;

    @FXML
    private Button bt_parteRojo;

    @FXML
    private Button bt_parteVerde;

    @FXML
    private DatePicker dp_fechaParte;

    @FXML
    private Label nombre_profesor;

    @FXML
    private ComboBox<String> cb_horaParte;

    @FXML
    private TextArea txt_descripcion;

    @FXML
    private TextField txt_expedienteAlumno;

    @FXML
    private Label grupoAlumno;

    ParteIncidenciasDAO parteDAO = new ParteIncidenciasDAO();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //iniciar el comboBox con las horas lectivas del centro
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
    }//initialize

    @FXML
    void onParteRojoClick(ActionEvent event) {
        ComprobacionesYcambioEscena.cambiarEscena(bt_parteRojo, "parteRojo.fxml");
    }//onParteRojoClick

    @FXML
    void onParteVerdeClick(ActionEvent event) {
        ComprobacionesYcambioEscena.cambiarEscena(bt_parteRojo, "parteVerde.fxml");
    }//onParteVerdeClick


    @FXML
    void onCrearParteNaranjaClick(ActionEvent event) {
        String numExpedienteAlumno = txt_expedienteAlumno.getText();
        //grupo
        String nombreProfesor = nombre_profesor.getText();
        LocalDate diaParte = dp_fechaParte.getValue();
        //hora
        String descripcion = txt_descripcion.getText();

        //comprobar que todos los campos estan rellenos -> solo en los que se pueden introducir datos
        if (numExpedienteAlumno.isEmpty() || diaParte == null || descripcion.isEmpty()){
            ComprobacionesYcambioEscena.mostrarAlerta(Alert.AlertType.ERROR, "Todos los campos deben estar rellenos.");
        } else { //si todos los campos están correctos -> creo el parte y lo introduzco en la BD
            ParteIncidencia parte = new ParteIncidencia();
            parteDAO.insertarParte(parte);
        }
        //poner el profesor en los label
        //comprobacion en tiempo real del expediente
    }//onCrearParteNaranjaClick
}//class
