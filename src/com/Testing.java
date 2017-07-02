package com;

import com.Convector.WNNConvector;
import com.NeuralNetwork.Coefficient;
import com.NeuralNetwork.Option;
import com.Train.AbsTraining;
import com.Train.TrainingWNN;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
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
    private static final long MAX_TIME = 60;

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
    private static void trainNNFromFile() {
        TrainingWNN trainingWNN = readTWNNFromFile();
        train(trainingWNN, MAX_TIME);
        WNNConvector result = trainingWNN.getConvector();
        saveWNNToFile(result);
    }
    private static void handTest(WNNConvector convector) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter two words.");
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

    public static void main(String[] args) {
        TrainingWNN trainingWNN = readTWNNFromFile();
        WNNConvector result = trainingWNN.getConvector();


    }
}
