package com.Convector;

import com.NeuralNetwork.Coefficient;
import com.NeuralNetwork.RecurrentNN;

/**
 * Created by josdas on 26.06.2017.
 */
public class WConvector implements WordConvector {
    private static final int ALP = 26;

    private RecurrentNN recurrentNN;

    public WConvector(RecurrentNN recurrentNN) {
        this.recurrentNN = recurrentNN;
    }

    @Override
    public double[] get(String str) {
        double[][] data = new double[str.length()][ALP + 2];
        for (int i = 0; i < str.length(); i++) {
            data[i][((int)str.charAt(i)) - 'a'] = 1.0; // transform char to int with shift
            data[i][ALP] = 1.0; // just constant for good results
            data[i][ALP + 1] = str.length();
        }
        return recurrentNN.get(data);
    }

    public Coefficient getCoefficient() {
        return new Coefficient(recurrentNN.getCoefficient());
    }
}
