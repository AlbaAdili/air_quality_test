package org.example;

public class SensorData {
    public String time;
    public double pm1;
    public double pm25;
    public double pm10;

    public SensorData(String time, double pm1, double pm25, double pm10) {
        this.time = time;
        this.pm1 = pm1;
        this.pm25 = pm25;
        this.pm10 = pm10;
    }

    @Override
    public String toString() {
        return String.format("%s | PM1: %.2f | PM2.5: %.2f | PM10: %.2f", time, pm1, pm25, pm10);
    }
}