package org.example;

import org.junit.Test;
import java.util.*;
import static org.assertj.core.api.Assertions.*;

public class PM25PredictorTest {

    @Test
    public void shouldPredictNextValueAccurately() {
        List<Double> history = Arrays.asList(10.0, 12.0, 14.0, 16.0); // slope = 2.0
        double predicted = PM25Predictor.predictNext(history);

        assertThat(predicted)
                .as("Predicted next value should follow the linear trend")
                .isCloseTo(18.0, within(0.001));
    }

    @Test
    public void shouldThrowExceptionIfInsufficientData() {
        List<Double> history = Collections.singletonList(10.0);

        assertThatThrownBy(() -> PM25Predictor.predictNext(history))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Need at least 2 points");
    }
}
