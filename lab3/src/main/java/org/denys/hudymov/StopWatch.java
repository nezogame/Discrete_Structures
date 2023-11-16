package org.denys.hudymov;

import java.time.Duration;
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StopWatch {
    private Instant start;
    private Instant end;

    public void startMeasurement() {
        start = Instant.now();
    }

    public Duration endMeasurement() {
        end = Instant.now();
        return Duration.between(start, end);
    }

    public static double calculateStandardDeviation(long[] numbers) {
        int n = numbers.length;
        double sum = 0.0;
        double mean;

        // Calculating the sum of numbers
        for (double num : numbers) {
            sum += num;
        }

        // Calculating mean
        mean = sum / n;

        // Calculating the sum of squares of differences between
        // each number and the mean
        double sumOfSquaredDifferences = 0.0;
        for (double num : numbers) {
            sumOfSquaredDifferences += Math.pow(num - mean, 2);
        }

        // Calculating standard deviation
        return Math.sqrt(sumOfSquaredDifferences / n);
    }
}
