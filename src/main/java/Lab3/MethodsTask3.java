package Lab3;

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
            delta = clamp(delta, -MAX_RATING_CHANGE, MAX_RATING_CHANGE);

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
     * Прогнозирует количество ошибок для нового объема программы
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

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Комплексный расчет всех прогнозов для трех вариантов коэффициентов
     * Удобно для использования в main вместо цикла
     */
    public PredictionResult calculateAllPredictions(ArrayList<Double> volumes, ArrayList<Double> errors,
                                                    double R0, double lambda, double newProgramVolume) {
        PredictionResult result = new PredictionResult();

        for (int variant = 1; variant <= 3; variant++) {
            ArrayList<Double> ratings = ratingSequence(volumes, errors, R0, lambda, variant);
            double lastRating = ratings.get(ratings.size() - 1);
            double predictedErrors = planErrors(lastRating, lambda, newProgramVolume, variant);

            result.addVariantResult(variant, lastRating, predictedErrors, ratings);
        }

        return result;
    }

    /**
     * Record для хранения результатов прогнозирования
     */
    public static class PredictionResult {
        private final ArrayList<Double> lastRatings = new ArrayList<>();
        private final ArrayList<Double> predictedErrors = new ArrayList<>();
        private final ArrayList<ArrayList<Double>> allRatings = new ArrayList<>();

        public void addVariantResult(int variant, double lastRating, double predictedErrors,
                                     ArrayList<Double> ratings) {
            // Индекс = вариант - 1
            int index = variant - 1;

            // Убедимся, что списки достаточно велики
            while (lastRatings.size() <= index) lastRatings.add(0.0);
            while (this.predictedErrors.size() <= index) this.predictedErrors.add(0.0);
            while (allRatings.size() <= index) allRatings.add(new ArrayList<>());

            lastRatings.set(index, lastRating);
            this.predictedErrors.set(index, predictedErrors);
            allRatings.set(index, new ArrayList<>(ratings));
        }

        public double getLastRating(int variant) {
            return lastRatings.get(variant - 1);
        }

        public double getPredictedErrors(int variant) {
            return predictedErrors.get(variant - 1);
        }

        public ArrayList<Double> getRatings(int variant) {
            return new ArrayList<>(allRatings.get(variant - 1));
        }
    }

    // Старые методы оставлены для обратной совместимости

    /**
     * @deprecated Используйте {@link #calculateCoefficientVariant1(double, double)}
     * Соответствует оригинальному методу c1
     */
    @Deprecated
    public double c1(double R, double lambda) {
        return calculateCoefficientVariant1(R, lambda);
    }

    /**
     * @deprecated Используйте {@link #calculateCoefficientVariant2(double, double)}
     * Соответствует оригинальному методу c2
     */
    @Deprecated
    public double c2(double R, double lambda) {
        return calculateCoefficientVariant2(R, lambda);
    }

    /**
     * @deprecated Используйте {@link #calculateCoefficientVariant3(double, double)}
     * Соответствует оригинальному методу c3
     */
    @Deprecated
    public double c3(double R, double lambda) {
        return calculateCoefficientVariant3(R, lambda);
    }

    /**
     * @deprecated Используйте {@link #applySafetyLimits(double)}
     * Соответствует оригинальному методу safeC
     */
    @Deprecated
    private double safeC(double cRaw) {
        return applySafetyLimits(cRaw);
    }
}
