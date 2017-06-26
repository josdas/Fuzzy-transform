package Evaluate;

import Convector.WordConvector;

import java.util.ArrayList;

/**
 * Created by Stas on 26.06.2017.
 */
public class Eval implements Evaluation {
    ArrayList<String> dictionary;
    ArrayList<String> tempWords;

    public Eval(ArrayList<String> dictionary) {
        this.dictionary = dictionary;
    }

    double distance(double[] a, double[] b) {
        assert a.length == b.length;
        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += Math.pow(a[i] - b[i], 2);
        }
        return result;
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
                double temp = distance(points[i], points[j]);
                if (minDis > temp) {
                    minDis = temp;
                }
            }
        }
        return minDis;
    }
}
