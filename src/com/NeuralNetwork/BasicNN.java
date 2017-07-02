package com.NeuralNetwork;

import java.util.function.UnaryOperator;

/**
 * Created by josdas on 26.06.2017.
 */
public class BasicNN implements NeuralNetwork<double[]> {
    private Coefficient coefficient;
    private UnaryOperator<Double> active; // all layers have the same function except the last
    private UnaryOperator<Double> activeLast;

    public BasicNN(Coefficient coefficient, UnaryOperator<Double> active) {
        this.coefficient = coefficient;
        this.active = active;
        this.activeLast = active;
    }

    public BasicNN(Coefficient coefficient, UnaryOperator<Double> active, UnaryOperator<Double> activeLast) {
        this.coefficient = coefficient;
        this.active = active;
        this.activeLast = activeLast;
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
                result[i] = activeLast.apply(temp);
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
