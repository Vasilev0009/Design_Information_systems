package Lab3;

/**
 * Класс для вычисления метрик Холстеда - Задание 1
 * Расчет потенциального объема программы и ошибок
 */
public class MethodsTask1 {
    private static final double CONSTANT_TERM = 2.0;
    private static final double ERROR_DIVISOR = 3000.0;
    private static final double LOG_BASE = 2.0;

    /**
     * Вычисляет число независимых параметров (n2*)
     * Формула: n2* = targets × measurements × trackedParams + targets × calcParams
     *
     * @param targets количество целей
     * @param measurements количество измерений
     * @param trackedParams количество отслеживаемых параметров
     * @param calcParams количество вычисляемых параметров
     * @return число независимых параметров n2*
     */
    public double calculateIndependentParameters(double targets, double measurements,
                                                 double trackedParams, double calcParams) {
        validatePositive(targets, "targets");
        validatePositive(measurements, "measurements");
        validatePositive(trackedParams, "trackedParams");
        validatePositive(calcParams, "calcParams");

        double firstTerm = targets * measurements * trackedParams;
        double secondTerm = targets * calcParams;

        return firstTerm + secondTerm;
    }

    /**
     * Вычисляет потенциальный объем программы (V*)
     * Формула: V* = (n2* + 2) × log₂(n2* + 2)
     *
     * @param independentParameters число независимых параметров n2*
     * @return потенциальный объем программы V*
     * @throws IllegalArgumentException если independentParameters отрицательное
     */
    public double calculatePotentialVolume(double independentParameters) {
        validateNonNegative(independentParameters, "independentParameters");

        double adjustedParameters = independentParameters + CONSTANT_TERM;
        double logBase2 = Math.log(adjustedParameters) / Math.log(LOG_BASE);

        return adjustedParameters * logBase2;
    }

    /**
     * Вычисляет потенциальное число ошибок (B*)
     * Формула: B* = V² / (3000 × λ)
     *
     * @param potentialVolume потенциальный объем программы V*
     * @param lambda параметр λ (лямбда)
     * @return потенциальное число ошибок B*
     * @throws IllegalArgumentException если potentialVolume отрицательное или lambda не положительное
     */
    public double calculatePotentialErrors(double potentialVolume, double lambda) {
        validateNonNegative(potentialVolume, "potentialVolume");
        validatePositive(lambda, "lambda");

        double squaredVolume = potentialVolume * potentialVolume;
        double divisor = ERROR_DIVISOR * lambda;

        return squaredVolume / divisor;
    }

    // Вспомогательные методы валидации

    private void validatePositive(double value, String parameterName) {
        if (value <= 0) {
            throw new IllegalArgumentException(
                    String.format("Параметр '%s' должен быть положительным числом. Получено: %.2f",
                            parameterName, value)
            );
        }
    }

    private void validateNonNegative(double value, String parameterName) {
        if (value < 0) {
            throw new IllegalArgumentException(
                    String.format("Параметр '%s' должен быть неотрицательным числом. Получено: %.2f",
                            parameterName, value)
            );
        }
    }

    /**
     * Комплексный расчет всех метрик Задания 1
     *
     * @param targets количество целей
     * @param measurements количество измерений
     * @param trackedParams количество отслеживаемых параметров
     * @param calcParams количество вычисляемых параметров
     * @param lambda параметр λ (лямбда)
     * @return объект с результатами всех вычислений
     */
    public Task1Result calculateAll(double targets, double measurements,
                                    double trackedParams, double calcParams, double lambda) {
        double n2 = calculateIndependentParameters(targets, measurements, trackedParams, calcParams);
        double potentialVolume = calculatePotentialVolume(n2);
        double potentialErrors = calculatePotentialErrors(potentialVolume, lambda);

        return new Task1Result(n2, potentialVolume, potentialErrors);
    }

    /**
     * Record для хранения результатов вычислений Задания 1
     */
    public static record Task1Result(
            double independentParameters,
            double potentialVolume,
            double potentialErrors
    ) {
        /**
         * @return округленное значение потенциального объема
         */
        public long getRoundedVolume() {
            return Math.round(potentialVolume);
        }

        /**
         * @return округленное значение потенциальных ошибок
         */
        public long getRoundedErrors() {
            return Math.round(potentialErrors);
        }
    }

    // Старые методы оставлены для обратной совместимости (если нужно)

    /**
     * @deprecated Используйте {@link #calculateIndependentParameters(double, double, double, double)}
     */
    @Deprecated
    public double calcN2(double targets, double measurements, double trackedParams, double calcParams) {
        return calculateIndependentParameters(targets, measurements, trackedParams, calcParams);
    }

    /**
     * @deprecated Используйте {@link #calculatePotentialVolume(double)}
     */
    @Deprecated
    public double calcPotentialVolume(double n2) {
        return calculatePotentialVolume(n2);
    }

    /**
     * @deprecated Используйте {@link #calculatePotentialErrors(double, double)}
     */
    @Deprecated
    public double calcPotentialErrors(double V, double lambda) {
        return calculatePotentialErrors(V, lambda);
    }
}