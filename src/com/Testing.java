package com;

import com.Convector.WNNConvector;
import com.NeuralNetwork.Coefficient;
import com.NeuralNetwork.Option;
import com.Train.AbsTraining;
import com.Train.TrainingWNN;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by josdas on 26.06.2017.
 */

//done применить сверточную идею для хеширования любых строк
//done играть с метриками
//done сделать обучение с levenshtein
//todo геометрический поиск ближайших точек через случайные прямые
//todo словарь + быстрый приближенный поиск (можно делать точный поиск по ближайшим)
//done файлы

public class Testing {
    private static final int ALP = 3;
    private static final int NEURONS_FOR_LETTER = 10;
    private static final long MAX_TIME = 60 * 10;
    private static final int TOP_NUMBER = 10;

    private static TrainingWNN readTWNNFromFile() {
        Coefficient coefficientF = null;
        Coefficient coefficientS = null;
        Option option = new Option(
                AbsTraining.ACTIVE_F_A,
                ALP,
                NEURONS_FOR_LETTER
        );
        try {
            coefficientF = new Coefficient("First.txt");
            coefficientS = new Coefficient("Second.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Fail to read the NN");
            System.exit(1);
        }
        return new TrainingWNN(
                coefficientF,
                coefficientS,
                option
        );
    }

    private static TrainingWNN generationRandomTWNN() {
        Coefficient coefficient = new Coefficient(
                NEURONS_FOR_LETTER + 1,
                15,
                NEURONS_FOR_LETTER
        );
        Option option = new Option(
                AbsTraining.ACTIVE_F_A,
                ALP,
                NEURONS_FOR_LETTER
        );
        return new TrainingWNN(
                coefficient,
                coefficient,
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
            result.getCoefficient()[0].save("First.txt");
            result.getCoefficient()[1].save("Second.txt");
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
                distances.add(new Pair<Double, Integer>(
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

    private static void trainNNFromFile() {
        TrainingWNN trainingWNN = readTWNNFromFile();
        train(trainingWNN, MAX_TIME);
        WNNConvector result = trainingWNN.getConvector();
        saveWNNToFile(result);
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
            System.out.println("Fail to read dictionary");
            System.exit(1);
        }

        TrainingWNN trainingWNN = readTWNNFromFile();
        WNNConvector convector = trainingWNN.getConvector();
        convector.setAlp(33);
        convector.setMinLetter('а');
        handDictionaryTest(convector, dictionary);
    }

    public static void main(String[] args) {
        trainNNFromFile();
    }
}
