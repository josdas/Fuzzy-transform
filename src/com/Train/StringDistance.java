package com.Train;

/**
 * Created by josdas on 29.06.2017.
 */
public class StringDistance {
    public static double levenshtein(String a, String b) {
        final int m = a.length();
        final int n = b.length();

        int[] D1;
        int[] D2 = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            D2[i] = i;
        }

        for (int i = 1; i <= m; i++) {
            D1 = D2;
            D2 = new int[n + 1];
            for (int j = 0; j <= n; j++) {
                if (j == 0) {
                    D2[j] = i;
                }
                else {
                    int cost = (a.charAt(i - 1) != b.charAt(j - 1)) ? 1 : 0;
                    if (D2[j - 1] < D1[j] && D2[j - 1] < D1[j - 1] + cost) {
                        D2[j] = D2[j - 1] + 1;
                    } else if (D1[j] < D1[j - 1] + cost) {
                        D2[j] = D1[j] + 1;
                    } else {
                        D2[j] = D1[j - 1] + cost;
                    }
                }
            }
        }
        return D2[n];
    }

    public static double levenshteinSwap(String a, String b) {
        final int n = a.length();
        final int m = b.length();
        double[][] dp = new double[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]);
                }
                if (i > 1 && j > 1
                        && a.charAt(i - 2) == b.charAt(j - 1)
                        && a.charAt(i - 1) == b.charAt(j - 2)) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 2][j - 2] + 0.5);
                }
                dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + 1);
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + 1);
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1] + 1);
            }
        }
        return dp[n][m];
    }
}
