package com.dysconcsa.sondeos.controller;

import java.util.List;

import com.dysconcsa.sondeos.dao.DaoEmpresa;
import com.dysconcsa.sondeos.model.EmpresaProperty;
import com.dysconcsa.sondeos.util.AlertError;

import io.datafx.controller.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@FXMLController("/view/BuscarClienteDialogo.fxml")
public class BuscarEmpresaController {

    private boolean seleccionar = false;
    private EmpresaProperty empresaProperty;
    private ObservableList<EmpresaProperty> empresaProperties = FXCollections.observableArrayList();
    private Stage dialogStage;

    @FXML
    private TableView<EmpresaProperty> tableClientes;

    @FXML
    private TableColumn<EmpresaProperty, Integer> colCodigo;

    @FXML
    private TableColumn<EmpresaProperty, String> colCliente;

    @FXML
    private TableColumn<EmpresaProperty, String> colProyecto;

    @FXML
    private TableColumn<EmpresaProperty, String> colOperador;

    @FXML
    private Button btnSeleccionar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtFiltrar;

    public BuscarEmpresaController() {
    }

    EmpresaProperty getEmpresaProperty() {
        return this.empresaProperty;
    }

    public void setEmpresaProperty(EmpresaProperty empresaProperty) {
        this.empresaProperty = empresaProperty;
    }

    void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void initialize() {
        this.colCodigo.setCellValueFactory((cellData) -> cellData.getValue().idProperty().asObject());
        this.colCliente.setCellValueFactory((value) -> (value.getValue()).clienteProperty());
        this.colProyecto.setCellValueFactory((value) -> (value.getValue()).proyectoProperty());
        this.colOperador.setCellValueFactory((value) -> value.getValue().operadorProperty());
        this.loadData();
        this.tableClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.empresaProperty = newValue;
        });
        this.btnCancelar.setOnAction((e) -> this.dialogStage.close());
        this.btnSeleccionar.setOnAction((e) -> {
            this.seleccionar = true;
            this.dialogStage.close();
        });
        FilteredList<EmpresaProperty> filteredList = new FilteredList<>(empresaProperties, e -> true);
        txtFiltrar.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredList.setPredicate(e->{
                if(newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if(e.getCliente().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(e.getProyecto().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        }));
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<EmpresaProperty> sortedData = new SortedList<>(filteredList);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableClientes.comparatorProperty());
        tableClientes.setItems(sortedData);
    }

    private void loadData() {
        DaoEmpresa daoEmpresa = new DaoEmpresa();
        List<EmpresaProperty> list = daoEmpresa.findAll();
        this.empresaProperties.clear();
        this.empresaProperties.addAll(list);
        this.tableClientes.setItems(this.empresaProperties);
        if (daoEmpresa.get_error() != null) {
            AlertError.showAlert(daoEmpresa.get_error());
        }

    }

    boolean isSeleccionar() {
        return this.seleccionar;
    }
}
