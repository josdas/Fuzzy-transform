package com.NeuralNetwork;

import java.util.Random;

/**
 * Created by josdas on 26.06.2017.
 */
public class Coefficient {
    private double data[][][];
    private int size[];

    private int indexStorage[][];

    public Coefficient(int... size) {
        // create |size| - 1 layers.
        // The size of the first layer is size[0].
        // All layers have random wights.

        this.size = size;
        data = new double[size.length - 1][][];
        int summarySize = 0;
        for (int i = 1; i < size.length; i++) {
            final int in = size[i - 1];
            final int out = size[i];
            summarySize += in * out;
        }
        indexStorage = new int[summarySize][];

        Random random = new Random();

        for (int i = 1, cur = 0; i < size.length; i++) {
            final int in = size[i - 1];
            final int out = size[i];
            data[i - 1] = new double[out][in];
            for (int j = 0; j < out; j++) {
                for (int k = 0; k < in; k++, cur++) {
                    indexStorage[cur] = new int[]{i - 1, j, k};
                    data[i - 1][j][k] = random.nextDouble() - 0.5;
                }
            }
        }
    }

    public Coefficient(Coefficient coefficient) {
        this.data = coefficient.data.clone();
        this.size = coefficient.size.clone();
        this.indexStorage = coefficient.indexStorage.clone();
    }

    public double get(int t) {
        return get(indexStorage[t][0], indexStorage[t][1], indexStorage[t][2]);
    }

    public void set(int t, double x) {
        set(indexStorage[t][0], indexStorage[t][1], indexStorage[t][2], x);
    }

    public double get(int x, int y, int z) {
        return data[x][y][z];
    }

    public void set(int x, int y, int z, double v) {
        data[x][y][z] = v;
    }

    public int getNumberIn(int layer) {
        return size[layer];
    }

    public int getNumberOut(int layer) {
        return size[layer + 1];
    }

    public int layersCount() {
        return size.length - 1;
    }

    public int getNumBlocks() {
        return size.length;
    }

    public double[][] getBlock(int ind) {
        return data[ind];
    }

    public int getSize(int i) {
        return size[i];
    }

    public int summarySize() {
        return indexStorage.length;
    }
}
