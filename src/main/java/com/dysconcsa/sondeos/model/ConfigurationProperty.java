package com.dysconcsa.sondeos.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConfigurationProperty {

    private IntegerProperty id;
    private StringProperty imagen;
    private StringProperty nombreEmpresa;

    public ConfigurationProperty(Integer id, String nombreEmpresa, String imagen) {
        this.id = new SimpleIntegerProperty(id);
        this.imagen = new SimpleStringProperty(imagen);
        this.nombreEmpresa = new SimpleStringProperty(nombreEmpresa);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getImagen() {
        return imagen.get();
    }

    public StringProperty imagenProperty() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen.set(imagen);
    }

    public String getNombreEmpresa() {
        return nombreEmpresa.get();
    }

    public StringProperty nombreEmpresaProperty() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa.set(nombreEmpresa);
    }

    @Override
    public String toString() {
        return nombreEmpresa.get();
    }
}
