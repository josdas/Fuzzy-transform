package Convector;

import NeuralNetwork.RecurrentNN;

/**
 * Created by Stas on 26.06.2017.
 */
public class WConvector implements WordConvector {
    static final int ALP = 26;

    RecurrentNN recurrentNN;

    public WConvector(RecurrentNN recurrentNN) {
        this.recurrentNN = recurrentNN;
    }

    @Override
    public double[] get(String str) {
        Double[][] data = new Double[str.length()][ALP];
        for (int i = 0; i < str.length(); i++) {
            data[i][((int)str.charAt(i)) - 'a'] = 1.0;
        }
        return recurrentNN.get(data);
    }
}
