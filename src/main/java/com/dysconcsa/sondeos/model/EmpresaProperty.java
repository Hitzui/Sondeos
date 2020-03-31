package com.dysconcsa.sondeos.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmpresaProperty {
    IntegerProperty id;
    StringProperty cliente;
    StringProperty proyecto;
    StringProperty fecha;

    public EmpresaProperty(String cliente, String proyecto, String fecha) {
        this.cliente = new SimpleStringProperty(cliente);
        this.proyecto = new SimpleStringProperty(proyecto);
        this.fecha = new SimpleStringProperty(fecha);
    }

    public EmpresaProperty(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public String getCliente() {
        return cliente.get();
    }

    public StringProperty clienteProperty() {
        return cliente;
    }

    public String getProyecto() {
        return proyecto.get();
    }

    public StringProperty proyectoProperty() {
        return proyecto;
    }

    public String getFecha() {
        return fecha.get();
    }

    public StringProperty fechaProperty() {
        return fecha;
    }
}
