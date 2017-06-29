package com.NeuralNetwork;

/**
 * Created by Stas on 26.06.2017.
 */
public class BasicNN implements NeuralNetwork<double[]> {
    private Coefficient coefficient;
    private ActiveFunction active;

    public BasicNN(Coefficient coefficient, ActiveFunction active) {
        this.coefficient = coefficient;
        this.active = active;
    }

    public double[] get(double[] data, int number) {
        final int in = coefficient.getNumberIn(number);
        final int out = coefficient.getNumberOut(number);
        double[] result = new double[out];
        for (int i = 0; i < out; i++) {
            double temp = 0;
            for (int j = 0; j < in; j++) {
                temp += data[j] * coefficient.get(number, i, j);
            }
            result[i] = active.active(temp);
        }
        return result;
    }

    @Override
    public double[] get(double[] data) {
        double result[] = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[i];
        }
        for (int i = 0; i < coefficient.layersCount(); i++) {
            result = get(result, i);
        }
        return result;
    }

    @Override
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
