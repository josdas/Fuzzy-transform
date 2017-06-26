package NeuralNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Stas on 26.06.2017.
 */
public class Coefficient {
    ArrayList<Double> data;
    ArrayList<Integer> size;

    public Coefficient(Integer ... size_) {
        this.size = new ArrayList<Integer>(Arrays.asList(size_));
        data = new ArrayList<>();

        Random random = new Random();

        for (int i = 1; i < size.size(); i++) {
            int n = size.get(i) * size.get(i - 1);
            for (int j = 0; j < n; j++) {
                data.add(random.nextDouble() - 0.5);
            }
        }
    }

    public Coefficient(int startSize) {
        data = new ArrayList<>();
        size = new ArrayList<>();
        size.add(startSize);
    }

    public double get(int t) {
        return data.get(t);
    }

    public void set(int t, double x) {
        data.set(t, x);
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
        for (int i = 1; i < ind + 1; i++) {
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

    public int maxSize() {
        return data.size();
    }
}
