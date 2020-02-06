package com.dysconcsa.sondeos.controller;

import com.dysconcsa.sondeos.dao.DaoConfiguration;
import com.dysconcsa.sondeos.model.ConfigurationProperty;
import com.dysconcsa.sondeos.util.ImageLoader;
import com.dysconcsa.sondeos.util.Utility;
import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.container.AnimatedFlowContainer;
import io.datafx.controller.flow.container.ContainerAnimations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController(value = "/view/Main.fxml", title = "Aplicacion de Sondeos - DYSCONCSA")
public class MainController extends AbstractController implements Initializable {

    boolean openFormValores;
    StackPane formValores;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView imagenEmpresa;

    @FXML
    @ActionTrigger("tipoSuelo")
    private Button btnTipoSuelo;

    @FXML
    @ActionTrigger("ingresoValores")
    private Button btnIngresarValores;

    @FXML
    @ActionTrigger("actionClientes")
    private Button btnClientes;

    @FXML
    @ActionTrigger("actionConfiguration")
    private Button btnConfiguration;

    private FlowHandler flowHandler;

    @PostConstruct
    public void init() {
        ImageLoader imageLoader = new ImageLoader();
        try {
            onIngresoValores();
            DaoConfiguration daoConfiguration = new DaoConfiguration();
            ConfigurationProperty configurationProperty = daoConfiguration.findOne();
            imageLoader.updateImageView(imagenEmpresa, String.valueOf(new File(configurationProperty.getImagen()).toURI()));
        } catch (Exception ignored) {
        }
    }

    @ActionMethod("tipoSuelo")
    public void onTipoSuelo() throws FlowException {
        Flow flow = new Flow(TipoSueloController.class);
        flowHandler = flow.createHandler();
        centerPane.getChildren().clear();
        centerPane.getChildren().add(flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.SWIPE_RIGHT)));
    }

    @ActionMethod("ingresoValores")
    public void onIngresoValores() throws FlowException {
        Flow flow = new Flow(FormValoresController.class);
        flowHandler = flow.createHandler();
        centerPane.getChildren().clear();
        formValores = flowHandler.start();
        centerPane.getChildren().add(formValores);
        for(Node node: centerPane.getChildren()){
            System.out.println(node.getId());
        }
    }

    @ActionMethod("actionClientes")
    public void action_clientes() {
        Flow flow = new Flow(ClienteController.class);
        flowHandler = flow.createHandler();
        try {
            centerPane.getChildren().clear();
            StackPane pane = flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.SWIPE_RIGHT));
            centerPane.getChildren().add(pane);
        } catch (FlowException e) {
            e.printStackTrace();
        }
    }

    @ActionMethod("actionConfiguration")
    public void action_configuration() {
        // Load the fxml file and create a new stage for the popup dialog.
        Flow flow = new Flow(ConfigurationController.class);
        flowHandler = flow.createHandler();
        try {
            StackPane pane = flowHandler.start();
            Utility utility = new Utility();
            Stage dialogStage = utility.showDialogStage((Stage) btnConfiguration.getScene().getWindow(), pane, "Establecer configuracion");
            ConfigurationController configurationController = (ConfigurationController) flowHandler.getCurrentView().getViewContext().getController();
            configurationController.setStage(dialogStage);
            dialogStage.showAndWait();
            if (!configurationController.isCancel) {
                imagenEmpresa.setImage(new Image(String.valueOf(new File(configurationController.getPathImagen()).toURI())));
            }
        } catch (FlowException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
