package com.Train;

import com.Convector.WNNConvector;
import com.Evaluate.Eval;
import com.NeuralNetwork.Coefficient;
import com.NeuralNetwork.WordNN;

/**
 * Created by josdas on 30.06.2017.
 */
public class TrainingWNNC extends AbsTraining<WNNConvector> {
    public static int ALP = 3;
    public static int NEURONS_FOR_LETTER = 5;

    public TrainingWNNC(Eval evaluation, WNNConvector convector) {
        super(evaluation, convector);
    }

    @Override
    public void train(int numberIterations) {
        for (int iteration = 0; iteration < numberIterations; iteration++) {
            Coefficient[] coefficient = convector.getCoefficient();

            for (Coefficient aCoefficient : coefficient) {
                for (int j = 0; j < n + 1; j++) {
                    int t = random.nextInt(aCoefficient.summarySize());
                    double x = aCoefficient.get(t);
                    x += (random.nextDouble() - 0.5) * dh;
                    aCoefficient.set(t, x);
                }
            }
            WordNN newNN = new WordNN(coefficient[0], coefficient[1], ALP, NEURONS_FOR_LETTER, ACTIVE_F_A);
            WNNConvector nConvector = new WNNConvector(newNN);
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
