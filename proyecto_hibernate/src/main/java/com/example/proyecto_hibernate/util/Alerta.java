package com.example.proyecto_hibernate.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

//Clase que tiene los métodos necesarios para mostrar alertas

public class Alerta {
    //método que muestra un alerta, pasándole el encabezado y un mensaje
    public static void mensajeError(String encabezado, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }//mensajeError


    //método que muestra un alerta, pasándole el título, encabezado y un mensaje
    public static void mensajeInfo(String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }//mensajeInfo
}
