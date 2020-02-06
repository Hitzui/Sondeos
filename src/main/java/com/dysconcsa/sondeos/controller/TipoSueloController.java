package com.dysconcsa.sondeos.controller;

import com.dysconcsa.sondeos.dao.DaoSuelos;
import com.dysconcsa.sondeos.model.SuelosProperty;
import com.dysconcsa.sondeos.util.AlertError;
import com.dysconcsa.sondeos.util.Utility;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.container.AnimatedFlowContainer;
import io.datafx.controller.flow.container.ContainerAnimations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

@FXMLController("/view/TipoSuelo.fxml")
public class TipoSueloController extends AbstractController implements Initializable {

    private static final String _eliminar = "Eliminar";

    private ObservableList<SuelosProperty> suelosProperties = FXCollections.observableArrayList();

    static SuelosProperty suelosProperty;

    @FXML
    private AnchorPane datoSuelos;

    @FXML
    private TableView<SuelosProperty> tableSuelos;

    @FXML
    private TableColumn<SuelosProperty, String> colDescripcion;

    @FXML
    private TableColumn<SuelosProperty, String> colSimbolo;

    @FXML
    private TableColumn<SuelosProperty, String> colImagen;

    @FXML
    @ActionTrigger("nuevoRegistroSuelos")
    private Button btnNuevo;

    @FXML
    @ActionTrigger("editarRegistroSuelos")
    private Button btnEditar;

    @FXML
    @ActionTrigger("eliminarRegistroSuelos")
    private Button btnEliminar;

    private JFXPopup popup;

    @PostConstruct
    void init() {
    }

    private void loadDatas() {
        DaoSuelos daoSuelos = new DaoSuelos();
        try {
            suelosProperties.clear();
            suelosProperties.addAll(daoSuelos.findAll());
            tableSuelos.setItems(suelosProperties);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDatas();
        colDescripcion.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colSimbolo.setCellValueFactory(cellData -> cellData.getValue().simboloProperty());
        colImagen.setCellValueFactory(cellData -> cellData.getValue().imagenProperty());
        tableSuelos.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> suelosProperty = newValue));
        tableSuelos.setRowFactory(event -> {
            final TableRow<SuelosProperty> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getButton().name().equals("SECONDARY") && (!row.isEmpty())) {
                    initPopup(row, e.getX(), e.getY());
                }
            });
            return row;
        });
    }

    @ActionMethod("nuevoRegistroSuelos")
    public void action_nuevoRegistro() {
        suelosProperty = null;
        loadForm();
    }

    @ActionMethod("editarRegistroSuelos")
    public void action_editarRegistro() {
        if (suelosProperty == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Editar");
            alert.setHeaderText("Tipo de Suelos");
            alert.setContentText("Debe seleccionar un valor para poder editarlo, intente nuevamente.");
            alert.showAndWait();
            return;
        }
        loadForm();
    }

    @ActionMethod("eliminarRegistroSuelos")
    public void action_eliminarRegistro() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle(_eliminar);
        confirm.setContentText("¿Seguro desea eliminar los datos, esta accion no se puede revertir?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.CANCEL) {
                return;
            }
        }
        DaoSuelos daoSuelos = new DaoSuelos();
        if (suelosProperty == null) {
            dialog("Debe especificar un valor para poder eliminar, intente de nuevo, y seleccione uno de la tabla.");
        }
        daoSuelos.delete(suelosProperty.getID());
        if (daoSuelos.get_error() != null) {
            AlertError.showAlert(daoSuelos.get_error());
        } else {
            dialog("Se ha eliminado el registro de forma correcta");
            loadDatas();
        }
    }

    private void loadForm() {
        Flow flow = new Flow(FormSuelosController.class);
        FlowHandler flowHandler = flow.createHandler();
        try {
            centerPane.getChildren().clear();
            centerPane.getChildren().add(flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.SWIPE_RIGHT)));
        } catch (FlowException e) {
            e.printStackTrace();
        }
    }

    private void dialog(String content) {
        Utility.dialog(TipoSueloController._eliminar, "", content);
    }

    private void initPopup(TableRow<SuelosProperty> tableRow, double x, double y) {
        JFXListView<String> list = new JFXListView<>();
        String stylesheet = getClass().getResource("/css/application.css").toExternalForm();
        list.getStylesheets().add(stylesheet);
        list.getItems().addAll("Editar", "Eliminar", "Nuevo");
        list.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            switch ((int) newValue) {
                case 0:
                    action_editarRegistro();
                    break;
                case 1:
                    action_eliminarRegistro();
                    break;
                case 2:
                    action_nuevoRegistro();
                    break;
                default:
                    break;
            }
            popup.hide();
        }));
        popup = new JFXPopup(list);
        popup.show(tableRow, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, x, y);
    }
}
