package com.example.proyectopartepablo.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerta {
    public static void alertaError(String mensaje){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensaje );
        alert.showAndWait();
    }
    public static void alertaInfo(String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje );
        alert.showAndWait();
    }
    public static Optional<ButtonType> alertaWarning(String mensaje){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(mensaje );
        return alert.showAndWait();
    }
}
