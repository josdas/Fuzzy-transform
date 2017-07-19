package com.Convector;

import com.NeuralNetwork.Coefficient;
import com.Train.Option;
import com.NeuralNetwork.WordNN;

/**
 * Created by josdas on 29.06.2017.
 */
public class WNNConvector implements WordConvector {
    private WordNN wordNN;
    private char minLetter;

    public WNNConvector(WordNN wordNN) {
        this.wordNN = wordNN;
    }

    public WNNConvector(Coefficient coefficientF,
                        Coefficient coefficientS,
                        Coefficient coefficientE,
                        Option option,
                        char minLetter) {
        this.wordNN = new WordNN(coefficientF, coefficientS, coefficientE, option);
        this.minLetter = minLetter;
    }

    @Override
    public double[] get(String str) {
        int[] temp = new int[str.length()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = str.charAt(i) - minLetter;
        }
        return wordNN.get(temp);
    }

    public Coefficient[] getCoefficient() {
        return new Coefficient[]{
                new Coefficient(wordNN.getFirstCoef()),
                new Coefficient(wordNN.getSecondCoef()),
                new Coefficient(wordNN.getEndCoef())
        };
    }

    public WNNConvector setAlp(int alp) {
        wordNN.setAlp(alp);
        return this;
    }

    public int getAlp() {
        return wordNN.getAlp();
    }

    public WNNConvector setMinLetter(char minLetter) {
        this.minLetter = minLetter;
        return this;
    }

}
