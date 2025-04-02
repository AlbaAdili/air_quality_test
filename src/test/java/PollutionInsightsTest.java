package org.example;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class PollutionInsightsTest {

    @Test
    public void testGetUnstableHoursByStdDev() {
        List<SensorData> data = new ArrayList<>();
        // Stable hour: 10 with constant PM2.5
        for (int i = 0; i < 10; i++) {
            data.add(new SensorData("2022-09-01 10:0" + i + ":00", 1, 50, 10));
        }
        // Unstable hour: 11 with fluctuating PM2.5
        for (int i = 0; i < 10; i++) {
            double val = (i % 2 == 0) ? 30 : 100;
            data.add(new SensorData("2022-09-01 11:0" + i + ":00", 1, val, 10));
        }

        List<String> unstable = PollutionInsights.getUnstableHoursByStdDev(data, 20);

        assertTrue(unstable.contains("11"));
        assertFalse(unstable.contains("10"));
    }
}
