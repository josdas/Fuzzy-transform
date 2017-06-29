package com.NeuralNetwork;

/**
 * Created by josdas on 26.06.2017.
 */
public interface NeuralNetwork<T> {
    double[] get(T data);

    Coefficient getCoefficient();
}
