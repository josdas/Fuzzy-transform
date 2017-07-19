package com.Train;

import com.Convector.WordConvector;

import java.util.Random;

/**
 * Created by josdas on 30.06.2017.
 */
public abstract class AbsTraining<T extends WordConvector> {
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
