package com.Evaluate;

/**
 * Created by josdas on 27.06.2017.
 */
public class VectorN {
    public static double distance(double[] a, double[] b) {
        assert a.length == b.length;
        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += Math.pow(a[i] - b[i], 2);
        }
        return result;
    }
}
