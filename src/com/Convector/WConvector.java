package com.Convector;

import com.NeuralNetwork.Coefficient;
import com.NeuralNetwork.RecurrentNN;

/**
 * Created by Stas on 26.06.2017.
 */
public class WConvector implements WordConvector {
    static final int ALP = 26;

    RecurrentNN recurrentNN;

    public WConvector(RecurrentNN recurrentNN) {
        this.recurrentNN = recurrentNN;
    }

    @Override
    public double[] get(String str) {
        double[][] data = new double[str.length()][ALP + 2];
        for (int i = 0; i < str.length(); i++) {
            data[i][((int)str.charAt(i)) - 'a'] = 1.0;
            data[i][ALP] = 1.0;
            data[i][ALP + 1] = str.length() / 10.0;
        }
        return recurrentNN.get(data);
    }

    public Coefficient getCoefficient() {
        return recurrentNN.getCoefficient();
    }
}
