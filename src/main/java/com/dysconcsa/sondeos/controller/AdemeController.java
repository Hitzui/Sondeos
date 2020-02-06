package com.dysconcsa.sondeos.controller;

import com.dysconcsa.sondeos.model.AdemeProperty;
import com.dysconcsa.sondeos.util.AdemeConverter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@FXMLController("/view/MenuAdeme.fxml")
public class AdemeController extends AbstractController {

    private Stage dialogStage;
    public static ObservableList<AdemeProperty> ademeProperties;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    @ActionTrigger("actionBtnSalir")
    private JFXButton btnSalir;

    @FXML
    @ActionTrigger("actionBtnIngresar")
    private JFXButton btnIngresar;

    @FXML
    @ActionTrigger("actionBtnEliminar")
    private JFXButton btnEliminar;

    @FXML
    @ActionTrigger("actionBtnLimpiarDatos")
    private JFXButton btnLimpiarLista;

    @FXML
    private JFXTextField txtAdeme;

    @FXML
    private ListView<AdemeProperty> listView;

    @FXML
    public void initialize() {
        if (ademeProperties == null) {
            ademeProperties = FXCollections.observableArrayList();
        }
        listView.setEditable(true);
        txtAdeme.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                txtAdeme.setText(oldValue);
            }
        });
        txtAdeme.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                action_btnIngresar();
            }
        });
        listView.setItems(ademeProperties);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                txtAdeme.setText("0");
            } else {
                txtAdeme.setText(String.valueOf(newValue.getProfundidad()));
            }
        });
        listView.setCellFactory(converter -> {
            TextFieldListCell<AdemeProperty> cell = new TextFieldListCell<>();
            cell.setConverter(new AdemeConverter(cell));
            return cell;
        });
        listView.setOnEditCommit(event -> {
            System.out.println(event.getNewValue());
        });
    }

    @ActionMethod("actionBtnIngresar")
    public void action_btnIngresar() {
        Double valor;
        if (txtAdeme.getText().trim().length() <= 0) {
            return;
        }
        valor = Double.parseDouble(txtAdeme.getText());
        AdemeProperty ademeProperty = new AdemeProperty(valor, "Se ademo hasta " + valor);
        listView.getItems().add(ademeProperty);
        txtAdeme.setText("");
        txtAdeme.requestFocus();
    }

    @ActionMethod("actionBtnEliminar")
    public void action_btnEliminar() {
        AdemeProperty selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            listView.getItems().remove(selectedItem);
        }
    }

    @ActionMethod("actionBtnSalir")
    public void action_btnSalir() {
        dialogStage.close();
    }

    @ActionMethod("actionBtnLimpiarDatos")
    public void action_btnLimpiarDatos() {
        ademeProperties.clear();
        listView.getItems().clear();
    }

    void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    ObservableList<AdemeProperty> getAdemeProperties() {
        return ademeProperties;
    }

    void setAdemeProperties(ObservableList<AdemeProperty> ademeProperties) {
        AdemeController.ademeProperties = ademeProperties;
    }
}
