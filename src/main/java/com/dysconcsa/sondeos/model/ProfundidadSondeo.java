package com.dysconcsa.sondeos.model;

public class ProfundidadSondeo {
    private Double profundidadMinima;
    private Double profundidadMaxima;
    private Double elevacion;

    public ProfundidadSondeo() {
    }

    public ProfundidadSondeo(Double profundidadMinima, Double profundidadMaxima, Double elevacion) {
        this.profundidadMinima = profundidadMinima;
        this.profundidadMaxima = profundidadMaxima;
        this.elevacion = elevacion;
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
}
