package com.Train;

import com.Convector.WConvector;
import com.Evaluate.Eval;
import com.NeuralNetwork.Coefficient;
import com.NeuralNetwork.RecurrentNN;

/**
 * Created by josdas on 26.06.2017.
 */
public class TrainingWC extends AbsTraining<WConvector> {
    public TrainingWC(Eval evaluation, WConvector convector) {
        super(evaluation, convector);
    }

    public void train(int numberIterations) { // simple random gradient descent
        for (int iteration = 0; iteration < numberIterations; iteration++) {
            Coefficient coefficient = convector.getCoefficient(); // work with clone of the coefficient

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
}
