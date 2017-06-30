package com;

import com.Convector.WConvector;
import com.Evaluate.Eval;
import com.NeuralNetwork.Coefficient;
import com.NeuralNetwork.RecurrentNN;
import com.Train.TrainingWC;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by josdas on 26.06.2017.
 */


//todo применить сверточную идею для хеширования любых строк
//todo играть с метриками
//todo сделать обучение с levenshtein

public class Testing {
    public static final int ALP = 3;
    private final static int MAX_TIME = 60 * 10;

    private static ArrayList<String> generationDictionary(int n, int m, int alp) {
        Random random = new Random();
        ArrayList<String> dictionary = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            int l = random.nextInt(m) + 2;
            for (int j = 0; j < l; j++) {
                stringBuilder.append(
                        (char)(random.nextInt(alp) + 'a')
                );
            }
            String str = stringBuilder.toString();
            dictionary.add(str);
        }
        HashSet<String> hs = new HashSet<>();
        hs.addAll(dictionary);
        dictionary.clear();
        dictionary.addAll(hs);
        return dictionary;
    }

    public static void main(String[] args) {
        ArrayList<String> dictionary = generationDictionary(10, 6, ALP);
        Eval eval = new Eval(dictionary);
        Coefficient coefficient = new Coefficient(38, 10, 10);
        RecurrentNN recurrentNN = new RecurrentNN(coefficient, TrainingWC.ACTIVE_F_A);
        WConvector wConvector = new WConvector(recurrentNN);
        TrainingWC training = new TrainingWC(eval, wConvector);

        long startTime = System.currentTimeMillis();
        while (true) {
            long timeSpent = (System.currentTimeMillis() - startTime) / 1000;
            if (timeSpent > MAX_TIME) {
                break;
            }
            training.train(1000);
        }

        WConvector result = training.getConvector();
        Scanner scan = new Scanner(System.in);


        dictionary.forEach(System.out::println);
        System.out.println("-------------------");
        while (true) {
            String str = scan.nextLine();
            double[] pointA = result.get(str);
            String best = "";
            double minDis = Double.POSITIVE_INFINITY;
            for (String word : dictionary) {
                double[] pointB = result.get(word);
                double temp = VectorN.distance(pointA, pointB);
                if (minDis > temp) {
                    minDis = temp;
                    best = word;
                }
            }
            System.out.println(best);
        }
    }
}
