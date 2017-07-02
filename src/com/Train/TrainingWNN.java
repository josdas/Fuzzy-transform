package com.Train;

import com.Convector.WNNConvector;
import com.Evaluate.EvalWNN;
import com.NeuralNetwork.Coefficient;
import com.NeuralNetwork.Option;
import com.NeuralNetwork.WordNN;

/**
 * Created by josdas on 30.06.2017.
 */
public class TrainingWNN extends AbsTraining<WNNConvector> {
    private Option option;
    private EvalWNN evaluation;

    public TrainingWNN(Coefficient startCoefF,
                       Coefficient startCoefS,
                       Option option) {
        super(
                new WNNConvector(
                        startCoefF,
                        startCoefS,
                        option
                )
        );
        this.option = option;
        this.evaluation = new EvalWNN();
    }

    @Override
    public void train(int numberIterations) {
        // simple random gradient descent

        for (int iteration = 0; iteration < numberIterations; iteration++) {
            Coefficient[] coefficient = convector.getCoefficient(); // work with clone of the coefficients

            for (Coefficient aCoefficient : coefficient) { // do small change of the arrays
                int m = random.nextInt((int) n + 1) + 1;
                for (int j = 0; j < m; j++) {
                    int t = random.nextInt(aCoefficient.summarySize());
                    double x = aCoefficient.get(t);
                    x += (random.nextDouble() - 0.5) * (dh + 0.1);
                    aCoefficient.set(t, x);
                }
            }
            WordNN newNN = new WordNN(coefficient[0], coefficient[1], option);
            WNNConvector nConvector = new WNNConvector(newNN);
            double nRes = evaluation.eval(nConvector); // calc function of the NN
            if (nRes > result) {
                result = nRes;
                convector = nConvector;
                dh *= 0.9999;
                n *= 0.9999;
            }
        }
        //evaluation.generation();
        result = evaluation.eval(convector);
        System.out.println(result + " " + dh);
    }
}
