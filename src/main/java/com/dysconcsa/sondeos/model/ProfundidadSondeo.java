package com.dysconcsa.sondeos.model;

public class ProfundidadSondeo {
    private String sondeoNumero;
    private Double profundidadMinima;
    private Double profundidadMaxima;
    private String lugar;
    private String observaciones;
    private String operador;
    private String archivo;
    private String nivelFreatico;
    private Double elevacion;

    public ProfundidadSondeo() {
    }

    public ProfundidadSondeo(String sondeoNumero, Double profundidadMinima, Double profundidadMaxima, String lugar, String observaciones, String operador, String archivo, String nivelFreatico, Double elevacion) {
        this.sondeoNumero = sondeoNumero;
        this.profundidadMinima = profundidadMinima;
        this.profundidadMaxima = profundidadMaxima;
        this.lugar = lugar;
        this.observaciones = observaciones;
        this.operador = operador;
        this.archivo = archivo;
        this.nivelFreatico = nivelFreatico;
        this.elevacion = elevacion;
    }

    public String getSondeoNumero() {
        return sondeoNumero;
    }

    public void setSondeoNumero(String sondeoNumero) {
        this.sondeoNumero = sondeoNumero;
    }

    public Double getProfundidadMinima() {
        return profundidadMinima;
    }

    public void setProfundidadMinima(Double profundidadMinima) {
        this.profundidadMinima = profundidadMinima;
    }

    public Double getProfundidadMaxima() {
        return profundidadMaxima;
    }

    public void setProfundidadMaxima(Double profundidadMaxima) {
        this.profundidadMaxima = profundidadMaxima;
    }

    public Double getElevacion() {
        return elevacion;
    }

    public void setElevacion(Double elevacion) {
        this.elevacion = elevacion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getNivelFreatico() {
        return nivelFreatico;
    }

    public void setNivelFreatico(String nivelFreatico) {
        this.nivelFreatico = nivelFreatico;
    }
}
