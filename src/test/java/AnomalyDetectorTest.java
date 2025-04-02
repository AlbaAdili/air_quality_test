import org.example.AnomalyDetector;
import org.example.SensorData;
import org.example.StatsUtils;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class AnomalyDetectorTest {

    @Test
    public void testMeanAndStdDev() {
        List<Double> sample = Arrays.asList(1.0, 2.0, 3.0);
        assertEquals(2.0, StatsUtils.mean(sample), 0.001);
        assertEquals(0.816, StatsUtils.stdDev(sample), 0.001);
    }
    @Test
    public void testDetectUpwardTrend() {
        List<SensorData> trendData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            trendData.add(new SensorData("T" + i, 1, 20 + i, 10)); // increasing PM2.5
        }

        AnomalyDetector detector = new AnomalyDetector(10, 2.0);
        boolean trendDetected = detector.detectUpwardTrend(trendData, 20, 0.5);

        assertTrue(trendDetected);
    }

    @Test
    public void testAnomalyDetection() {
        List<SensorData> mockData = new ArrayList<>();

        // Add normal values with PM2.5 = 10
        for (int i = 0; i < 30; i++) {
            mockData.add(new SensorData("T" + i, 1, 10, 15));
        }

        // Add one obvious anomaly (very large spike)
        mockData.add(new SensorData("T-anomaly", 1, 300, 15));

        // Lower threshold to make it easier to detect
        AnomalyDetector detector = new AnomalyDetector(30, 2.0);
        List<String> results = detector.detectAnomalies(mockData);

        // There should be 1 anomaly detected
        assertEquals(1, results.size());
        assertTrue(results.get(0).contains("T-anomaly"));
    }
}