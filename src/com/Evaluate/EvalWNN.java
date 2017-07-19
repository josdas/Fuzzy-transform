package com.Evaluate;

import com.Convector.WordConvector;
import com.HelpClass.StringDistance;
import com.HelpClass.VectorDistance;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by josdas on 26.06.2017.
 */
public class EvalWNN implements Evaluation {
    private static final int TEMP_SIZE = 200;
    private static final int MAX_SIZE = 10;
    private static final int ALP = 3;

    private ArrayList<String> tempWords;
    private Random random;

    public EvalWNN() {
        this.tempWords = new ArrayList<>();
        random = new Random();
        generation();
    }

    public void generation() {
        /*
          create new set of words
         */
        tempWords.clear();
        for (int i = 0; i < TEMP_SIZE; i++) {
            StringBuilder str = new StringBuilder();
            StringBuilder strBug = new StringBuilder();
            int n = random.nextInt(MAX_SIZE) + 1;
            for (int j = 0; j < n; j++) {
                char c = (char) (random.nextInt(ALP) + 'a');
                str.append(c);
                if (random.nextInt(n) > 0) {
                    strBug.append(c);
                }
            }
            if (strBug.toString().length() > 0) {
                tempWords.add(strBug.toString());
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
                double disPoint = VectorDistance.distance(a, b);

                if (disStr > 0) {
                    errCounter += Math.pow(Math.abs(disPoint - disStr) / disStr, 2);
                }
            }
        }
        return -errCounter / tempWords.size() / tempWords.size();
    }
}
