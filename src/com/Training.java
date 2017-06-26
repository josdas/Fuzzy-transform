package com;

import com.Convector.WConvector;
import com.Evaluate.Evaluation;
import com.NeuralNetwork.ActiveFunction;
import com.NeuralNetwork.Coefficient;
import com.NeuralNetwork.RecurrentNN;

import java.util.Random;

/**
 * Created by Stas on 26.06.2017.
 */
public class Training {
    static final double START_DH = 10;
    static final double START_N = 10;
    public static final ActiveFunction ACTIVE_F = a -> 1.0 / (1 + Math.exp(-a));

    Evaluation evaluation;
    WConvector convector;
    Random random;

    double dh = START_DH;
    double n = START_N;
    double result;

    public Training(Evaluation evaluation, WConvector convector) {
        this.evaluation = evaluation;
        this.convector = convector;
        random = new Random();
        result = evaluation.eval(convector);
    }

    public void train(int numberIterations) {
        for (int iteration = 0; iteration < numberIterations; iteration++) {
            Coefficient coefficient = convector.getCoefficient();
            for (int j = 0; j < n; j++) {
                int t = random.nextInt(coefficient.maxSize());
                double x = coefficient.get(t);
                x += (random.nextDouble() - 0.5) * dh;
                coefficient.set(t, x);
            }
            RecurrentNN newNN = new RecurrentNN(coefficient, ACTIVE_F);
            WConvector nConvector = new WConvector(newNN);
            double nRes = evaluation.eval(nConvector);
            if (nRes > result) {
                result = nRes;
                convector = nConvector;
            }
        }
        System.out.println(result);
    }

    public WConvector getConvector() {
        return convector;
    }
}
