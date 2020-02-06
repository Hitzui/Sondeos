/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dysconcsa.sondeos.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

/**
 * @author server
 */
public class SuelosProperty {
    private IntegerProperty ID;
    private StringProperty nombre;
    private StringProperty simbolo;
    private StringProperty imagen;

    /**
     * tipo de suelos en la base de datos, se guarda la imagne como texto
     *
     * @param ID     Llave primaria
     * @param name   Nombre del tipo de suelo
     * @param symbol Simbologia a usar
     * @param image  Ruta de la imgaen
     * @return SuelosProperty
     */
    public SuelosProperty(Integer ID, String name, String symbol, String image) {
        this.ID = new SimpleIntegerProperty(ID);
        this.nombre = new SimpleStringProperty(name);
        this.simbolo = new SimpleStringProperty(symbol);
        this.imagen = new SimpleStringProperty(image);
    }

    public int getID() {
        return ID.get();
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getSimbolo() {
        return simbolo.get();
    }

    public StringProperty simboloProperty() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo.set(simbolo);
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

    @Override
    public String toString() {
        return simbolo.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuelosProperty that = (SuelosProperty) o;
        return Objects.equals(ID, that.ID) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(simbolo, that.simbolo) &&
                Objects.equals(imagen, that.imagen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, nombre, simbolo, imagen);
    }
}
