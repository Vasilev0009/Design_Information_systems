package lab_2.service;

import lab_2.model.ModelLab2;

import java.util.Arrays;

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
        System.out.println("=== НАЧАЛО BugCalc ===");

        if (userData == null || userData.length == 0) {
            throw new IllegalArgumentException("Данные Xi не могут быть пустыми");
        }

        // Используем переданную модель или создаем новую, но правильно инициализируем
        ModelLab2 updatedModel = model;
        if (updatedModel == null) {
            updatedModel = new ModelLab2();
        }

        updatedModel.setXi(userData.clone());
        updatedModel.setN(userData.length); // Убедитесь, что этот метод работает!

        // ПРОВЕРКА: используем userData.length вместо updatedModel.getN()
        int n = userData.length; // ← ФИКС: используем реальную длину массива
        System.out.println("n = " + n + " (длина массива)");
        System.out.println("model.getN() = " + updatedModel.getN());

        double sumXi = 0.0;
        double sumIXi = 0.0;

        // ФИКС: используем n (длину массива) вместо model.getN()
        for (int i = 1; i <= n; i++) {
            sumXi += userData[i - 1];
            sumIXi += i * userData[i - 1];
            System.out.println("i=" + i + ", Xi=" + userData[i-1] + ", i*Xi=" + (i * userData[i-1]) +
                    ", sumXi=" + sumXi + ", sumIXi=" + sumIXi);
        }

        System.out.println("ФИНАЛЬНЫЕ СУММЫ: sumXi = " + sumXi + ", sumIXi = " + sumIXi);

        if (Math.abs(sumIXi) < 1e-10) {
            throw new ArithmeticException("Сумма i * Xi равна нулю — некорректные данные. sumIXi = " + sumIXi);
        }

        double rhs = sumXi / sumIXi;

        // Метод бисекции - также используем n вместо model.getN()
        double left = n + 0.1; // ← ФИКС
        double right = B_MAX;

        while (right - left > TOLERANCE) {
            double mid = (left + right) / 2.0;
            double fMid = calculateFunctionB(mid, n, userData); // ← ФИКС

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
        System.out.println("BugCalc результат: n = " + updatedModel.getN() + ", B = " + updatedModel.getB());
        System.out.println("B > n? " + (updatedModel.getB() > updatedModel.getN()));
        return updatedModel;
    }

    /**
     * Вычисляет функцию F(B) = Σ(1/(B - i + 1)) - Σ(Xi)/Σ(i * Xi)
     * Нужно найти B, при котором F(B) = 0
     */
    private static double calculateFunctionB(double B, int n, double[] xi) {
        System.out.println("  calculateFunctionB: B=" + B + ", n=" + n);

        double sumInv = 0.0;
        for (int i = 1; i <= n; i++) {
            double denominator = B - i + 1;
            System.out.println("    i=" + i + ", denominator=" + denominator);
            if (Math.abs(denominator) < 1e-10) {
                System.out.println("    ERROR: Деление на ноль!");
                throw new ArithmeticException("Деление на ноль в calculateFunctionB: B - i + 1 = 0");
            }
            double term = 1.0 / denominator;
            sumInv += term;
            System.out.println("    term=" + term + ", sumInv=" + sumInv);
        }

        double sumXi = 0.0, sumIXi = 0.0;
        for (int i = 1; i <= n; i++) {
            sumXi += xi[i - 1];
            sumIXi += i * xi[i - 1];
        }

        System.out.println("    Внутренние суммы: sumXi=" + sumXi + ", sumIXi=" + sumIXi);

        if (Math.abs(sumIXi) < 1e-10) {
            System.out.println("    ERROR: Внутренняя сумма i*Xi равна нулю!");
            throw new ArithmeticException("Внутренняя сумма i * Xi равна нулю");
        }

        double rhs = sumXi / sumIXi;
        System.out.println("    rhs=" + rhs);

        double result = sumInv - rhs;
        System.out.println("    result=sumInv-rhs=" + sumInv + "-" + rhs + "=" + result);

        return result;
    }

    /**
     * Рассчитывает коэффициент пропорциональности K по формуле:
     * K = n / Σ[(B - i + 1) * Xi]
     *
     * @param model модель с уже рассчитанным B
     * @return новая модель с установленным значением k
     */
    public static ModelLab2 PropCoeffCalc(ModelLab2 model) {
        if (model == null) {
            throw new IllegalArgumentException("Модель не может быть null");
        }

        int n = model.getN();
        double B = model.getB();

        if (B <= n) {
            throw new IllegalStateException("B должен быть больше n. B = " + B + ", n = " + n);
        }

        double sum = 0.0;
        double[] xi = model.getXi();
        for (int i = 1; i <= n; i++) {
            sum += (B - i + 1) * xi[i - 1];
        }

        if (Math.abs(sum) < 1e-10) {
            throw new ArithmeticException("Сумма (B - i + 1) * Xi равна нулю");
        }

        double K = n / sum;

        // ИЗМЕНЯЕМ СУЩЕСТВУЮЩУЮ МОДЕЛЬ, а не создаем новую
        model.setK(K);

        System.out.println("PropCoeffCalc: установлен K = " + K);

        return model; // Возвращаем ту же модель
    }

    /**
     * Рассчитывает среднее время до появления (n+1)-й ошибки:
     * X_{n+1} = 1 / [K * (B - n)]
     *
     * @param model модель с уже рассчитанными B и K
     * @return новая модель с установленным значением xNext
     */
    public static ModelLab2 AverageBugTimeCalc(ModelLab2 model) {
        if (model == null) {
            throw new IllegalArgumentException("Модель не может быть null");
        }

        double K = model.getK();
        double B = model.getB();
        int n = model.getN();

        if (K <= 0) {
            throw new IllegalStateException("K должен быть положительным. K = " + K);
        }
        if (B <= n) {
            throw new IllegalStateException("B должен быть больше n. B = " + B + ", n = " + n);
        }

        double xNext = 1.0 / (K * (B - n));

        // ИЗМЕНЯЕМ СУЩЕСТВУЮЩУЮ МОДЕЛЬ
        model.setXNext(xNext);

        System.out.println("AverageBugTimeCalc: установлен XNext = " + xNext);

        return model; // Возвращаем ту же модель
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

