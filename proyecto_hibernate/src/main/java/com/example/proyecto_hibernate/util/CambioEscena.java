package com.example.proyecto_hibernate.util;

import com.example.proyecto_hibernate.GestionPartesApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CambioEscena {

    //metodo que cambia de escena en el FXML
    public static void cambiarEscena(Button boton, String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GestionPartesApplication.class.getResource(fxmlFile)); //carga el archivo FXML
            Parent root = fxmlLoader.load(); //carga el archivo FXML

            Scene scene = new Scene(root); //crea la nueva escena
            Stage stage = (Stage) boton.getScene().getWindow(); //obtiene la ventana (Stage) desde el botón
            stage.setScene(scene); //establece la nueva escena en la ventana actual
        } catch (Exception e) {
            System.out.println("Error al cambiar la escena.");
            e.printStackTrace(); //muestra la traza completa de la excepción
        } //try-catch
    } //cambiarEscena


    public static void abrirEscena(String fxmlFile, String titulo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GestionPartesApplication.class.getResource(fxmlFile)); //carga el archivo FXML
            Parent root = fxmlLoader.load(); //carga el archivo FXML
            Object controller = fxmlLoader.getController(); //obtener el controller

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error al cambiar la escena.");
            e.printStackTrace(); //muestra la traza completa de la excepción
        } //try-catch
    } //cambiarEscena
}
