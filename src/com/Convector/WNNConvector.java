package com.Convector;

import com.NeuralNetwork.Coefficient;
import com.NeuralNetwork.WordNN;

/**
 * Created by josdas on 29.06.2017.
 */
public class WNNConvector implements WordConvector {
    private WordNN wordNN;

    public WNNConvector(WordNN wordNN) {
        this.wordNN = wordNN;
    }

    @Override
    public double[] get(String str) {
        int[] temp = new int[str.length()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = str.charAt(i) - 'a';
        }
        return wordNN.get(temp);
    }

    public Coefficient[] getCoefficient() {
        return new Coefficient[]{
                new Coefficient(wordNN.getFirstCoef()),
                new Coefficient(wordNN.getSecondCoef())
        };
    }
}
