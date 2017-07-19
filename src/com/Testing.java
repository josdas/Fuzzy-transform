package com;

import com.Convector.WNNConvector;
import com.HelpClass.Dictionary;
import com.HelpClass.StringDistance;
import com.HelpClass.VectorDistance;
import com.NeuralNetwork.Coefficient;
import com.Train.Option;
import com.Train.TrainingWNN;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.UnaryOperator;

/**
 * Created by josdas on 26.06.2017.
 */

//done применить сверточную идею для хеширования любых строк
//done играть с метриками
//done сделать обучение с levenshtein
//todo геометрический поиск ближайших точек через случайные прямые
//todo словарь + быстрый приближенный поиск (можно делать точный поиск по ближайшим)
//done файлы
//todo стохастическое обучение для случайной пары + градиент

public class Testing {
    public static final UnaryOperator<Double> ACTIVE_F_A = (Double a) -> {
        return 1.0 / (1 + Math.exp(-a));
    };
    public static final UnaryOperator<Double> ACTIVE_F_B = (Double a) -> {
        return (Math.exp(a) - Math.exp(-a)) / (Math.exp(a) + Math.exp(-a));
    };
    private static final int ALP = 3;
    private static final int NEURONS_FOR_LETTER = 10;
    private static final long MAX_TIME = 30;
    private static final int TOP_NUMBER = 10;
    private static final UnaryOperator<Double> ACTIVE = ACTIVE_F_B;

    private static TrainingWNN readTWNNFromFile() {
        System.out.println("Start read from file");
        Coefficient coefficientF = null;
        Coefficient coefficientS = null;
        Coefficient coefficientE = null;
        Option option = new Option(
                ACTIVE,
                ALP,
                NEURONS_FOR_LETTER
        );
        try {
            coefficientF = new Coefficient("First.txt");
            coefficientS = new Coefficient("Second.txt");
            coefficientE = new Coefficient("End.txt");
            System.out.println("Reading from file have been finished");
        } catch (FileNotFoundException e) {
            System.out.println("Fail to read the NN");
            System.exit(1);
        }
        return new TrainingWNN(
                coefficientF,
                coefficientS,
                coefficientE,
                option
        );
    }

    private static TrainingWNN generationRandomTWNN() {
        System.out.println("Start generation random NN");
        Coefficient coefficientF = new Coefficient(
                NEURONS_FOR_LETTER + 1,
                13,
                13,
                NEURONS_FOR_LETTER
        );
        Coefficient coefficientS = new Coefficient(coefficientF);
        Coefficient coefficientE = new Coefficient(
                NEURONS_FOR_LETTER + 1,
                20,
                NEURONS_FOR_LETTER
        );
        Option option = new Option(
                ACTIVE,
                ALP,
                NEURONS_FOR_LETTER
        );
        System.out.println("Generation has been finished");
        return new TrainingWNN(
                coefficientF,
                coefficientS,
                coefficientE,
                option
        );
    }

    private static void train(TrainingWNN trainingWNN, long maxTime) {
        System.out.println("Start training for " + maxTime + " seconds");
        long startTime = System.currentTimeMillis();
        while (true) {
            long timeSpent = (System.currentTimeMillis() - startTime) / 1000;
            System.out.println("Time spent: " + timeSpent + " seconds");
            if (timeSpent > maxTime) {
                break;
            }
            trainingWNN.train(100);
        }
        System.out.println("Training has been finished");
    }

    private static void saveWNNToFile(WNNConvector result) {
        try {
            result.getDeepCoefficients()[0].save("First.txt");
            result.getDeepCoefficients()[1].save("Second.txt");
            result.getDeepCoefficients()[2].save("End.txt");
            System.out.println("SavingWNNToFile has been finished");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("Fail to saveWNNToFile the NN");
        }
    }


    private static void handPairTest(WNNConvector convector) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter two words");
        System.out.println("Enter \"end!\" if you want to finish test");
        while (true) {
            String strA = scan.nextLine();
            if (Objects.equals(strA, "end!")) {
                break;
            }
            String strB = scan.nextLine();
            double[] pointA = convector.get(strA);
            double[] pointB = convector.get(strB);
            System.out.println("Distance in my metric is " + VectorDistance.distance(pointA, pointB));
            System.out.println("Levenshtein distance is " + StringDistance.levenshtein(strA, strB));
        }
    }

    private static void handDictionaryTest(WNNConvector convector, ArrayList<String> dictionary) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter one word");
        System.out.println("Enter \"end!\" if you want to finish test");

        double[][] points = new double[dictionary.size()][];
        for (int i = 0; i < dictionary.size(); i++) {
            points[i] = convector.get(dictionary.get(i));
        }

        while (true) {
            String str = scan.nextLine();
            if (Objects.equals(str, "end!")) {
                break;
            }
            double[] pointA = convector.get(str);
            ArrayList<Pair<Double, Integer>> distances = new ArrayList<>();
            for (int i = 0; i < points.length; i++) {
                distances.add(new Pair<>(
                        VectorDistance.distance(pointA, points[i]),
                        i
                ));
            }
            distances.sort(
                    (a, b) -> {
                        if (a.getKey() < b.getKey()) {
                            return -1;
                        }
                        if (a.getKey().equals(b.getKey())) {
                            return 0;
                        }
                        return 1;
                    }
            );
            for (int i = 0; i < TOP_NUMBER && i < distances.size(); i++) {
                int v = distances.get(i).getValue();
                double dis = distances.get(i).getKey();
                System.out.println(
                        dictionary.get(v) + " "
                                + dis + " "
                                + StringDistance.levenshtein(str, dictionary.get(v))
                );
            }
        }
    }

    private static void dictionaryTest() {
        ArrayList<String> dictionary = null;
        try {
            dictionary = Dictionary.readDictionary(
                    "topwords.txt",
                    2,
                    a -> 'а' <= a && a <= 'я' // letter from 'а' to 'я'
            );
        } catch (FileNotFoundException e) {
            System.out.println("Fail to read a dictionary");
            System.exit(1);
        }

        TrainingWNN trainingWNN = readTWNNFromFile();
        WNNConvector convector = trainingWNN.getConvector();
        convector.setAlp(33);
        convector.setMinLetter('а');
        handDictionaryTest(convector, dictionary);
    }

    private static void trainNNFromFile() {
        TrainingWNN trainingWNN = readTWNNFromFile();
        train(trainingWNN, MAX_TIME);
        WNNConvector result = trainingWNN.getConvector();
        saveWNNToFile(result);
    }

    private static void trainNNFromRand() {
        TrainingWNN trainingWNN = generationRandomTWNN();
        train(trainingWNN, MAX_TIME);
        WNNConvector result = trainingWNN.getConvector();
        saveWNNToFile(result);
    }

    public static void main(String[] args) {
        trainNNFromFile();
    }
}
