package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.CRUD.PartesCRUD;
import com.example.proyecto_hibernate.classes.ColorParte;
import com.example.proyecto_hibernate.util.Alerta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class EstadísticasController {

    @FXML
    private BarChart<String, Number> barChartEstadisticas;

    @FXML
    private Label lblTotalPartes;


    @FXML
    private TextField txtNumeroExpediente;

    @FXML
    private Button bt_buscar;

    @FXML
    private Button bt_limpiar;
    private PartesCRUD partesCRUD;


    @FXML
    public void initialize() {
        partesCRUD = new PartesCRUD();
        mostrarEstadisticasGlobales();
    }
    public void mostrarEstadisticasGlobales() {
        // Limpiar datos existentes
        barChartEstadisticas.getData().clear();

        // Obtener estadísticas globales
        List<Object[]> estadisticasGlobales = partesCRUD.obtenerEstadisticasPartesPorColor();

        // Crear una nueva serie
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Estadísticas Globales");

        // Llenar la gráfica con las estadísticas globales
        for (Object[] resultado : estadisticasGlobales) {
            ColorParte colorParte = (ColorParte) resultado[0];
            String colorString = colorParte.name();
            Long cantidad = (Long) resultado[1];

            XYChart.Data<String, Number> data = new XYChart.Data<>(colorString, cantidad);
            series.getData().add(data);

            // Aplicar estilo de color
            final String colorHex = colorParte.getCodigo_color();
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    newValue.setStyle("-fx-bar-fill: " + colorHex + ";");
                }
            });
        }

        barChartEstadisticas.getData().add(series);

        // Mostrar el total de partes en el Label
        int totalPartes = partesCRUD.obtenerPartes().size();
        lblTotalPartes.setText("Total de partes (global): " + totalPartes);
    }

    public void buscarEstadisticasPorAlumno() {
        // Obtener el número de expediente
        String numeroExpediente = txtNumeroExpediente.getText().trim();

        if (numeroExpediente.isEmpty()) {
            lblTotalPartes.setText("Por favor, introduce un número de expediente.");
            return;
        }

        // Obtener estadísticas filtradas por alumno
        List<Object[]> estadisticasAlumno = partesCRUD.obtenerEstadisticasPartesPorAlumno(numeroExpediente);

        // Limpiar datos existentes
        barChartEstadisticas.getData().clear();

        if (estadisticasAlumno.isEmpty()) {
            lblTotalPartes.setText("No se encontraron partes para el alumno con Nº expediente: " + numeroExpediente);
            return;
        }

        // Crear una nueva serie
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Estadísticas de Alumno: " + numeroExpediente);

        // Llenar la gráfica con las estadísticas del alumno
        for (Object[] resultado : estadisticasAlumno) {
            ColorParte colorParte = (ColorParte) resultado[0];
            String colorString = colorParte.name();
            Long cantidad = (Long) resultado[1];

            XYChart.Data<String, Number> data = new XYChart.Data<>(colorString, cantidad);
            series.getData().add(data);

            // Aplicar estilo de color
            final String colorHex = colorParte.getCodigo_color();
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    newValue.setStyle("-fx-bar-fill: " + colorHex + ";");
                }
            });
        }

        barChartEstadisticas.getData().add(series);

        // Mostrar el total de partes en el Label
        int totalPartesAlumno = estadisticasAlumno.stream()
                .mapToInt(resultado -> ((Long) resultado[1]).intValue())
                .sum();
        lblTotalPartes.setText("Total de partes del alumno: " + totalPartesAlumno);
    }


    @FXML
    public void onClickbuscarEstadisticasPorAlumno(javafx.event.ActionEvent actionEvent) {
        // Obtener el número de expediente ingresado en el TextField
        String numeroExpediente = txtNumeroExpediente.getText().trim();

        if (numeroExpediente.isEmpty()) {
            // Mostrar una alerta si el campo está vacío
            Alerta.mensajeInfo("Advertencia", "Campo vacío", "Por favor, introduce un número de expediente.");
            return;
        }

        // Obtener estadísticas por alumno
        List<Object[]> estadisticasAlumno = partesCRUD.obtenerEstadisticasPartesPorAlumno(numeroExpediente);

        if (estadisticasAlumno.isEmpty()) {
            // Mostrar una alerta si no hay estadísticas para ese alumno
            Alerta.mensajeInfo("Sin resultados", "No se encontraron partes", "No hay registros para el alumno con expediente: " + numeroExpediente);
            return;
        }

        // Actualizar el gráfico de barras con los datos del alumno
        actualizarGrafico(estadisticasAlumno);

        // Actualizar el label del total de partes (solo para este alumno)
        int totalPartesAlumno = estadisticasAlumno.stream()
                .mapToInt(o -> ((Long) o[1]).intValue())
                .sum();
        lblTotalPartes.setText("Total de partes para " + numeroExpediente + ": " + totalPartesAlumno);
    }

    @FXML
    public void onClickmostrarEstadisticasGlobales(ActionEvent actionEvent) {
        // Obtener las estadísticas globales
        List<Object[]> estadisticasGlobales = partesCRUD.obtenerEstadisticasPartesPorColor();

        // Actualizar el gráfico de barras con los datos globales
        actualizarGrafico(estadisticasGlobales);

        // Actualizar el label del total de partes globales
        int totalPartesGlobal = partesCRUD.obtenerPartes().size();
        lblTotalPartes.setText("Total de partes: " + totalPartesGlobal);

        // Limpiar el campo de texto del número de expediente
        txtNumeroExpediente.clear();
    }

    private void actualizarGrafico(List<Object[]> estadisticas) {
        // Limpiar el gráfico actual
        barChartEstadisticas.getData().clear();

        // Crear una nueva serie de datos
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Partes por Color");

        // Rellenar los datos
        for (Object[] resultado : estadisticas) {
            ColorParte color = (ColorParte) resultado[0]; // Enum del color
            Long cantidad = (Long) resultado[1];          // Cantidad de partes
            XYChart.Data<String, Number> data = new XYChart.Data<>(color.name(), cantidad);

            // Aplicar el color correspondiente a la barra
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    newValue.setStyle("-fx-bar-fill: " + color.getCodigo_color() + ";");
                }
            });

            series.getData().add(data);
        }

        // Agregar la nueva serie al gráfico
        barChartEstadisticas.getData().add(series);
    }
}