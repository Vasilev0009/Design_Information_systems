package lab_2.service;

import lab_2.model.ModelLab2;

public class MathService {
    private static final double TOLERANCE = 1e-10;
    private static final double B_MIN = 1.1;  // Минимальное B (должно быть > n)
    private static final double B_MAX = 10000.0; // Максимальное B для поиска

    /**
     * Рассчитывает оценку общего числа ошибок B методом бисекции.
     * Уравнение: Σ(1 / (B - i + 1)) = Σ(Xi) / Σ(i * Xi)
     *
     * @param model исходная модель с заполненными xi и n
     * @return новая модель с установленным значением b
     */
    public static ModelLab2 BugCalc(ModelLab2 model, double[] userData) {
        if (userData == null || userData.length == 0) {
            throw new IllegalArgumentException("Данные Xi не могут быть пустыми");
        }

        // Копируем данные из userData в модель (если модель ещё не заполнена)
        ModelLab2 updatedModel = new ModelLab2();
        updatedModel.setXi(userData.clone());
        updatedModel.setN(userData.length);

        double sumXi = 0.0;
        double sumIXi = 0.0;

        for (int i = 1; i <= updatedModel.getN(); i++) {
            sumXi += userData[i - 1];
            sumIXi += i * userData[i - 1];
        }

        if (sumIXi == 0) {
            throw new ArithmeticException("Сумма i * Xi равна нулю — некорректные данные");
        }

        double rhs = sumXi / sumIXi; // правая часть уравнения

        // Метод бисекции для поиска B
        double left = updatedModel.getN() + 0.1; // B > n
        double right = B_MAX;

        while (right - left > TOLERANCE) {
            double mid = (left + right) / 2.0;
            double fMid = calculateFunctionB(mid, updatedModel.getN(), userData);

            if (Math.abs(fMid) < TOLERANCE) {
                break;
            }

            if (fMid > 0) {
                left = mid;
            } else {
                right = mid;
            }
        }

        updatedModel.setB((left + right) / 2.0);
        return updatedModel;
    }

    /**
     * Вычисляет функцию F(B) = Σ(1/(B - i + 1)) - Σ(Xi)/Σ(i * Xi)
     * Нужно найти B, при котором F(B) = 0
     */
    private static double calculateFunctionB(double B, int n, double[] xi) {
        double sumInv = 0.0;
        for (int i = 1; i <= n; i++) {
            sumInv += 1.0 / (B - i + 1);
        }
        double sumXi = 0.0, sumIXi = 0.0;
        for (int i = 1; i <= n; i++) {
            sumXi += xi[i - 1];
            sumIXi += i * xi[i - 1];
        }
        double rhs = sumXi / sumIXi;
        return sumInv - rhs;
    }

    /**
     * Рассчитывает коэффициент пропорциональности K по формуле:
     * K = n / Σ[(B - i + 1) * Xi]
     *
     * @param model модель с уже рассчитанным B
     * @return новая модель с установленным значением k
     */
    public static ModelLab2 PropCoeffCalc(ModelLab2 model) {
        if (model.getB() <= model.getN()) {
            throw new IllegalStateException("Значение B должно быть больше числа обнаруженных ошибок (n)");
        }

        double sum = 0.0;
        for (int i = 1; i <= model.getN(); i++) {
            sum += (model.getB() - i + 1) * model.getXi()[i - 1];
        }

        if (sum == 0) {
            throw new ArithmeticException("Сумма (B - i + 1) * Xi равна нулю — невозможно вычислить K");
        }

        ModelLab2 updatedModel = new ModelLab2(); // конструктор копирования
        updatedModel.setK(model.getN() / sum);
        return updatedModel;
    }

    /**
     * Рассчитывает среднее время до появления (n+1)-й ошибки:
     * X_{n+1} = 1 / [K * (B - n)]
     *
     * @param model модель с уже рассчитанными B и K
     * @return новая модель с установленным значением xNext
     */
    public static ModelLab2 AverageBugTimeCalc(ModelLab2 model) {
        if (model.getK() <= 0 || model.getB() <= model.getN()) {
            throw new IllegalStateException("K должен быть положительным, B > n");
        }

        double xNext = 1.0 / (model.getK() * (model.getB() - model.getN()));

        ModelLab2 updatedModel = new ModelLab2();
        updatedModel.setXNext(xNext);
        return updatedModel;
    }

    /**
     * Рассчитывает время до окончания тестирования:
     * T = Σ_{i=1}^{B} 1/(K * i)
     * B округляется до ближайшего целого
     *
     * @param model модель с уже рассчитанным K
     * @return новая модель с установленным значением tEnd
     */
    public static ModelLab2 TestingEndTimeCalc(ModelLab2 model) {
        if (model.getK() <= 0) {
            throw new IllegalStateException("K должен быть положительным");
        }

        int B = (int) Math.round(model.getB());
        double sum = 0.0;
        for (int i = 1; i <= B; i++) {
            sum += 1.0 / (model.getK() * i);
        }

        ModelLab2 updatedModel = new ModelLab2();
        updatedModel.setTEnd(sum);
        return updatedModel;
    }
}

