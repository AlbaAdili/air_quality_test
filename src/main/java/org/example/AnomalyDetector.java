package org.example;
import java.util.*;
import org.example.SensorData;
import org.example.StatsUtils;

public class AnomalyDetector {
    private final int windowSize;
    private final double threshold;

    public AnomalyDetector(int windowSize, double threshold) {
        this.windowSize = windowSize;
        this.threshold = threshold;
    }

    public boolean detectUpwardTrend(List<SensorData> data, int trendWindow, double minSlope) {
        if (data.size() < trendWindow) return false;

        List<SensorData> subList = data.subList(data.size() - trendWindow, data.size());
        double slope = linearRegressionSlope(subList);

        return slope > minSlope;
    }

    private double linearRegressionSlope(List<SensorData> data) {
        int n = data.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            double x = i;
            double y = data.get(i).pm25;

            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }

        return (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
    }

    public List<String> detectAnomalies(List<SensorData> data) {
        List<String> anomalies = new ArrayList<>();
        LinkedList<Double> window = new LinkedList<>();

        for (SensorData entry : data) {
            if (window.size() == windowSize) window.poll();
            window.add(entry.pm25);
            if (window.size() < windowSize) continue;

            double mean = StatsUtils.mean(new ArrayList<>(window));
            double std = StatsUtils.stdDev(new ArrayList<>(window));

            double zScore = (entry.pm25 - mean) / std;
            if (Math.abs(zScore) > threshold) {
                anomalies.add(entry.toString() + " [Z-score: " + String.format("%.2f", zScore) + "]");
            }
        }
        return anomalies;
    }
}