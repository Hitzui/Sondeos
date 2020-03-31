package com.dysconcsa.sondeos.controller;

import com.dysconcsa.sondeos.dao.DaoEmpresa;
import com.dysconcsa.sondeos.model.EmpresaProperty;
import com.dysconcsa.sondeos.util.AlertError;
import com.dysconcsa.sondeos.util.DateUtil;
import com.dysconcsa.sondeos.util.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import javax.rmi.CORBA.Util;
import java.time.LocalDate;

@FXMLController("/view/FormCliente.fxml")
public class FormClienteController {

    private static final String _guardar = "Guardar";
    private int aux = 0;
    private EmpresaProperty empresaProperty;
    private DaoEmpresa daoEmpresa;
    private Utility utility;
    private Integer idEmpresa = 0;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXTextField txtCliente;

    @FXML
    private JFXTextArea txtProyecto;


    @FXML
    private JFXDatePicker txtFecha;

    @FXML
    @LinkAction(ClienteController.class)
    private JFXButton btnCancelar;

    @FXML
    @ActionTrigger("actionGuardarCliente")
    private JFXButton btnAceptar;

    @FXML
    public void initialize() {
        if (ClienteController.empresaProperty != null) {
            this.empresaProperty = ClienteController.empresaProperty;
            loadValues(this.empresaProperty);
            idEmpresa = this.empresaProperty.getId();
            aux = 1;
        } else {
            aux = 0;
        }
        txtCliente.requestFocus();
        daoEmpresa = new DaoEmpresa();
        utility = new Utility();
        txtFecha.setValue(LocalDate.now());
        utility.keyPressed(txtCliente, txtProyecto);
    }

    @ActionMethod("actionGuardarCliente")
    public void action_guardarCliente() {
        if (utility.resultDialog(_guardar, "¿Seguro desea guardar los datos del cliente?").get() == ButtonType.CANCEL) {
            return;
        }
        String fecha = DateUtil.format(txtFecha.getValue());
        if (validateText(txtCliente)) return;
        if (validateText(txtProyecto)) return;
        if (fecha.length() <= 0) {
            txtFecha.requestFocus();
            dialog("Fecha", "Debe especificar una fecha del proyecto para poder continuar");
            return;
        }
        empresaProperty = new EmpresaProperty(txtCliente.getText(), txtProyecto.getText(), DateUtil.format(txtFecha.getValue()));
        switch (aux) {
            case 0:
                //guardar datos
                daoEmpresa.save(empresaProperty);
                if (daoEmpresa.get_error() != null) {
                    AlertError.showAlert(daoEmpresa.get_error());
                } else {
                    dialog("", "Se han guardado los datos de forma correcta");
                    clear();
                }
                break;
            case 1:
                //editar datos
                empresaProperty.setId(idEmpresa);
                daoEmpresa.update(empresaProperty);
                if (daoEmpresa.get_error() != null) {
                    AlertError.showAlert(daoEmpresa.get_error());
                } else {
                    dialog("", "Se han guardado los datos de forma correcta");
                    clear();
                }
                break;
        }
    }

    private boolean validateText(JFXTextField textField) {
        if (textField.getText().length() <= 0) {
            textField.requestFocus();
            return true;
        } else {
            return false;
        }
    }

    private boolean validateText(JFXTextArea textField) {
        if (textField.getText().length() <= 0) {
            textField.requestFocus();
            return true;
        } else {
            return false;
        }
    }

    private void dialog(String header, String content) {
        Utility.dialog(FormClienteController._guardar, header, content);
    }

    private void loadValues(EmpresaProperty empresaProperty) {
        txtCliente.setText(empresaProperty.getCliente());
        txtProyecto.setText(empresaProperty.getProyecto());
        txtFecha.setValue(DateUtil.parse(empresaProperty.getFecha()));
    }

    private void clear() {
        txtCliente.clear();
        txtProyecto.clear();
    }
}
