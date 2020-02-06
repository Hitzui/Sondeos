package com.dysconcsa.sondeos.controller;


import java.util.List;

import com.dysconcsa.sondeos.model.ValoresProperty;
import com.dysconcsa.sondeos.util.Utility;
import com.jfoenix.controls.JFXButton;

import io.datafx.controller.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@FXMLController("/view/Grafico.fxml")
public class GraficarController {

    private Stage dialogStage;
    private ObservableList<ValoresProperty> valoresProperties = FXCollections.observableArrayList();

    public void setValoresProperties(ObservableList<ValoresProperty> valoresProperties) {
        this.valoresProperties = valoresProperties;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    private JFXButton btnCerrar;

    private LineChart<Number, Number> chartSondeos;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @FXML
    public void initialize() {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        chartSondeos = new LineChart<>(xAxis, yAxis);
        chartSondeos.setPrefWidth(500);
        chartSondeos.setPrefHeight(680);
        chartSondeos.setLayoutX(300 / 2);
        chartSondeos.setLayoutY(-80);
        chartSondeos.setRotate(90);
        XYChart.Series series = new XYChart.Series();
        series.setName(null);
        anchorPane.getChildren().add(chartSondeos);
        AnchorPane.setLeftAnchor(chartSondeos, 100d);
        AnchorPane.setTopAnchor(chartSondeos, -50d);
        Utility utility = new Utility();
        /* exportar grafico como png */
        btnGuardar.setOnAction(event -> {
            utility.saveAsPng(chartSondeos, anchorPane);
        });

    }
}
