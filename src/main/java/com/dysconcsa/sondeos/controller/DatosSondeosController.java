package com.dysconcsa.sondeos.controller;

import com.dysconcsa.sondeos.model.ProfundidadSondeo;
import io.datafx.controller.FXMLController;
import javafx.fxml.FXML;
import javafx.stage.Stage;

@FXMLController("/view/DatosSondeo.fxml")
public class DatosSondeosController {

    private boolean okClicked = false;
    private Stage dialogStage;
    private ProfundidadSondeo profundidadSondeo;

    public void setProfundidadSondeo(ProfundidadSondeo profundidadSondeo) {
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
        if (isInputValid()) {

        }
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
