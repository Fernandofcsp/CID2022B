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
    private float rdSpend;
    private float administration;
    private float mSpend;
    private float profit;
    private String state;

    Dataset(float rdSpend, float administration, float mSpend, float profit, String state) {
        this.rdSpend = rdSpend;
        this.administration = administration;
        this.mSpend = mSpend;
        this.profit = profit;
        this.state = state;
    }

    float getrdSpend() {
        return rdSpend;
    }

    float getadministration() {
        return administration;
    }

    float getProfit() {
        return profit;
    }

    float getmSpend() {
        return mSpend;
    }

    String getState() {
        return state;
    }
}
