package com.Train;

import com.Convector.WordConvector;

import java.util.Random;
import java.util.function.UnaryOperator;

/**
 * Created by josdas on 30.06.2017.
 */
public abstract class AbsTraining<T extends WordConvector> {
    private static final double START_DH = 5;
    private static final double START_N = 7;

    public static final UnaryOperator<Double> ACTIVE_F_A = (Double a) -> {
        return 1.0 / (1 + Math.exp(-a));
    };
    public static final UnaryOperator<Double> ACTIVE_F_B = (Double a) -> {
        return (Math.exp(a) - Math.exp(-a)) / (Math.exp(a) + Math.exp(-a));
    };

    protected T convector;
    protected Random random;

    protected double dh = START_DH;
    protected double n = START_N;
    protected double result;

    public AbsTraining(T convector) {
        this.convector = convector;
        random = new Random();
    }

    public abstract void train(int numberIterations);

    public T getConvector() {
        return convector;
    }
}
