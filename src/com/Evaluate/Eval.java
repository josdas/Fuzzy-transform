package com.Evaluate;

import com.Convector.WordConvector;
import com.Testing;

import java.util.ArrayList;
import java.util.Random;
/**
 * Created by Stas on 26.06.2017.
 */
public class Eval implements Evaluation {
    static final int TEMP_SIZE = 100;

    ArrayList<String> dictionary;
    ArrayList<String> tempWords;
    ArrayList<Integer> number;
    Random random;

    public Eval(ArrayList<String> dictionary) {
        this.dictionary = dictionary;
        this.tempWords = new ArrayList<>();
        this.number = new ArrayList<>();
        random = new Random();
        generation();
    }

    public void generation() {
        tempWords.clear();
        number.clear();
        for (int i = 0; i < TEMP_SIZE; i++) {
            int n = random.nextInt(dictionary.size());
            StringBuilder str = new StringBuilder(dictionary.get(n));

            do {
                int type = random.nextInt(3);
                int t;
                switch (type) {
                    case 0:
                        t = random.nextInt(str.length());
                        str.delete(t, t);
                        break;
                    case 1:
                        t = random.nextInt(str.length() - 1);
                        str.setCharAt(t, (char) ((char) (random.nextInt(Testing.ALP)) + 'a'));
                        break;
                    case 2:
                        t = random.nextInt(str.length() - 1);
                        str.insert(t, (char) ((char) (random.nextInt(Testing.ALP)) + 'a'));
                        break;

                }
            } while (random.nextDouble() > 0.9);
            tempWords.add(str.toString());
            number.add(n);
        }
    }

    @Override
    public double eval(WordConvector wordConvector) {
        double[][] points = new double[dictionary.size()][];
        for (int i = 0; i < dictionary.size(); i++) {
            points[i] = wordConvector.get(dictionary.get(i));
        }

        double minDis = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < i; j++) {
                double temp = VectorN.distance(points[i], points[j]);
                if (minDis > temp) {
                    minDis = temp;
                }
            }
        }
        double errCounter = 0;
        for (int i = 0; i < TEMP_SIZE; i++) {
            int n = number.get(i);
            double[] point = wordConvector.get(tempWords.get(i));
            double l = VectorN.distance(point, points[n]);
            for (int j = 0; j < points.length; j++) {
                if (j != n) {
                    double temp = VectorN.distance(point, points[j]);
                    if (temp < l) {
                        errCounter++;
                    }
                }
            }
        }
        return minDis - errCounter;
    }
}
