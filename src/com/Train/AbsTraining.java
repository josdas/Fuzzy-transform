package com.Train;

import com.Convector.WordConvector;
import com.Evaluate.Eval;

import java.util.Random;
import java.util.function.UnaryOperator;

/**
 * Created by josdas on 30.06.2017.
 */
public abstract class AbsTraining<T extends WordConvector> {
    protected static final double START_DH = 10;
    protected static final double START_N = 30;
    public static final UnaryOperator<Double> ACTIVE_F_A = (Double a) -> {
        return 1.0 / (1 + Math.exp(-a));
    };
    public static final UnaryOperator<Double> ACTIVE_F_B = (Double a) -> {
        return (Math.exp(a) - Math.exp(-a)) / (Math.exp(a) + Math.exp(-a));
    };

    protected Eval evaluation;
    protected T convector;
    protected Random random;

    protected double dh = START_DH;
    protected double n = START_N;
    protected double result;

    public AbsTraining(Eval evaluation, T convector) {
        this.evaluation = evaluation;
        this.convector = convector;
        random = new Random();
        result = evaluation.eval(convector);
    }

    public abstract void train(int numberIterations);

    public T getConvector() {
        return convector;
    }
}