package com.dysconcsa.sondeos.main;

import com.dysconcsa.sondeos.controller.AdemeController;
import com.dysconcsa.sondeos.controller.FormValoresController;
import com.dysconcsa.sondeos.controller.MainController;
import com.dysconcsa.sondeos.dao.DaoConfiguration;
import com.dysconcsa.sondeos.model.ConfigurationProperty;
import com.dysconcsa.sondeos.util.Utility;
import com.sun.javafx.application.LauncherImpl;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Application extends javafx.application.Application {


    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                File f = new File(args[0]);
                if (f.isFile()) {
                    FormValoresController.file = f;
                    writeParameters(f.getPath());
                }
            }
        } catch (Exception ex) {
            writeParameters(ex.getMessage());
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DaoConfiguration daoConfiguration;
        ConfigurationProperty configurationProperty;
        try {
            daoConfiguration = new DaoConfiguration();
            configurationProperty = daoConfiguration.findOne();
            Utility.selectedConfiguration = configurationProperty;
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
            if (FormValoresController.file != null) {
                primaryStage.setTitle("Aplicacion de Sondeos - " + configurationProperty.getNombreEmpresa().toUpperCase() + " - " + FormValoresController.file.getName());
            } else {
                primaryStage.setTitle("Aplicacion de Sondeos - " + configurationProperty.getNombreEmpresa().toUpperCase());
            }
        } catch (Exception ex) {
            primaryStage.setTitle("Aplicacion de Sondeos");
        }
        primaryStage.show();
    }

    public static void writeParameters(String value) {
        PrintWriter fw;
        try {
            fw = new PrintWriter("messages.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("--Start line--");
            bw.newLine();
            bw.write(value);
            bw.newLine();
            bw.write("--End line--");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
