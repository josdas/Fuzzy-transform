package NeuralNetwork;

/**
 * Created by Stas on 26.06.2017.
 */
public interface NeuralNetwork<T> {
    double[] get(T[] data);

    Coefficient getCoefficient();
}
