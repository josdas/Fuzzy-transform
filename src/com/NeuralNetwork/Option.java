package com.NeuralNetwork;

import java.util.function.UnaryOperator;

/**
 * Created by josdas on 02.07.2017.
 */
public class Option {
    public UnaryOperator<Double> active;
    public int alp;
    public int neuronsForLetter;

    public Option(UnaryOperator<Double> active,
                  int alp,
                  int neuronsForLetter) {
        this.active = active;
        this.alp = alp;
        this.neuronsForLetter = neuronsForLetter;
    }
}
