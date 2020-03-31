package com.dysconcsa.sondeos.controller;

import com.dysconcsa.sondeos.dao.DaoEmpresa;
import com.dysconcsa.sondeos.model.EmpresaProperty;
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
import javafx.util.Duration;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@FXMLController("/view/Clientes.fxml")
public class ClienteController extends AbstractController implements Initializable {

    static EmpresaProperty empresaProperty;
    private static final String _cliente = "Cliente";
    private ObservableList<EmpresaProperty> empresaProperties = FXCollections.observableArrayList();
    int idcliente;

    public int getIdcliente() {
        return idcliente;
    }

    private void loadData() {
        try {
            DaoEmpresa daoEmpresa = new DaoEmpresa();
            empresaProperties.clear();
            empresaProperties.addAll(daoEmpresa.findAll());
            tableClientes.setItems(empresaProperties);
        } catch (Exception ex) {
            AlertError.showAlert(ex);
        }
    }

    private JFXPopup popup;

    @FXML
    private TableView<EmpresaProperty> tableClientes;

    @FXML
    private TableColumn<EmpresaProperty, Integer> colCodigoCliente;

    @FXML
    private TableColumn<EmpresaProperty, String> colNombreCliente;

    @FXML
    private TableColumn<EmpresaProperty, String> colProyecto;

    @FXML
    private TableColumn<EmpresaProperty, String> colFecha;

    @FXML
    @ActionTrigger("actionNuevoCliente")
    private Button btnNuevo;

    @FXML
    @ActionTrigger("actionEditarCliente")
    private Button btnEditar;

    @FXML
    @ActionTrigger("actionEliminarCliente")
    private Button btnEliminar;

    @FXML
    @ActionTrigger("action_btnSeleccionar")
    private Button btnSeleccionar;

    void habilitarBtnSeleccionar() {
        btnSeleccionar.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCodigoCliente.setCellValueFactory(value -> value.getValue().idProperty().asObject());
        colNombreCliente.setCellValueFactory(value -> value.getValue().clienteProperty());
        colFecha.setCellValueFactory(value -> value.getValue().fechaProperty());
        colProyecto.setCellValueFactory(value -> value.getValue().proyectoProperty());
        tableClientes.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> empresaProperty = newValue));
        tableClientes.setRowFactory(event -> {
            final TableRow<EmpresaProperty> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getButton().name().equals("SECONDARY") && (!row.isEmpty())) {
                    initPopup(row, e.getX(), e.getY());
                }
            });

            return row;
        });
        loadData();
    }

    @ActionMethod("actionNuevoCliente")
    public void action_nuevoCliente() {
        try {
            empresaProperty = null;
            loadForm();
        } catch (Exception ex) {
            AlertError.showAlert(ex);
        }
    }

    @ActionMethod("actionEditarCliente")
    public void action_editarCliente() {
        try {
            if (empresaProperty == null) {
                dialog("Editar cliente", "Debe especificar un valor para poder editar, intente nuevamente.");
                return;
            }
            loadForm();
        } catch (Exception ex) {
            AlertError.showAlert(ex);
        }
    }

    @ActionMethod("actionEliminarCliente")
    public void action_eliminarCliente() {
        try {
            Utility utility = new Utility();
            Optional<ButtonType> result = utility.resultDialog(_cliente, "¿Seguro desea elimninar al cliente seleccionado, esta accion no puede revertirse?");
            if (result.get() == ButtonType.CANCEL) return;
            if (empresaProperty == null) {
                dialog("Eliminar cliente", "Debe especificar un valor para poder eliminar al cliente, intente nuevamente.");
                return;
            }
            DaoEmpresa daoEmpresa = new DaoEmpresa();
            daoEmpresa.delete(empresaProperty.getId());
            if (daoEmpresa.get_error() != null) {
                AlertError.showAlert(daoEmpresa.get_error());
                return;
            }
            dialog("Eliminar Cliente", "Se ha eliminado al cliente seleccionado");
            loadData();
        } catch (Exception ex) {
            AlertError.showAlert(ex);
        }
    }

    @ActionMethod("action_btnSeleccionar")
    public void action_btnSeleccionar() {
        Utility.idcliente = tableClientes.getSelectionModel().getSelectedItem().getId();

    }

    private void loadForm() {
        Flow flow = new Flow(FormClienteController.class);
        FlowHandler flowHandler = flow.createHandler();
        try {
            centerPane.getChildren().clear();
            centerPane.getChildren().add(flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.SWIPE_RIGHT)));
        } catch (FlowException e) {
            e.printStackTrace();
        }
    }

    private void dialog(String header, String content) {
        Utility.dialog(ClienteController._cliente, header, content);
    }

    private void initPopup(TableRow<EmpresaProperty> row, double x, double y) {
        JFXListView<String> list = new JFXListView<>();
        String stylesheet = getClass().getResource("/css/application.css").toExternalForm();
        list.getStylesheets().add(stylesheet);
        list.getItems().addAll("Editar", "Eliminar", "Nuevo");
        list.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            switch ((int) newValue) {
                case 0:
                    action_editarCliente();
                    break;
                case 1:
                    action_eliminarCliente();
                    break;
                case 2:
                    action_nuevoCliente();
                    break;
                default:
                    break;
            }
            popup.hide();
        }));
        popup = new JFXPopup(list);
        popup.show(row, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, x, y);
    }
}
