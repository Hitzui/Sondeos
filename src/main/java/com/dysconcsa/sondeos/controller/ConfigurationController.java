package com.dysconcsa.sondeos.controller;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Optional;

import com.dysconcsa.sondeos.dao.DaoConfiguration;
import com.dysconcsa.sondeos.model.ConfigurationProperty;
import com.dysconcsa.sondeos.model.EmpresaProperty;
import com.dysconcsa.sondeos.util.ImageLoader;
import com.dysconcsa.sondeos.util.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import io.datafx.controller.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.rmi.CORBA.Util;

@FXMLController("/view/Configuration.fxml")
public class ConfigurationController {

    private String pathImagen = "";
    private final String _title = "Configuracion";
    private Stage stage;
    int aux;
    Boolean isCancel;
    private ConfigurationProperty selectedEmpresa;
    private ObservableList<ConfigurationProperty> configurationProperties = FXCollections.observableArrayList();
    ImageLoader imageLoader = new ImageLoader();

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
    private JFXButton btnNew;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSelection;

    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXListView<ConfigurationProperty> listEmpresas;

    @FXML
    private void initialize() {
        Utility utility = new Utility();
        loadData();
        DaoConfiguration daoConfiguration = new DaoConfiguration();

        //imageLoader.updateImageView(imagen, String.valueOf(getClass().getResource("/image/logo.jpg").toURI()));
        btnNew.setOnAction(event -> selectedEmpresa = null);
        btnDelete.setOnAction(event -> {
            if (selectedEmpresa == null) {
                Utility.dialog(_title, "", "Debe especificar un valor para poder continuar.");
                return;
            }
            Optional<ButtonType> response = utility.resultDialog("Eliminar valor",
                    "Seguro desea elminar el valor seleccionado, esta accion no se puede deshacer.");
            if (response.isPresent()) {
                if (response.get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    daoConfiguration.delete(selectedEmpresa);
                    if (daoConfiguration.get_error() != null) {
                        daoConfiguration.get_error().printStackTrace();
                    } else {
                        loadData();
                    }
                    Utility.dialog("Eliminar valor", "", "Se ha eliminado el valor seleccionado");
                    selectedEmpresa = null;
                    txtNombreEmpresa.clear();
                    try {
                        imageLoader.updateImageView(imagen, String.valueOf(getClass().getResource("/image/logo.jpg").toURI()));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        btnSelection.setOnAction(event -> {
            Utility.selectedConfiguration = selectedEmpresa;
            isCancel = true;
            stage.close();
        });
        /*try {
            ConfigurationProperty configurationProperty = daoConfiguration.findOne();
            txtNombreEmpresa.setText(configurationProperty.getNombreEmpresa());
            imageLoader.updateImageView(imagen, String.valueOf(new File(configurationProperty.getImagen()).toURI()));
        } catch (Exception ex) {
            imageLoader.updateImageView(imagen, String.valueOf(getClass().getResource("/image/logo.jpg").toURI()));
        }*/
        btnCancelar.setOnAction(e -> {
            isCancel = true;
            stage.close();
        });
        btnSave.setOnAction(e -> {
            if (txtNombreEmpresa.getText().length() <= 0) {
                Utility.dialog(_title, "", "Debe especificar el nombre de la empresa para aplicar los cambios.");
                txtNombreEmpresa.requestFocus();
                return;
            }
            if (selectedEmpresa == null) {
                if (pathImagen.length() <= 0) {
                    Utility.dialog(_title, "", "Debe especificar una imagen de la empresa para poder continuar.");
                    return;
                }
                selectedEmpresa = new ConfigurationProperty(0, txtNombreEmpresa.getText(), pathImagen);
                daoConfiguration.save(selectedEmpresa);
            } else {
                if (pathImagen.length() <= 0) {
                    pathImagen = selectedEmpresa.getImagen();
                }
                selectedEmpresa.setNombreEmpresa(txtNombreEmpresa.getText());
                selectedEmpresa.setImagen(pathImagen);
                daoConfiguration.update(selectedEmpresa);
            }
            if (daoConfiguration.get_error() != null) {
                daoConfiguration.get_error().printStackTrace();
            } else {
                Utility.dialog("Configuracion general", "", "Se han apalicado los cambios de forma correcta.");
                loadData();
            }
        });
        btnBuscarImagen.setOnAction(e -> pathImagen = utility.openImage((Stage) btnBuscarImagen.getScene().getWindow(), imagen));
        listEmpresas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            txtNombreEmpresa.setText(newValue.getNombreEmpresa());
            //imagen.setImage(new Image(newValue.getImagen()));
            imageLoader.updateImageView(imagen, String.valueOf(new File(newValue.getImagen()).toURI()));
            this.selectedEmpresa = newValue;
        });
    }

    void loadData() {
        if (Utility.selectedConfiguration != null) {
            selectedEmpresa = Utility.selectedConfiguration;
            txtNombreEmpresa.setText(selectedEmpresa.getNombreEmpresa());
            imageLoader.updateImageView(imagen, String.valueOf(new File(selectedEmpresa.getImagen()).toURI()));
        }
        if (configurationProperties.size() > 0) {
            configurationProperties.clear();
            listEmpresas.getItems().clear();
        }
        DaoConfiguration daoConfiguration = new DaoConfiguration();
        configurationProperties.addAll(daoConfiguration.findAll());
        listEmpresas.getItems().addAll(configurationProperties);
        listEmpresas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}
