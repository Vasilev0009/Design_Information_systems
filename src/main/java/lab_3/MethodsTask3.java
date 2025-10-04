package lab_3;

import java.util.ArrayList;

public class MethodsTask3 {
    // Константы
    private static final double EPSILON = 1e-9;
    private static final double MAX_RATING_CHANGE = 0.5;
    private static final double LEARNING_RATE = 0.001;

    /**
     * Вычисляет последовательность рейтингов программиста
     * Соответствует вызову в main: task3.ratingSequence(volumes, errors, R0, lambda, coeff)
     */
    public ArrayList<Double> ratingSequence(ArrayList<Double> volumes, ArrayList<Double> errors,
                                            double R0, double lambda, int coeffVariant) {
        ArrayList<Double> ratings = new ArrayList<>();
        ratings.add(R0);
        double sumB = 0.0;
        double sumV = 0.0;

        for (int i = 0; i < volumes.size(); i++) {
            double currentR = ratings.get(ratings.size() - 1);
            double cRaw = calculateCoefficient(currentR, lambda, coeffVariant);
            double c = applySafetyLimits(cRaw);

            // Аккумуляция сумм для расчета изменения рейтинга
            sumB += errors.get(i) / c;
            sumV += volumes.get(i);

            // Расчет изменения рейтинга
            double delta = LEARNING_RATE * (sumV - sumB);
            delta = clamp(delta);

            double nextR = currentR * (1.0 + delta);

            // Защита от некорректных значений
            if (Double.isFinite(nextR)) {
                ratings.add(nextR);
            } else {
                ratings.add(Double.NaN);
                break;
            }
        }
        return ratings;
    }

    /**
     * Прогнозирует количество ошибок для нового объема программы.
     * Соответствует вызову в main: task3.planErrors(lastR, lambda, newProgramV, coeff)
     */
    public double planErrors(double Rn, double lambda, double Vnew, int coeffVariant) {
        double cRaw = calculateCoefficient(Rn, lambda, coeffVariant);
        double c = applySafetyLimits(cRaw);
        return c * Vnew;
    }

    // Вспомогательные методы для расчета коэффициентов

    private double calculateCoefficient(double R, double lambda, int variant) {
        return switch (variant) {
            case 1 -> calculateCoefficientVariant1(R, lambda);
            case 2 -> calculateCoefficientVariant2(R, lambda);
            case 3 -> calculateCoefficientVariant3(R, lambda);
            default -> throw new IllegalArgumentException("Неизвестный вариант коэффициента: " + variant);
        };
    }

    private double calculateCoefficientVariant1(double R, double lambda) {
        return 1.0 / (R + lambda);
    }

    private double calculateCoefficientVariant2(double R, double lambda) {
        return 1.0 / (R * lambda);
    }

    private double calculateCoefficientVariant3(double R, double lambda) {
        return 1.0 / R + 1.0 / lambda;
    }

    // Методы безопасности и утилиты

    private double applySafetyLimits(double rawValue) {
        if (Double.isNaN(rawValue)) {
            return EPSILON;
        }

        double sign = Math.signum(rawValue);
        double absoluteValue = Math.abs(rawValue);

        if (absoluteValue < EPSILON) {
            absoluteValue = EPSILON;
        }

        return sign * absoluteValue;
    }

    private double clamp(double value) {
        return Math.max(-0.5, Math.min(MethodsTask3.MAX_RATING_CHANGE, value));
    }

}
