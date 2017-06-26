package NeuralNetwork;

/**
 * Created by Stas on 26.06.2017.
 */
public class RecurrentNN implements NeuralNetwork<Double[]> {
    BasicNN basicNN;

    public RecurrentNN(Coefficient coefficient, ActiveFunction activeFunction) {
        basicNN = new BasicNN(coefficient, activeFunction);
    }

    @Override
    public double[] get(Double[][] data) {
        double[] lastResult = new double[basicNN.getNumberOut()];
        for (Double[] aData : data) {
            Double[] temp = new Double[basicNN.getNumberIn()];
            for (int j = 0; j < aData.length; j++) {
                temp[j] = aData[j];
            }
            for (int j = 0; j < lastResult.length; j++) {
                temp[aData.length + j] = lastResult[j];
            }

            lastResult = basicNN.get(temp);
        }
        return lastResult;
    }

    @Override
    public Coefficient getCoefficient() {
        return basicNN.getCoefficient();
    }
}
