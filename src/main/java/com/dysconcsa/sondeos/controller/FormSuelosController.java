package com.dysconcsa.sondeos.controller;

import com.dysconcsa.sondeos.dao.DaoSuelos;
import com.dysconcsa.sondeos.model.SuelosProperty;
import com.dysconcsa.sondeos.util.AlertError;
import com.dysconcsa.sondeos.util.Utility;
import com.jfoenix.controls.JFXComboBox;
import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFColor;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;


@FXMLController("/view/FormSuelos.fxml")
public class FormSuelosController extends AbstractController implements Initializable {

    private final ObservableList<IndexedColors> itemsColorPoperties = FXCollections.observableArrayList();
    private final ObservableList<FillPatternType> patternTypesProperties = FXCollections.observableArrayList();
    int aux;
    String pathImagen = "";
    SuelosProperty sueloEdit;


    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtSimbologia;

    @FXML
    private JFXComboBox<IndexedColors> cmbColor;

    @FXML
    private JFXComboBox<FillPatternType> cmbPattern;

    @FXML
    @ActionTrigger("btnGuardarSuelo")
    private Button btnGuardarSuelo;

    @FXML
    @LinkAction(TipoSueloController.class)
    private Button btnCancelar;

    @SuppressWarnings("unused")
    private FlowHandler flowHandler;

    @PostConstruct
    void init() {
    }

    @ActionMethod("btnGuardarSuelo")
    public void action_btnGuardarSuelo() {
        try {
            if (txtNombre.getText().length() <= 0) {
                txtNombre.requestFocus();
                return;
            }
            if (txtSimbologia.getText().length() <= 0) {
                txtSimbologia.requestFocus();
                return;
            }
            DaoSuelos daoSuelos = new DaoSuelos();
            Utility utility = new Utility();
            if (utility.resultDialog("Guardar", "¿Seguro desea guardar los datos del tipo de suelo?").get() == ButtonType.CANCEL) {
                return;
            }
            IndexedColors colors = cmbColor.getValue();
            FillPatternType patternType = cmbPattern.getValue();
            switch (aux) {
                //nuevo registro
                case 0:
                    SuelosProperty suelosProperty = new SuelosProperty(0, txtNombre.getText().toUpperCase(), txtSimbologia.getText().toUpperCase(), colors, patternType);
                    daoSuelos.save(suelosProperty);
                    if (daoSuelos.get_error() != null) {
                        AlertError.showAlert(daoSuelos.get_error());
                    }
                    dialog("Guardar", "Tipo de suelo", "Se han ingresado los datos de forma correcta.");
                    clear();

                    break;
                //editar datos
                case 1:
                    suelosProperty = new SuelosProperty(sueloEdit.getID(), txtNombre.getText(), txtSimbologia.getText(), colors, patternType);
                    daoSuelos.update(suelosProperty);
                    if (daoSuelos.get_error() != null) {
                        AlertError.showAlert(daoSuelos.get_error());
                    }
                    dialog("Guardar", "Tipo de suelo", "Se han ingresado los datos de forma correcta.");
                    clear();
                    break;
            }
        } catch (Exception ex) {
            AlertError.showAlert(ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadColors();
            loadPattern();
            sueloEdit = TipoSueloController.suelosProperty;
            //editar datos
            txtSimbologia.setText(sueloEdit.getSimbolo());
            txtNombre.setText(sueloEdit.getNombre());
            aux = 1;
            cmbPattern.getItems().addAll(patternTypesProperties);
            cmbColor.getItems().addAll(itemsColorPoperties);
            if (sueloEdit != null) {
                cmbColor.setValue(sueloEdit.getColor());
                cmbPattern.setValue(sueloEdit.getPattern());
            }
            cmbColor.valueProperty().addListener((observable, oldValue, newValue) -> {
                XSSFColor color = new XSSFColor(newValue, null);
                cmbColor.setStyle(" -fx-background-color: #" + color.getARGBHex().substring(2));
            });
            cmbColor.setCellFactory(lv -> new ListCell<IndexedColors>() {
                        @Override
                        protected void updateItem(IndexedColors item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setBackground(Background.EMPTY);
                                setText("");
                            } else {
                                XSSFColor color = new XSSFColor(item, null);
                                String textColor = color.getARGBHex().substring(2);
                                setBackground(new Background(new BackgroundFill(Color.valueOf(textColor),
                                        CornerRadii.EMPTY,
                                        Insets.EMPTY)));
                                setText(item.name());
                            }
                        }
                    }
            );
        } catch (Exception ex) {
            //nuevo registro
            aux = 0;
        }
    }

    private void dialog(String title, String header, String content) {
        Utility.dialog(title, header, content);
    }

    private void clear() {
        txtNombre.clear();
        txtSimbologia.clear();
        cmbColor.setValue(IndexedColors.WHITE);
        cmbPattern.setValue(FillPatternType.NO_FILL);
    }

    private void loadColors() {
        Utility utility = new Utility();
        utility.loadColors(itemsColorPoperties);
    }

    private void loadPattern() {
        patternTypesProperties.addAll(FillPatternType.values());
    }
}
