package com.dysconcsa.sondeos.controller;

import com.dysconcsa.sondeos.model.ProfundidadSondeo;
import com.dysconcsa.sondeos.util.DateUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.FXMLController;
import javafx.fxml.FXML;
import javafx.stage.Stage;

@FXMLController(value = "/view/DatosSondeo.fxml", title = "Datos del sondeo")
public class DatosSondeosController {

    private boolean okClicked = false;
    private Stage dialogStage;
    private ProfundidadSondeo profundidadSondeo;
    @FXML
    private JFXTextField txtSondeoNumero;

    @FXML
    private JFXTextField txtProfundidadMinima;

    @FXML
    private JFXTextField txtProfundidadMaxima;

    @FXML
    private JFXTextField txtElevacion;

    @FXML
    private JFXTextField txtNivelFreatico;

    @FXML
    private JFXTextField txtLugar;

    @FXML
    private JFXTextField txtObservaciones;

    @FXML
    private JFXTextField txtOperador;

    @FXML
    private JFXTextField txtArchivo;

    @FXML
    private JFXDatePicker txtFecha;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    public void initialize() {

    }

    public void setProfundidadSondeo(ProfundidadSondeo profundidadSondeo) {
        System.out.println("This profundidad sondeo");
        if (profundidadSondeo != null) {
            txtSondeoNumero.setText(profundidadSondeo.getSondeoNumero());
            txtProfundidadMinima.setText(String.valueOf(profundidadSondeo.getProfundidadMinima()));
            txtProfundidadMaxima.setText(String.valueOf(profundidadSondeo.getProfundidadMaxima()));
            txtElevacion.setText(String.valueOf(profundidadSondeo.getElevacion()));
            txtArchivo.setText(profundidadSondeo.getArchivo());
            txtLugar.setText(profundidadSondeo.getLugar());
            txtObservaciones.setText(profundidadSondeo.getObservaciones());
            txtOperador.setText(profundidadSondeo.getOperador());
            txtNivelFreatico.setText(profundidadSondeo.getNivelFreatico());
            txtFecha.setValue(DateUtil.parse(profundidadSondeo.getFecha()));
        }
        this.profundidadSondeo = profundidadSondeo;
    }

    public ProfundidadSondeo getProfundidadSondeo() {
        return profundidadSondeo;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        double profundidadMinima;

        if (txtProfundidadMinima.getText().length() == 0) {
            profundidadMinima = 0d;
        } else {
            profundidadMinima = Double.parseDouble(txtProfundidadMinima.getText());
        }
        double profundidadMaxima;
        if (txtProfundidadMaxima.getText().length() == 0) {
            profundidadMaxima = 0D;
        } else {
            profundidadMaxima = Double.parseDouble(txtProfundidadMaxima.getText());
        }
        double elevacion;
        if (txtElevacion.getText().length() == 0) {
            elevacion = 0d;
        } else {
            elevacion = Double.parseDouble(txtElevacion.getText());
        }
        String fecha = DateUtil.format(txtFecha.getValue());
        this.profundidadSondeo = new ProfundidadSondeo(txtSondeoNumero.getText(),
                profundidadMinima, profundidadMaxima, txtLugar.getText(),
                txtObservaciones.getText(), txtOperador.getText(),
                txtArchivo.getText(), txtNivelFreatico.getText(), elevacion, fecha);
        okClicked = true;
        dialogStage.close();
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        return true;
    }
}
