package com;

import com.Convector.WNNConvector;
import com.NeuralNetwork.Coefficient;
import com.NeuralNetwork.Option;
import com.Train.AbsTraining;
import com.Train.TrainingWNN;

import java.util.Scanner;

/**
 * Created by josdas on 26.06.2017.
 */


//done применить сверточную идею для хеширования любых строк
//todo играть с метриками
//done сделать обучение с levenshtein
//todo геометрический поиск ближайших точек через случайные прямые
//todo словарь + быстрый приближенный поиск (можно делать точный поиск по ближайшим)

public class Testing {
    public static final int ALP = 3;
    public static final int NEURONS_FOR_LETTER = 10;
    private static final int MAX_TIME = 60 * 10;

    public static void main(String[] args) {
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
        TrainingWNN trainingWNN = new TrainingWNN(
                coefficient,
                coefficient,
                option
        );

        long startTime = System.currentTimeMillis();
        while (true) {
            long timeSpent = (System.currentTimeMillis() - startTime) / 1000;
            if (timeSpent > MAX_TIME) {
                break;
            }
            System.out.println("Time spent: " + timeSpent + " second");
            trainingWNN.train(100);
        }

        WNNConvector result = trainingWNN.getConvector();
        Scanner scan = new Scanner(System.in);

        System.out.println("Training has been finished");
        while (true) {
            String strA = scan.nextLine();
            String strB = scan.nextLine();
            double[] pointA = result.get(strA);
            double[] pointB = result.get(strB);

            System.out.println(VectorDistance.distance(pointA, pointB) + " "
                    + StringDistance.levenshtein(strA, strB));
        }
    }
}
