package org.example;

import java.util.List;

public class StatsUtils {
    public static double mean(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    public static double stdDev(List<Double> values) {
        double mean = mean(values);
        return Math.sqrt(values.stream().mapToDouble(v -> Math.pow(v - mean, 2)).average().orElse(0));
    }
}
