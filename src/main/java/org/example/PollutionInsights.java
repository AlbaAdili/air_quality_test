package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PollutionInsights {
    // Returns a list of hours (e.g. "08", "15") where pollution was unstable
    public static List<String> getUnstableHoursByStdDev(List<SensorData> data, double instabilityThreshold) {
        Map<String, List<Double>> hourToPMs = new HashMap<>();

        for (SensorData entry : data) {
            if (entry.time.length() < 13) continue;
            String hour = entry.time.substring(11, 13); // extract HH from "YYYY-MM-DD HH:mm:ss"
            hourToPMs.putIfAbsent(hour, new ArrayList<>());
            hourToPMs.get(hour).add(entry.pm25);
        }

        List<String> unstableHours = new ArrayList<>();
        for (Map.Entry<String, List<Double>> entry : hourToPMs.entrySet()) {
            double std = StatsUtils.stdDev(entry.getValue());
            if (std > instabilityThreshold) {
                unstableHours.add(entry.getKey());
            }
        }
        return unstableHours;
    }
}
