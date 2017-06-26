package com.NeuralNetwork;

/**
 * Created by Stas on 26.06.2017.
 */
public class Layer {
    ActiveFunction activeFunction;
    int in, out;
    double[][] wights;

    public Layer(double[] block, ActiveFunction activeFunction, int in, int out) {
        this.activeFunction = activeFunction;
        this.in = in;
        this.out = out;
        wights = new double[out][in];

        for (int i = 0, cur = 0; i < out; i++) {
            for (int j = 0; j < in; j++, cur++) {
                wights[i][j] = block[cur];
            }
        }
    }


    public double[] get_coefficient() {
        double result[] = new double[in * out];
        for (int i = 0, cur = 0; i < out; i++) {
            for (int j = 0; j < in; j++, cur++) {
                result[cur] = wights[i][j];
            }
        }
        return result;
    }

    public double[] get(double[] data) {
        double[] result = new double[out];
        for (int i = 0; i < out; i++) {
            double temp = 0;
            for (int j = 0; j < in; j++) {
                temp += data[j] * wights[i][j];
            }
            result[i] = activeFunction.active(temp);
        }
        return result;
    }

    public int getNumberIn() {
        return in;
    }

    public int getNumberOut() {
        return out;
    }
}
