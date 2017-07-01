package com.NeuralNetwork;

import java.util.function.UnaryOperator;

/**
 * Created by josdas on 26.06.2017.
 */
public class BasicNN implements NeuralNetwork<double[]> {
    // the last layer has linear function of activation

    private Coefficient coefficient;
    private UnaryOperator<Double> active; // all layers have the same function except the last

    public BasicNN(Coefficient coefficient, UnaryOperator<Double> active) {
        this.coefficient = coefficient;
        this.active = active;
    }

    private double[] get(double[] data, int number) {
        // get result after the layer with number "number"

        final int in = coefficient.getNumberIn(number);
        final int out = coefficient.getNumberOut(number);

        assert in == data.length;
        double[] result = new double[out];
        for (int i = 0; i < out; i++) {
            double temp = 0;
            for (int j = 0; j < in; j++) {
                temp += data[j] * coefficient.get(number, i, j);
            }

            // chosen function depends on number
            if (number < coefficient.layersCount()) {
                result[i] = active.apply(temp);
            } else {
                result[i] = temp;
            }
        }
        return result;
    }

    @Override
    public double[] get(double[] data) {
        for (int i = 0; i < coefficient.layersCount(); i++) {
            data = get(data, i);
        }
        return data;
    }

    public Coefficient getCoefficient() {
        return coefficient;
    }

    public int getNumberOut() {
        return coefficient.getNumberOut(coefficient.layersCount() - 1);
    }

    public int getNumberIn() {
        return coefficient.getNumberIn(0);
    }
}
