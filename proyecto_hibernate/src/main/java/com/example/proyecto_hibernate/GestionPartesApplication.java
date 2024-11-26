package com.example.proyecto_hibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GestionPartesApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GestionPartesApplication.class.getResource("inicio-sesion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Gestión de partes");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}