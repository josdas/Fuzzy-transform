package com.NeuralNetwork;

/**
 * Created by josdas on 29.06.2017.
 */
public class WordNN implements NeuralNetwork<int[]> {
    private BasicNN firstNN;
    private BasicNN secondNN;
    private int alp;
    private int neuronsForLetter;

    public WordNN(Coefficient coefficientF,
                  Coefficient coefficientS,
                  Option option) {
        assert coefficientF.getNumberIn(0) == option.neuronsForLetter + 1;
        assert coefficientF.getNumberOut(coefficientF.layersCount() - 1) == option.neuronsForLetter;
        assert coefficientS.getNumberIn(0) == option.neuronsForLetter + 1;
        assert coefficientS.getNumberOut(coefficientS.layersCount() - 1) == option.neuronsForLetter;

        this.firstNN = new BasicNN(coefficientF, option.active);
        this.secondNN = new BasicNN(coefficientS, option.active);
        this.alp = option.alp;
        this.neuronsForLetter = option.neuronsForLetter;
    }

    @Override
    public double[] get(int[] data) {
        double[][] temp = new double[alp][neuronsForLetter];
        for (int letter : data) {
            for (int j = 0; j < alp; j++) {
                double[] block = new double[neuronsForLetter + 1];
                System.arraycopy(temp[j], 0, block, 0, neuronsForLetter);
                block[neuronsForLetter] = 1;
                if (j == letter) {
                    temp[j] = firstNN.get(block);
                } else {
                    temp[j] = secondNN.get(block);
                }
            }
        }
        double[] result = new double[alp * neuronsForLetter];
        for (int i = 0, cur = 0; i < alp; i++) {
            for (int j = 0; j < neuronsForLetter; j++, cur++) {
                result[cur] = temp[i][j];
            }
        }
        return result;
    }

    public Coefficient getFirstCoef() {
        return firstNN.getCoefficient();
    }

    public Coefficient getSecondCoef() {
        return secondNN.getCoefficient();
    }
}
