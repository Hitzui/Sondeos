package com.dysconcsa.sondeos.main;

import com.dysconcsa.sondeos.controller.AdemeController;
import com.dysconcsa.sondeos.controller.MainController;
import com.dysconcsa.sondeos.dao.DaoConfiguration;
import com.dysconcsa.sondeos.model.ConfigurationProperty;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DaoConfiguration daoConfiguration;
        ConfigurationProperty configurationProperty;
        try {
            daoConfiguration = new DaoConfiguration();
            configurationProperty = daoConfiguration.findOne();
        } catch (Exception ex) {
            configurationProperty = new ConfigurationProperty(0, "EMPRESA", "/image/company_name.png");
        }
        try {
            AdemeController.ademeProperties = FXCollections.observableArrayList();
            Flow flow = new Flow(MainController.class);
            FlowHandler flowHandler = flow.createHandler();
            StackPane pane = flowHandler.start(new DefaultFlowContainer());
            pane.getStylesheets().add("/css/application.css");
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.setResizable(true);
            primaryStage.setMaximized(true);
            primaryStage.getIcons().add(new Image("/image/Sondeos.png"));
            primaryStage.setTitle("Aplicacion de Sondeos - " + configurationProperty.getNombreEmpresa().toUpperCase());
        } catch (Exception ex) {
            primaryStage.setTitle("Aplicacion de Sondeos");
        }
        primaryStage.show();
    }
}
