package com.example.proyecto_hibernate.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alerta {
    public static void mensajeError(String encabezado, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void mensajeInfo(String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void mensajeInfoAccion(String titulo, String encabezado, String mensaje, Runnable accion) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();

        // Mostrar la alerta y esperar la acción del usuario
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Ejecutar la acción que se pasa como parámetro, que actualizará la lista
                accion.run();
            }
        });
    }
}
