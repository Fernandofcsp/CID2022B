/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agenteemisor;

/**
 *
 * @author ferna
 */
class Dataset {
    private int columna1;
    private double tv;
    private double radio;
    private double sales;
    private double nPaper;

    Dataset(int columna1, double tv, double radio, double sales, double nPaper) {
        this.columna1 = columna1;
        this.tv = tv;
        this.radio = radio;
        this.sales = sales;
        this.nPaper = nPaper;
    }

    int getColumna1() {
        return columna1;
    }

    double getTv() {
        return tv;
    }

    double getRadio() {
        return radio;
    }

    double getSales() {
        return sales;
    }

    double getNPaper() {
        return nPaper;
    }
}