package com;

import com.Convector.WNNConvector;
import com.Evaluate.EvalWNN;
import com.NeuralNetwork.Coefficient;
import com.NeuralNetwork.WordNN;
import com.Train.AbsTraining;
import com.Train.TrainingWNNC;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by josdas on 26.06.2017.
 */


//done применить сверточную идею для хеширования любых строк
//todo играть с метриками
//done сделать обучение с levenshtein

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
        // unique
        HashSet<String> hs = new HashSet<>();
        hs.addAll(dictionary);
        dictionary.clear();
        dictionary.addAll(hs);
        return dictionary;
    }

    public static void main(String[] args) {
        EvalWNN evalWNN = new EvalWNN();
        Coefficient coefficient = new Coefficient(10, 10, 10);
        WordNN wordNN = new WordNN(coefficient, coefficient, ALP, 10, AbsTraining.ACTIVE_F_A);
        WNNConvector wnnConvector = new WNNConvector(wordNN);

        TrainingWNNC trainingWNNC = new TrainingWNNC(evalWNN, wnnConvector);

        long startTime = System.currentTimeMillis();
        while (true) {
            long timeSpent = (System.currentTimeMillis() - startTime) / 1000;
            if (timeSpent > MAX_TIME) {
                break;
            }
            trainingWNNC.train(100);
        }

        WNNConvector result = trainingWNNC.getConvector();
        Scanner scan = new Scanner(System.in);

        while (true) {
            String strA = scan.nextLine();
            String strB = scan.nextLine();
            double[] pointA = result.get(strA);
            double[] pointB = result.get(strB);

            System.out.println(VectorN.distance(pointA, pointB));
        }
    }
}
