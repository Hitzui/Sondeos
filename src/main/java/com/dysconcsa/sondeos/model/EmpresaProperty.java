package com.dysconcsa.sondeos.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmpresaProperty {
    IntegerProperty id;
    StringProperty cliente;
    StringProperty proyecto;
    StringProperty sondeoN;
    StringProperty lugar;
    StringProperty operador;
    StringProperty nivel;
    StringProperty observaciones;
    StringProperty archivo;
    StringProperty fecha;

    public EmpresaProperty(String cliente, String proyecto, String sondeoN, String lugar, String operador, String nivel, String observaciones, String archivo, String fecha) {
        this.cliente = new SimpleStringProperty(cliente);
        this.proyecto = new SimpleStringProperty(proyecto);
        this.sondeoN = new SimpleStringProperty(sondeoN);
        this.lugar = new SimpleStringProperty(lugar);
        this.operador = new SimpleStringProperty(operador);
        this.nivel = new SimpleStringProperty(nivel);
        this.observaciones = new SimpleStringProperty(observaciones);
        this.archivo = new SimpleStringProperty(archivo);
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

    public String getSondeoN() {
        return sondeoN.get();
    }

    public StringProperty sondeoNProperty() {
        return sondeoN;
    }

    public void setSondeoN(String sondeoN) {
        this.sondeoN.set(sondeoN);
    }

    public String getLugar() {
        return lugar.get();
    }

    public StringProperty lugarProperty() {
        return lugar;
    }

    public String getOperador() {
        return operador.get();
    }

    public StringProperty operadorProperty() {
        return operador;
    }

    public String getNivel() {
        return nivel.get();
    }

    public StringProperty nivelProperty() {
        return nivel;
    }

    public String getObservaciones() {
        return observaciones.get();
    }

    public StringProperty observacionesProperty() {
        return observaciones;
    }

    public String getArchivo() {
        return archivo.get();
    }

    public StringProperty archivoProperty() {
        return archivo;
    }

    public String getFecha() {
        return fecha.get();
    }

    public StringProperty fechaProperty() {
        return fecha;
    }
}
