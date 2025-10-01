package lab5;

import java.util.Arrays;
import java.util.Random;

// Класс для оценки надежности
public class Reliability {
    private static final Random RANDOM = new Random();
    // Вероятность безотказной работы P
    public static double probabilityWithoutFailure(int Q, int N) {
        if (N <= 0) throw new IllegalArgumentException("N должно быть > 0");
        return 1.0 - (double) Q / N;
    }
    // Оценка среднего времени восстановления Qв
    public static double averageRecoveryTime(double leftSide, double rightSide, double allowedRecoveryTime) {
        final int SAMPLE_SIZE = 100;
        double[] samples = new double[SAMPLE_SIZE];
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            samples[i] = randomInRange(leftSide, rightSide);
        }
        double meanTv = Arrays.stream(samples).average().orElse(0.0);
        return (meanTv <= allowedRecoveryTime) ? 1.0 : (allowedRecoveryTime / meanTv);
    }
    // Оценка продолжительности преобразования Qп
    public static double transformationDuration(double leftSide, double rightSide, double allowedConversionTime) {
        final int SAMPLE_SIZE = 200;
        double sum = 0.0;
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            double val = randomInRange(leftSide, rightSide);
            double score = (val <= allowedConversionTime) ? 1.0 : (allowedConversionTime / val);
            sum += score;
        }
        return sum / SAMPLE_SIZE;
    }
    private static double randomInRange(double min, double max) {
        return min + (max - min) * RANDOM.nextDouble();
    }
}