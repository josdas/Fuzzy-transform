package com;

import com.Convector.WConvector;
import com.Evaluate.Eval;
import com.NeuralNetwork.ActiveFunction;
import com.NeuralNetwork.Coefficient;
import com.NeuralNetwork.RecurrentNN;

import java.util.Random;

/**
 * Created by josdas on 26.06.2017.
 */
public class Training {
    private static final double START_DH = 10;
    private static final double START_N = 30;
    public static final ActiveFunction ACTIVE_F_A = a -> 1.0 / (1 + Math.exp(-a));
    public static final ActiveFunction ACTIVE_F_B = a -> (Math.exp(a) - Math.exp(-a)) / (Math.exp(a) + Math.exp(-a));

    private Eval evaluation;
    private WConvector convector;
    private Random random;

    private double dh = START_DH;
    private double n = START_N;
    private double result;

    public Training(Eval evaluation, WConvector convector) {
        this.evaluation = evaluation;
        this.convector = convector;
        random = new Random();
        result = evaluation.eval(convector);
    }

    public void train(int numberIterations) { // simple random gradient descent
        for (int iteration = 0; iteration < numberIterations; iteration++) {
            Coefficient coefficient = new Coefficient(
                    convector.getCoefficient() // work with clone of the coefficient
            );
            for (int j = 0; j < n + 1; j++) { // do small change of the array
                int t = random.nextInt(coefficient.summarySize());
                double x = coefficient.get(t);
                x += (random.nextDouble() - 0.5) * dh;
                coefficient.set(t, x);
            }
            RecurrentNN newNN = new RecurrentNN(coefficient, ACTIVE_F_A);
            WConvector nConvector = new WConvector(newNN);
            double nRes = evaluation.eval(nConvector); // calc function of the NN
            if (nRes > result) {
                result = nRes;
                convector = nConvector;
                dh = Math.max(dh * 0.9995, 0.5);
                n = Math.max(n * 0.9995, 1);
            }
        }
        evaluation.generation();
        result = evaluation.eval(convector);
        System.out.println(result + " " + dh);
    }

    public WConvector getConvector() {
        return convector;
    }
}
