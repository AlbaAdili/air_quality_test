package org.example;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {
        // 1. Load sensor data
        List<SensorData> data = CSVLoader.load("20220901_node3.csv");

        // 2. Run anomaly detection
        AnomalyDetector detector = new AnomalyDetector(30, 3.0);
        List<String> anomalies = detector.detectAnomalies(data);
        System.out.println("=== Anomalies Detected ===");
        for (String a : anomalies) {
            System.out.println("Anomaly: " + a);
        }
        System.out.println("Total anomalies: " + anomalies.size());

        // 3. Check for upward pollution trend
        boolean trend = detector.detectUpwardTrend(data, 10, 0.2);
        System.out.println("\n=== Upward Trend Detection ===");
        System.out.println("Upward trend detected: " + trend);

        // 4. Analyze unstable hours
        List<String> unstable = PollutionInsights.getUnstableHoursByStdDev(data, 1.0);
        System.out.println("\n=== Unstable Hours (Std Dev > 1) ===");
        for (String hour : unstable) {
            System.out.println("Unstable hour: " + hour + ":00");
        }

        // 5. Predict next PM2.5 value using linear regression
        System.out.println("\n=== PM2.5 Prediction ===");
        int lookback = 20;
        if (data.size() >= lookback) {
            List<Double> recent = data.subList(data.size() - lookback, data.size())
                    .stream().map(d -> d.pm25).toList();
            double prediction = PM25Predictor.predictNext(recent);
            System.out.printf("Predicted next PM2.5: %.2f µg/m³%n", prediction);
        } else {
            System.out.println("Not enough data to make prediction.");
        }
    }
}
