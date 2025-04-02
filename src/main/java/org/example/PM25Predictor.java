package org.example;

import java.util.*;

public class PM25Predictor {

    public static double predictNext(List<Double> history) {
        if (history.size() < 2) throw new IllegalArgumentException("Need at least 2 points");

        int n = history.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            double x = i;
            double y = history.get(i);
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }

        double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        return slope * n + intercept; // prediction for next x (i = n)
    }
}
