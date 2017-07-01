package com.NeuralNetwork;

import java.util.function.UnaryOperator;

/**
 * Created by josdas on 26.06.2017.
 */
public class RecurrentNN implements NeuralNetwork<double[][]> {
    private BasicNN basicNN;

    public RecurrentNN(Coefficient coefficient, UnaryOperator<Double> activeFunction) {
        basicNN = new BasicNN(coefficient, activeFunction);
    }

    @Override
    public double[] get(double[][] data) {
        double[] lastResult = new double[basicNN.getNumberOut()];
        for (double[] aData : data) {
            double[] temp = new double[basicNN.getNumberIn()];
            System.arraycopy(aData, 0, temp, 0, aData.length);
            System.arraycopy(lastResult, 0, temp, aData.length, lastResult.length);

            lastResult = basicNN.get(temp);
        }
        return lastResult;
    }

    public Coefficient getCoefficient() {
        return basicNN.getCoefficient();
    }
}
