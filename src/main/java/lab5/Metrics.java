package lab5;

// Класс для расчётов метрик
public class Metrics {
    // Итоговая оценка
    public static double finalGrade(double qv, double qp) {
        return (qv + qp) / 2.0;
    }
    // Абсолютные показатели
    public static double absoluteIndicator(double firstMetric, double secondMetric) {
        return 0.5 * firstMetric + 0.5 * secondMetric;
    }
    // Относительные показатели
    public static double relativeIndicator(double P_ij, double rho_ij) {
        if (rho_ij == 0.0) throw new IllegalArgumentException("rho_ij должно быть != 0");
        return P_ij / rho_ij;
    }
}