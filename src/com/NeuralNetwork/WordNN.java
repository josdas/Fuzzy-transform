package com.NeuralNetwork;

/**
 * Created by josdas on 29.06.2017.
 */
public class WordNN implements NeuralNetwork<int[]> {
    private BasicNN firstNN;
    private BasicNN secondNN;
    private BasicNN endNN;
    private int alp;
    private int neuronsForLetter;

    public WordNN(Coefficient coefficientF,
                  Coefficient coefficientS,
                  Coefficient coefficientE,
                  Option option) {
        assert coefficientF.getNumberIn(0) == option.neuronsForLetter + 1;
        assert coefficientF.getNumberOut(coefficientF.layersCount() - 1) == option.neuronsForLetter;

        assert coefficientS.getNumberIn(0) == option.neuronsForLetter + 1;
        assert coefficientS.getNumberOut(coefficientS.layersCount() - 1) == option.neuronsForLetter;

        assert coefficientE.getNumberIn(0) == option.neuronsForLetter + 1;
        assert coefficientE.getNumberOut(coefficientE.layersCount() - 1) == option.neuronsForLetter;

        this.firstNN = new BasicNN(coefficientF, option.active);
        this.secondNN = new BasicNN(coefficientS, option.active);
        this.endNN = new BasicNN(coefficientE, x -> x);
        this.alp = option.alp;
        this.neuronsForLetter = option.neuronsForLetter;
    }

    @Override
    public double[] get(int[] data) {
        double[][] temp = new double[alp][neuronsForLetter];
        for (int i = 0; i <= data.length; i++) {
            for (int j = 0; j < alp; j++) {
                double[] block = new double[neuronsForLetter + 1];
                System.arraycopy(temp[j], 0, block, 0, neuronsForLetter);
                block[neuronsForLetter] = 1;
                if (i < data.length) {
                    int letter = data[i];
                    if (j == letter) {
                        temp[j] = firstNN.get(block);
                    } else {
                        temp[j] = secondNN.get(block);
                    }
                }
                else {
                    temp[j] = endNN.get(block);
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

    public Coefficient getEndCoef() {
        return endNN.getCoefficient();
    }

    public void setAlp(int alp) {
        this.alp = alp;
    }

    public int getAlp() {
        return alp;
    }
}
