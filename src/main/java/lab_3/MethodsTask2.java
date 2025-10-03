package lab_3;

/**
 * Класс для вычисления метрик Холстеда - Задание 2
 * Расчет метрик программного обеспечения: длины, объема, времени разработки и надежности
 */
public class MethodsTask2 {
    // Константы для формул
    private static final double MODULE_DIVISOR = 8.0;
    private static final double HIERARCHY_THRESHOLD = 80; // 8 * 3
    private static final double BASE_COMPLEXITY = 220.0;
    private static final double ASSEMBLY_COMMAND_RATIO = 3.0 / 8.0;
    private static final double DEVELOPMENT_TIME_RATIO = 3.0 / 8.0;
    private static final double ERROR_RATIO = 1.0 / 3000.0;
    private static final double RELIABILITY_DIVISOR = 2.0;
    private static final double REFERENCE_LOG_VALUE = 48.0;

    /**
     * Вычисляет базовое количество модулей
     * Формула: k = n2* / 8
     *
     * @param independentParameters число независимых параметров n2*
     * @return базовое количество модулей k
     * @throws IllegalArgumentException если independentParameters отрицательное
     */
    public double calculateBaseModules(double independentParameters) {
        validateNonNegative(independentParameters, "independentParameters");
        return independentParameters / MODULE_DIVISOR;
    }

    /**
     * Вычисляет количество модулей с учетом иерархии
     * Если k >> 8, используется приближение K = 1.125 * k
     *
     * @param independentParameters число независимых параметров n2*
     * @return количество модулей с учетом иерархии K
     */
    public double calculateHierarchicalModules(double independentParameters) {
        double baseModules = calculateBaseModules(independentParameters);

        if (baseModules > HIERARCHY_THRESHOLD) {
            return baseModules + baseModules/8;
        } else {
            return baseModules;
        }
    }

    /**
     * Вычисляет длину программы
     * Формула: N = K × (220 + log₂(K))
     *
     * @param hierarchicalModules количество модулей с учетом иерархии K
     * @return длина программы N
     * @throws IllegalArgumentException если hierarchicalModules не положительное
     */
    public double calculateProgramLength(double hierarchicalModules) {
        validatePositive(hierarchicalModules, "hierarchicalModules");

        double logBase2 = Math.log(hierarchicalModules) / Math.log(2.0);
        return hierarchicalModules * (BASE_COMPLEXITY + logBase2);
    }

    /**
     * Вычисляет объем программного обеспечения
     * Формула: V ≈ K × 220 × log₂(48)
     *
     * @param hierarchicalModules количество модулей с учетом иерархии K
     * @return объем ПО V
     * @throws IllegalArgumentException если hierarchicalModules не положительное
     */
    public double calculateProgramVolume(double hierarchicalModules) {
        validatePositive(hierarchicalModules, "hierarchicalModules");

        double logBase2 = Math.log(REFERENCE_LOG_VALUE) / Math.log(2.0);
        return hierarchicalModules * BASE_COMPLEXITY * logBase2;
    }

    /**
     * Вычисляет количество команд ассемблера
     * Формула: P = 3N / 8
     *
     * @param programLength длина программы N
     * @return количество команд ассемблера P
     * @throws IllegalArgumentException если programLength отрицательное
     */
    public double calculateAssemblyCommands(double programLength) {
        validateNonNegative(programLength, "programLength");
        return programLength * ASSEMBLY_COMMAND_RATIO;
    }

    /**
     * Вычисляет календарное время программирования в днях
     * Формула: Tk = 3N / (8 × m × v)
     *
     * @param programLength длина программы N
     * @param m количество программистов
     * @param v производительность (строк/день)
     * @return календарное время программирования в днях
     * @throws IllegalArgumentException если параметры не положительные
     */
    public double calculateDevelopmentTimeDays(double programLength, int m, int v) {
        validateNonNegative(programLength, "programLength");
        validatePositive(m, "m");
        validatePositive(v, "v");

        return (programLength * DEVELOPMENT_TIME_RATIO) / (m * v);
    }

    /**
     * Вычисляет потенциальное количество ошибок
     * Формула: B = V / 3000
     *
     * @param programVolume объем ПО V
     * @return потенциальное количество ошибок B
     * @throws IllegalArgumentException если programVolume отрицательное
     */
    public double calculatePotentialErrors(double programVolume) {
        validateNonNegative(programVolume, "programVolume");
        return programVolume * ERROR_RATIO;
    }

    /**
     * Безопасное вычисление количества ошибок с проверкой на валидность
     *
     * @param programVolume объем ПО V
     * @return потенциальное количество ошибок B (не менее 0)
     */
    public double calculatePotentialErrorsSafe(double programVolume) {
        try {
            double errors = calculatePotentialErrors(programVolume);
            return errors >= 0 ? errors : 0.0;
        } catch (IllegalArgumentException e) {
            return 0.0;
        }
    }

    /**
     * Вычисляет начальную надежность в часах
     * Формула: t_n = (Tk_дни × рабочие_часы_в_день) / (2 × ln(B))
     * Если B ≤ 1, возвращает бесконечность
     *
     * @param developmentTimeDays время разработки в днях
     * @param workDayHours рабочих часов в день
     * @param potentialErrors потенциальное количество ошибок B
     * @return начальная надежность в часах или Double.POSITIVE_INFINITY
     * @throws IllegalArgumentException если параметры не положительные (кроме potentialErrors)
     */
    public double calculateInitialReliabilityHours(double developmentTimeDays,
                                                   double workDayHours, double potentialErrors) {
        validateNonNegative(developmentTimeDays, "developmentTimeDays");
        validatePositive(workDayHours, "workDayHours");

        if (potentialErrors <= 1.0) {
            return Double.POSITIVE_INFINITY;
        }

        double totalHours = developmentTimeDays * workDayHours;
        double logErrors = Math.log(potentialErrors);

        return totalHours / (RELIABILITY_DIVISOR * logErrors);
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

    private void validatePositive(int value, String parameterName) {
        if (value <= 0) {
            throw new IllegalArgumentException(
                    String.format("Параметр '%s' должен быть положительным целым числом. Получено: %d",
                            parameterName, value)
            );
        }
    }
}
