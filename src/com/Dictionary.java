package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by josdas on 02.07.2017.
 */
public class Dictionary {
    public static ArrayList<String> generationRandomDictionary(int n, int m, int alp) {
        Random random = new Random();
        ArrayList<String> dictionary = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            int l = random.nextInt(m) + 2;
            for (int j = 0; j < l; j++) {
                stringBuilder.append(
                        (char) (random.nextInt(alp) + 'a')
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

    public static ArrayList<String> readDictionary(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        ArrayList<String> dictionary = new ArrayList<>();
        final int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            dictionary.add(scanner.useDelimiter("\\A").next());
        }
        return dictionary;
    }
}
