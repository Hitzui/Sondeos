package com.dysconcsa.sondeos.model;

import javafx.beans.property.*;
import javafx.scene.paint.Color;
import org.apache.poi.ss.usermodel.IndexedColors;

public class ClasificacionSucsProperty {

    private DoubleProperty profundidad;
    private IntegerProperty limiteLiquido;
    private IntegerProperty indicePlasticidad;
    private IntegerProperty tipoSuelo;
    private StringProperty descripcion;
    private IndexedColors color;

    public ClasificacionSucsProperty(Double profundidad, Integer limiteLiquido, Integer indicePlasticidad, Integer tipoSuelo, String descripcion) {
        this.profundidad = new SimpleDoubleProperty(profundidad);
        this.limiteLiquido = new SimpleIntegerProperty(limiteLiquido);
        this.indicePlasticidad = new SimpleIntegerProperty(indicePlasticidad);
        this.tipoSuelo = new SimpleIntegerProperty(tipoSuelo);
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public double getProfundidad() {
        return profundidad.get();
    }

    public DoubleProperty profundidadProperty() {
        return profundidad;
    }

    public void setProfundidad(double profundidad) {
        this.profundidad.set(profundidad);
    }

    public int getLimiteLiquido() {
        return limiteLiquido.get();
    }

    public IntegerProperty limiteLiquidoProperty() {
        return limiteLiquido;
    }

    public void setLimiteLiquido(int limiteLiquido) {
        this.limiteLiquido.set(limiteLiquido);
    }

    public int getIndicePlasticidad() {
        return indicePlasticidad.get();
    }

    public IntegerProperty indicePlasticidadProperty() {
        return indicePlasticidad;
    }

    public void setIndicePlasticidad(int indicePlasticidad) {
        this.indicePlasticidad.set(indicePlasticidad);
    }

    public int getTipoSuelo() {
        return tipoSuelo.get();
    }

    public IntegerProperty tipoSueloProperty() {
        return tipoSuelo;
    }

    public void setTipoSuelo(int tipoSuelo) {
        this.tipoSuelo.set(tipoSuelo);
    }

    public IndexedColors getColor() {
        return color;
    }

    public void setColor(IndexedColors color) {
        this.color = color;
    }
}
