package com.example.proyecto_hibernate.controllers;

import com.example.proyecto_hibernate.CRUD.PartesCRUD;
import com.example.proyecto_hibernate.classes.ColorParte;
import com.example.proyecto_hibernate.util.Alerta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class EstadisticasController {

    @FXML
    private BarChart<String, Number> barChartEstadisticas;

    @FXML
    private Label lblTotalPartes;

    @FXML
    private TextField txtNumeroExpediente;

    @FXML
    private Button bt_buscar;

    @FXML
    private Button bt_buscarCurso;

    @FXML
    private TextField txtNumeroCurso;

    @FXML
    private Button bt_limpiar;

    @FXML
    private Button bt_limpiar2;

    private PartesCRUD partesCRUD;

    @FXML
    public void initialize() {
        partesCRUD = new PartesCRUD();
        mostrarEstadisticasGlobales();
        buscarEstadisticasPorAlumno();
    }

    public void mostrarEstadisticasGlobales() {
        //Limpiamos los datos existentes
        barChartEstadisticas.getData().clear();


        //Obtenemos estadísticas globales
        List<Object[]> estadisticasGlobales = partesCRUD.obtenerEstadisticasPartesPorColor();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Estadísticas Globales");

        //Llenamos la gráfica con las estadísticas globales
        for (Object[] resultado : estadisticasGlobales) {
            ColorParte colorParte = (ColorParte) resultado[0];
            String colorString = colorParte.name();
            Long cantidad = (Long) resultado[1];

            XYChart.Data<String, Number> data = new XYChart.Data<>(colorString, cantidad);
            series.getData().add(data);

            // Aplicamos estilo de color
            final String colorHex = colorParte.getCodigo_color();
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    newValue.setStyle("-fx-bar-fill: " + colorHex + ";");
                }
            });
        }

        barChartEstadisticas.getData().add(series);

        //Mostramos el total de partes
        int totalPartes = partesCRUD.obtenerPartes().size();
        lblTotalPartes.setText("Total de partes (global): " + totalPartes);
    }

    public void buscarEstadisticasPorAlumno() {
        //Obtenemos el número de expediente
        String numeroExpediente = txtNumeroExpediente.getText().trim();

        if (numeroExpediente.isEmpty()) {
            lblTotalPartes.setText("Por favor, introduce un número de expediente o de curso.");
            return;
        }

        //Obtenemos estadísticas filtradas por alumno
        List<Object[]> estadisticasAlumno = partesCRUD.obtenerEstadisticasPartesPorAlumno(numeroExpediente);

        //Limpiamos los datos existentes
        barChartEstadisticas.getData().clear();

        if (estadisticasAlumno.isEmpty()) {
            lblTotalPartes.setText("No se encontraron partes para el alumno con Nº expediente: " + numeroExpediente);
            return;
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Estadísticas de Alumno: " + numeroExpediente);

        //Llenamos la gráfica con los datos
        for (Object[] resultado : estadisticasAlumno) {
            ColorParte colorParte = (ColorParte) resultado[0];
            String colorString = colorParte.name();
            Long cantidad = (Long) resultado[1];

            XYChart.Data<String, Number> data = new XYChart.Data<>(colorString, cantidad);
            series.getData().add(data);

            final String colorHex = colorParte.getCodigo_color();
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    newValue.setStyle("-fx-bar-fill: " + colorHex + ";");
                }
            });
        }

        barChartEstadisticas.getData().add(series);

        //Mostramos el total de partes en el Label
        int totalPartesAlumno = estadisticasAlumno.stream()
                .mapToInt(resultado -> ((Long) resultado[1]).intValue())
                .sum();
        lblTotalPartes.setText("Total de partes del alumno: " + totalPartesAlumno);
    }

    @FXML
    public void onClickbuscarEstadisticasPorAlumno(ActionEvent actionEvent) {
        //Obtenemos el número de expediente
        String numeroExpediente = txtNumeroExpediente.getText().trim();

        if (numeroExpediente.isEmpty()) {
            Alerta.mensajeInfo("Advertencia", "Campo vacío", "Por favor, introduce un número de expediente.");
            return;
        }

        //Obtenemos las estadísticas por alumno
        List<Object[]> estadisticasAlumno = partesCRUD.obtenerEstadisticasPartesPorAlumno(numeroExpediente);

        if (estadisticasAlumno.isEmpty()) {
            Alerta.mensajeInfo("Sin resultados", "No se encontraron partes", "No hay registros para el alumno con expediente: " + numeroExpediente);
            return;
        }

        //Actualizamos el gráfico de barras con los datos del alumno
        actualizarGrafico(estadisticasAlumno);

        //Actualizamos el label del total de partes (solo para este alumno)
        int totalPartesAlumno = estadisticasAlumno.stream()
                .mapToInt(o -> ((Long) o[1]).intValue())
                .sum();
        lblTotalPartes.setText("Total de partes para " + numeroExpediente + ": " + totalPartesAlumno);
    }

    private void actualizarGrafico(List<Object[]> estadisticas) {
        barChartEstadisticas.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Partes por Color");

        //Rellenamos los datos
        for (Object[] resultado : estadisticas) {
            ColorParte color = (ColorParte) resultado[0]; // Enum del color
            Long cantidad = (Long) resultado[1];          // Cantidad de partes
            XYChart.Data<String, Number> data = new XYChart.Data<>(color.name(), cantidad);

            //Aplicamos el color correspondiente a la barra
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    newValue.setStyle("-fx-bar-fill: " + color.getCodigo_color() + ";");
                }
            });

            series.getData().add(data);
        }
        barChartEstadisticas.getData().add(series);
    }
    @FXML
    public void onClickbuscarEstadisticasPorCurso(ActionEvent actionEvent) {
        String curso = txtNumeroCurso.getText().trim();

        if (curso.isEmpty()) {
            Alerta.mensajeInfo("Advertencia", "Campo vacío", "Por favor, introduce un nombre de curso.");
            return;
        }

        try {
            //Obtenemos las estadísticas por curso
            List<Object[]> estadisticasCurso = partesCRUD.obtenerEstadisticasPartesPorCurso(curso);

            if (estadisticasCurso.isEmpty()) {
                Alerta.mensajeInfo("Sin resultados", "No se encontraron partes", "No hay registros para el curso: " + curso);
                return;
            }
            actualizarGrafico(estadisticasCurso);

            // Calcular y actualizar el total de partes del curso
            int totalPartesCurso = estadisticasCurso.stream()
                    .mapToInt(o -> ((Long) o[1]).intValue())
                    .sum();
            lblTotalPartes.setText("Total de partes para el curso " + curso + ": " + totalPartesCurso);
        } catch (Exception e) {
            // Manejar posibles errores en la consulta
            Alerta.mensajeError("Error", "No se pudieron obtener las estadísticas");
        }
    }


    public void onClickLimpiar(ActionEvent actionEvent) {
        //Obtenemos las estadísticas globales
        List<Object[]> estadisticasGlobales = partesCRUD.obtenerEstadisticasPartesPorColor();

        //Actualizamos el gráfico de barras con los datos globales
        actualizarGrafico(estadisticasGlobales);

        //Actualizamos el label del total de partes globales
        int totalPartesGlobal = partesCRUD.obtenerPartes().size();
        lblTotalPartes.setText("Total de partes: " + totalPartesGlobal);

        //Limpiamos el campo de texto del número de expediente
        txtNumeroExpediente.clear();
        txtNumeroCurso.clear();
    }
}
