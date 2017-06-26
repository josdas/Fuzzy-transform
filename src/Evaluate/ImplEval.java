package Evaluate;

import NeuralNetwork.RecurrentNN;

import java.util.ArrayList;

/**
 * Created by Stas on 26.06.2017.
 */
public class ImplEval implements Evaluation {
    ArrayList<String> dictionary;
    ArrayList<String> tempWords;

    public ImplEval(ArrayList<String> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public double eval(RecurrentNN recurrentNN) {
        return 0;
    }
}
