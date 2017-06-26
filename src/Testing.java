import Convector.WConvector;
import Evaluate.Eval;
import NeuralNetwork.Coefficient;
import NeuralNetwork.RecurrentNN;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Stas on 26.06.2017.
 */
public class Testing {
    final static int MAX_TIME = 10;

    public static void main(String[] args) {
        Random random = new Random();

        ArrayList<String> dictionary = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            int l = random.nextInt(5) + 1;
            for (int j = 0; j < l; j++) {
                stringBuilder.append(
                        (char)(random.nextInt(3) + 'a')
                );
            }
            String str = stringBuilder.toString();
            System.out.println(str);
            dictionary.add(str);
        }
        Eval eval = new Eval(dictionary);
        Coefficient coefficient = new Coefficient(36, 10);
        RecurrentNN recurrentNN = new RecurrentNN(coefficient, Training.ACTIVE_F);
        WConvector wConvector = new WConvector(recurrentNN);
        Training training = new Training(eval, wConvector);

        long startTime = System.currentTimeMillis();
        while (true) {
            long timeSpent = (System.currentTimeMillis() - startTime) / 1000;
            if (timeSpent > MAX_TIME) {
                break;
            }
            training.train(10);
        }

        WConvector result = training.getConvector();
        Scanner scan = new Scanner(System.in);

        while (true) {
            String str = scan.nextLine();
            double[] pointA = result.get(str);
            String best = "";
            double minDis = Double.POSITIVE_INFINITY;
            for (String word : dictionary) {
                double[] pointB = result.get(word);
                double temp = 0;
                for (int j = 0; j < pointA.length; j++) {
                    temp += Math.pow(pointA[j] - pointB[j], 2);
                }
                if (minDis > temp) {
                    minDis = temp;
                    best = word;
                }
            }
            System.out.println(best);
        }
    }
}
