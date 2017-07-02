package com.Train;

import com.Convector.WordConvector;

import java.util.Random;
import java.util.function.UnaryOperator;

/**
 * Created by josdas on 30.06.2017.
 */
public abstract class AbsTraining<T extends WordConvector> {

    public static final UnaryOperator<Double> ACTIVE_F_A = (Double a) -> {
        return 1.0 / (1 + Math.exp(-a));
    };
    public static final UnaryOperator<Double> ACTIVE_F_B = (Double a) -> {
        return (Math.exp(a) - Math.exp(-a)) / (Math.exp(a) + Math.exp(-a));
    };

    T convector;
    Random random;
    double result;

    public AbsTraining(T convector) {
        this.convector = convector;
        random = new Random();
    }

    public abstract void train(int numberIterations);

    public T getConvector() {
        return convector;
    }
}
