package com.Train;

import com.Convector.WNNConvector;
import com.Evaluate.EvalWNN;
import com.NeuralNetwork.Coefficient;
import com.NeuralNetwork.WordNN;

/**
 * Created by josdas on 30.06.2017.
 */
public class TrainingWNNC extends AbsTraining<WNNConvector> {
    public final static int ALP = 3;
    public final static int NEURONS_FOR_LETTER = 10;

    private EvalWNN evaluation;

    public TrainingWNNC(EvalWNN evaluation, WNNConvector convector) {
        super(convector);
        this.evaluation = evaluation;
    }

    @Override
    public void train(int numberIterations) {
        for (int iteration = 0; iteration < numberIterations; iteration++) {
            Coefficient[] coefficient = convector.getCoefficient();

            for (Coefficient aCoefficient : coefficient) {
                int m = random.nextInt((int) n) + 1;
                for (int j = 0; j < m; j++) {
                    int t = random.nextInt(aCoefficient.summarySize());
                    double x = aCoefficient.get(t);
                    x += (random.nextDouble() - 0.5) * (dh + 0.1);
                    aCoefficient.set(t, x);
                }
            }
            WordNN newNN = new WordNN(coefficient[0], coefficient[1], ALP, NEURONS_FOR_LETTER, ACTIVE_F_A);
            WNNConvector nConvector = new WNNConvector(newNN);
            double nRes = evaluation.eval(nConvector);
            if (nRes > result) {
                result = nRes;
                convector = nConvector;
                dh *= 0.999;
                n *= 0.999;
            }
        }
        //evaluation.generation();
        result = evaluation.eval(convector);
        System.out.println(result + " " + dh);
    }
}
