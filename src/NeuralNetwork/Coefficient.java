package NeuralNetwork;

import java.util.ArrayList;

/**
 * Created by Stas on 26.06.2017.
 */
public class Coefficient {
    ArrayList<Double> data;
    ArrayList<Integer> size;

    public double get(int t) {
        return data.get(t);
    }

    public double get(int x, int y) {
        for (int i = 1; i <= x + 1; i++) {
            y += size.get(i) * size.get(i - 1);
        }
        return data.get(y);
    }

    public void add(double[] temp) {
        size.add(temp.length);
        for (double v : temp) {
            data.add(v);
        }
    }

    public int getNumBlocks() {
        return size.size();
    }

    public double[] getBlock(int ind) {
        double[] result = new double[size.get(ind)];
        int y = 0;
        for (int i = 1; i <= ind + 1; i++) {
            y += size.get(i) * size.get(i - 1);
        }
        for (int i = 0; i < size.get(ind) * size.get(ind + 1); i++) {
            result[i] = data.get(y + i);
        }
        return result;
    }

    public int getSize(int i) {
        return size.get(i);
    }
}
