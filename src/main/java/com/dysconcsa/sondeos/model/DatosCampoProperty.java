package com.dysconcsa.sondeos.model;

import javafx.beans.property.*;

public class DatosCampoProperty {

    private StringProperty sondeoNumero;
    private DoubleProperty profundidadInicial;
    private DoubleProperty profundidadFinal;
    private IntegerProperty recobro;
    private IntegerProperty golpe1;
    private IntegerProperty golpe2;
    private IntegerProperty golpe3;

    public DatosCampoProperty() {
        this.sondeoNumero = new SimpleStringProperty("");
        this.profundidadInicial = new SimpleDoubleProperty(0.0);
        this.profundidadFinal = new SimpleDoubleProperty(1.5);
        this.recobro = new SimpleIntegerProperty(0);
        this.golpe1 = new SimpleIntegerProperty(0);
        this.golpe2 = new SimpleIntegerProperty(0);
        this.golpe3 = new SimpleIntegerProperty(0);
    }

    public DatosCampoProperty(Double profundidadInicial, Double profundidadFinal,
                              Integer recobro, Integer golpe1,
                              Integer golpe2, Integer golpe3) {
        this.profundidadInicial = new SimpleDoubleProperty(profundidadInicial);
        this.profundidadFinal = new SimpleDoubleProperty(profundidadFinal);
        this.recobro = new SimpleIntegerProperty(recobro);
        this.golpe1 = new SimpleIntegerProperty(golpe1);
        this.golpe2 = new SimpleIntegerProperty(golpe2);
        this.golpe3 = new SimpleIntegerProperty(golpe3);
    }

    public String getSondeoNumero() {
        return sondeoNumero.get();
    }

    public StringProperty sondeoNumeroProperty() {
        return sondeoNumero;
    }

    public void setSondeoNumero(String sondeoNumero) {
        this.sondeoNumero.set(sondeoNumero);
    }

    public double getProfundidadInicial() {
        return profundidadInicial.get();
    }

    public DoubleProperty profundidadInicialProperty() {
        return profundidadInicial;
    }

    public void setProfundidadInicial(double profundidadInicial) {
        this.profundidadInicial.set(profundidadInicial);
    }

    public double getProfundidadFinal() {
        return profundidadFinal.get();
    }

    public DoubleProperty profundidadFinalProperty() {
        return profundidadFinal;
    }

    public void setProfundidadFinal(double profundidadFinal) {
        this.profundidadFinal.set(profundidadFinal);
    }

    public int getRecobro() {
        return recobro.get();
    }

    public IntegerProperty recobroProperty() {
        return recobro;
    }

    public void setRecobro(int recobro) {
        this.recobro.set(recobro);
    }

    public int getGolpe1() {
        return golpe1.get();
    }

    public IntegerProperty golpe1Property() {
        return golpe1;
    }

    public void setGolpe1(int golpe1) {
        this.golpe1.set(golpe1);
    }

    public int getGolpe2() {
        return golpe2.get();
    }

    public IntegerProperty golpe2Property() {
        return golpe2;
    }

    public void setGolpe2(int golpe2) {
        this.golpe2.set(golpe2);
    }

    public int getGolpe3() {
        return golpe3.get();
    }

    public IntegerProperty golpe3Property() {
        return golpe3;
    }

    public void setGolpe3(int golpe3) {
        this.golpe3.set(golpe3);
    }

    @Override
    public String toString() {
        return "DatosCampoProperty{" +
                "profundidadInicial=" + profundidadInicial +
                ", profundidadFinal=" + profundidadFinal +
                ", recobro=" + recobro +
                '}';
    }
}
