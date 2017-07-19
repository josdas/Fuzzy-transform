package com.Train;

import com.Convector.WNNConvector;
import com.Evaluate.EvalWNN;
import com.NeuralNetwork.Coefficient;
import com.NeuralNetwork.WordNN;

/**
 * Created by josdas on 30.06.2017.
 */
public class TrainingWNN extends AbsTraining<WNNConvector> {
    private static final double START_DH = 5;
    private static final double START_N = 5;
    private static final double DH_DECREASE_K = 0.999;
    private static final double N_DECREASE_K = 0.999;
    private static final double DH_MIN = 0.1;
    private static final double N_MIN = 1;

    private double dh = START_DH;
    private double n = START_N;

    private Option option;
    private EvalWNN evaluation;

    public TrainingWNN(Coefficient startCoefF,
                       Coefficient startCoefS,
                       Coefficient startCoefE,
                       Option option) {
        super(
                new WNNConvector( //todo remove
                        startCoefF,
                        startCoefS,
                        startCoefE,
                        option,
                        'a' //todo WTF
                )
        );
        this.option = option;
        this.evaluation = new EvalWNN();
    }

    @Override
    public void train(int numberIterations) {
        /* simple random gradient descent */

        evaluation.generation();
        result = evaluation.eval(convector);
        for (int iteration = 0; iteration < numberIterations; iteration++) {
            // work with clone of the coefficients
            Coefficient[] coefficients = convector.getDeepCoefficients();

            // do small change of the arrays
            for (Coefficient coefficient : coefficients) {
                int m = random.nextInt((int) (n + N_MIN));
                for (int j = 0; j < m; j++) {
                    int t = random.nextInt(coefficient.summarySize());
                    double x = coefficient.get(t);
                    x += (random.nextDouble() - 0.5) * (dh + DH_MIN);
                    coefficient.set(t, x);
                }
            }
            WordNN newNN = new WordNN(coefficients[0], coefficients[1], coefficients[2], option);
            WNNConvector nConvector = new WNNConvector(newNN);

            // calc function of the NN
            double nRes = evaluation.eval(nConvector);
            if (nRes > result) {
                result = nRes;
                convector = nConvector;
                dh *= DH_DECREASE_K;
                n *= N_DECREASE_K;
            }
        }
        System.out.println(result + " " + dh);
    }
}
