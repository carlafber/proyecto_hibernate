package org.example.ad_entrega8_proyectofinalpartescolores.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.ad_entrega8_proyectofinalpartescolores.App;

public class ComprobacionesYcambioEscena {

    //método que cambia de escena en el FXML
    public static void cambiarEscena(Button boton, String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFile)); //carga el archivo FXML
            Parent root = fxmlLoader.load(); //carga el archivo FXML

            Scene scene = new Scene(root); //crea la nueva escena
            Stage stage = (Stage) boton.getScene().getWindow(); //obtiene la ventana (Stage) desde el botón
            stage.setScene(scene); //establece la nueva escena en la ventana actual
        } catch (Exception e) {
            System.out.println("Error al cambiar la escena.");
            e.printStackTrace(); //muestra la traza completa de la excepción
        } //try-catch
    } //cambiarEscena


    //método para alertas
    public static void mostrarAlerta(Alert.AlertType tipoAlerta, String mensaje) {
        Alert alert = new Alert(tipoAlerta);
        alert.setContentText(mensaje);

        //configuro el título según el tipo de alerta
        switch (tipoAlerta) {
            case INFORMATION:
                alert.setTitle("Información");
                break;
            case ERROR:
                alert.setTitle("Error");
                break;
            case CONFIRMATION:
                alert.setTitle("Confirmación");
                break;
            default:
                alert.setTitle("Notificación");
                break;
        }//switch
        alert.showAndWait(); // muestra la alerta y espera que el usuario la cierre
    }//mostrarAlerta
}
