package com.NeuralNetwork;

/**
 * Created by Stas on 26.06.2017.
 */
public class BasicNN implements NeuralNetwork<double[]> {
    Layer[] layers;

    public BasicNN(Coefficient coef, ActiveFunction activeFunction) {
        layers = new Layer[coef.getNumBlocks() - 1];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new Layer(
                    coef.getBlock(i),
                    activeFunction,
                    coef.getSize(i),
                    coef.getSize(i + 1)
            );
        }
    }

    @Override
    public double[] get(double[] data) {
        double result[] = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[i];
        }
        for (Layer layer : layers) {
            result = layer.get(result);
        }
        return result;
    }

    @Override
    public Coefficient getCoefficient() {
        Coefficient result = new Coefficient(layers[0].getNumberIn());
        for (Layer layer : layers) {
            double[] temp = layer.get_coefficient();
            result.add(temp, layer.getNumberOut());
        }
        return result;
    }

    public int getNumberOut() {
        return layers[layers.length - 1].getNumberOut();
    }

    public int getNumberIn() {
        return layers[0].getNumberIn();
    }
}