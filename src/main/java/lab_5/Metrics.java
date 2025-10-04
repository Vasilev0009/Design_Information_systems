package lab_5;

// Класс для расчётов метрик
public class Metrics {
    // Итоговая оценка
    public static double finalGrade(double qv, double qp) {
        return (qv + qp) / 2;
    }
    // Абсолютные показатели
    public static double absoluteIndicator(double P, double overall) {
        return (overall + P) / 2;
    }
    // Относительные показатели
    public static double relativeIndicator(double P_ij, double rho_ij) {
        if (rho_ij == 0.0) throw new IllegalArgumentException("rho_ij должно быть != 0");
        return P_ij / rho_ij;
    }
}