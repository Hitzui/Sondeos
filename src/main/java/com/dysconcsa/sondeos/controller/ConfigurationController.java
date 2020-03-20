package com.dysconcsa.sondeos.controller;

import java.io.File;
import java.net.URISyntaxException;

import com.dysconcsa.sondeos.dao.DaoConfiguration;
import com.dysconcsa.sondeos.model.ConfigurationProperty;
import com.dysconcsa.sondeos.util.ImageLoader;
import com.dysconcsa.sondeos.util.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import io.datafx.controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

@FXMLController("/view/Configuration.fxml")
public class ConfigurationController {

    private String pathImagen = "";
    private final String _title = "Configuracion";
    private Stage stage;
    Boolean isCancel;

    String getPathImagen() {
        return pathImagen;
    }

    public Stage getStage() {
        return stage;
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private JFXTextField txtNombreEmpresa;

    @FXML
    private JFXButton btnBuscarImagen;

    @FXML
    private ImageView imagen;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private void initialize() throws URISyntaxException {
        DaoConfiguration daoConfiguration = new DaoConfiguration();
        ImageLoader imageLoader = new ImageLoader();
        //imageLoader.updateImageView(imagen, String.valueOf(getClass().getResource("/image/logo.jpg").toURI()));
        try {
            ConfigurationProperty configurationProperty = daoConfiguration.findOne();
            txtNombreEmpresa.setText(configurationProperty.getNombreEmpresa());
            imageLoader.updateImageView(imagen,String.valueOf(new File(configurationProperty.getImagen()).toURI()));
        } catch (Exception ex) {
            imageLoader.updateImageView(imagen, String.valueOf(getClass().getResource("/image/logo.jpg").toURI()));
        }
        Utility utility = new Utility();
        btnCancelar.setOnAction(e -> {
            isCancel=true;
            stage.close();
        });
        btnGuardar.setOnAction(e -> {
            if (txtNombreEmpresa.getText().length() <= 0) {
                Utility.dialog(_title, "", "Debe especificar el nombre de la empresa para aplicar los cambios.");
                txtNombreEmpresa.requestFocus();
                return;
            }
            ConfigurationProperty configurationProperty = daoConfiguration.findOne();
            if (configurationProperty == null) {
                if (pathImagen.length() <= 0) {
                    Utility.dialog(_title, "", "Debe especificar una imagen de la empresa para poder continuar.");
                    return;
                }
                configurationProperty = new ConfigurationProperty(0, txtNombreEmpresa.getText(), pathImagen);
                daoConfiguration.save(configurationProperty);
            } else {
                if (pathImagen.length() <= 0) {
                    pathImagen = configurationProperty.getImagen();
                }
                configurationProperty.setNombreEmpresa(txtNombreEmpresa.getText());
                configurationProperty.setImagen(pathImagen);
                daoConfiguration.update(configurationProperty);
            }
            if (daoConfiguration.get_error() != null) {
                daoConfiguration.get_error().printStackTrace();
            } else {
                Utility.dialog("Configuracion general", "", "Se han apalicado los cambios de forma correcta.");
                isCancel = false;
                stage.close();
            }
        });
        btnBuscarImagen.setOnAction(e -> pathImagen = utility.openImage((Stage) btnBuscarImagen.getScene().getWindow(), imagen));
    }
}
