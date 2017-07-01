package com.Evaluate;

import com.Convector.WordConvector;
import com.Train.StringDistance;
import com.VectorN;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by josdas on 26.06.2017.
 */
public class EvalWNN implements Evaluation {
    private static final int TEMP_SIZE = 100;
    private static final int MAX_SIZE = 7;
    private static final int ALP = 3;

    private ArrayList<String> tempWords;
    private Random random;

    public EvalWNN() {
        this.tempWords = new ArrayList<>();
        random = new Random();
        generation();
    }

    public void generation() { // create new set of changed words from the dictionary
        tempWords.clear();
        for (int i = 0; i < TEMP_SIZE; i++) {
            StringBuilder str = new StringBuilder();
            int n = random.nextInt(MAX_SIZE) + 1;
            for (int j = 0; j < n; j++) {
                str.append((char)(random.nextInt(ALP) + 'a'));
            }
            tempWords.add(str.toString());
        }
    }

    @Override
    public double eval(WordConvector wordConvector) {
        double[][] points = new double[tempWords.size()][];
        for (int i = 0; i < tempWords.size(); i++) {
            points[i] = wordConvector.get(tempWords.get(i));
        }

        double errCounter = 0;
        for (int i = 0; i < tempWords.size(); i++) {
            for (int j = 0; j < tempWords.size(); j++) {
                double[] a = points[i];
                double[] b = points[j];
                double disStr = StringDistance.levenshtein(tempWords.get(i), tempWords.get(j));
                double disPoint = VectorN.distance(a, b);

                errCounter += Math.pow(Math.abs(disPoint - disStr), 2);
            }
        }
        return -errCounter / tempWords.size() / tempWords.size();
    }
}
