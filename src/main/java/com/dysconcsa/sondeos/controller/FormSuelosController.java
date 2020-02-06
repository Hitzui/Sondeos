package com.dysconcsa.sondeos.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import com.dysconcsa.sondeos.dao.DaoSuelos;
import com.dysconcsa.sondeos.model.SuelosProperty;
import com.dysconcsa.sondeos.util.AlertError;
import com.dysconcsa.sondeos.util.Utility;

import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


@FXMLController("/view/FormSuelos.fxml")
public class FormSuelosController extends AbstractController implements Initializable {

    int aux;
    String pathImagen = "";
    SuelosProperty sueloEdit;

    @FXML
    private ImageView imagen;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtSimbologia;

    @FXML
    @ActionTrigger("btnOpenImage")
    private Button btnOpenImage;

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
            switch (aux) {
                //nuevo registro
                case 0:
                    if (pathImagen.length() <= 0) {
                        dialog("Guardar", "", "Debe especificar una imagen para poder guardar los datos.");
                        return;
                    }
                    SuelosProperty suelosProperty = new SuelosProperty(0, txtNombre.getText().toUpperCase(), txtSimbologia.getText().toUpperCase(), pathImagen);
                    daoSuelos.save(suelosProperty);
                    if (daoSuelos.get_error() != null) {
                        AlertError.showAlert(daoSuelos.get_error());
                    }
                    dialog("Guardar", "Tipo de suelo", "Se han ingresado los datos de forma correcta.");
                    clear();

                    break;
                //editar datos
                case 1:
                    if (pathImagen.length() <= 0) {
                        pathImagen = sueloEdit.getImagen();
                    }
                    suelosProperty = new SuelosProperty(sueloEdit.getID(), txtNombre.getText(), txtSimbologia.getText(), pathImagen);
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

    @ActionMethod("btnOpenImage")
    public void action_btnOpenImage() {
        Utility utility = new Utility();
        pathImagen = utility.openImage((Stage) btnOpenImage.getScene().getWindow(), imagen);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            sueloEdit = TipoSueloController.suelosProperty;
            //editar datos
            txtSimbologia.setText(sueloEdit.getSimbolo());
            txtNombre.setText(sueloEdit.getNombre());
            if (sueloEdit.getImagen().length() > 0) {
                Image image = new Image(String.valueOf(new File(sueloEdit.getImagen()).toURI()));
                imagen.setImage(image);
            }
            aux = 1;
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
        imagen.setImage(null);
    }
}
