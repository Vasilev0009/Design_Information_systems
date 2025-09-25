package lab_2.service;

import lab_2.model.ModelLab2;

public class MathService {
    private static final double TOLERANCE = 1e-10;
    private static final double B_MAX = 10000.0;

    public static ModelLab2 BugCalc(ModelLab2 model, double[] userData) {
        System.out.println("=== НАЧАЛО BugCalc ===");

        if (userData == null || userData.length == 0) {
            throw new IllegalArgumentException("Данные Xi не могут быть пустыми");
        }

        // Используем переданную модель или создаем новую
        ModelLab2 updatedModel = model;
        if (updatedModel == null) {
            updatedModel = new ModelLab2();
        }

        int n = userData.length;
        System.out.println("n = " + n + " (длина массива)");

        double sumXi = 0.0;
        double sumIXi = 0.0;

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



        // Метод бисекции
        double left = n + 0.1;
        double right = B_MAX;

        while (right - left > TOLERANCE) {
            double mid = (left + right) / 2.0;
            double fMid = calculateFunctionB(mid, n, userData);

            if (Math.abs(fMid) < TOLERANCE) {
                break;
            }

            if (fMid > 0) {
                left = mid;
            } else {
                right = mid;
            }
        }

        double calculatedB = (left + right) / 2.0;

        // Устанавливаем значения в модель
        updatedModel.setN(n);
        updatedModel.setXi(userData.clone());
        updatedModel.setB(calculatedB);

        System.out.println("BugCalc результат: n = " + updatedModel.getN() + ", B = " + updatedModel.getB());
        System.out.println("B > n? " + (updatedModel.getB() > updatedModel.getN()));
        System.out.println("=== КОНЕЦ BugCalc ===");

        return updatedModel;
    }

    private static double calculateFunctionB(double B, int n, double[] xi) {
        double sumInv = 0.0;
        for (int i = 1; i <= n; i++) {
            double denominator = B - i + 1;
            if (Math.abs(denominator) < 1e-10) {
                throw new ArithmeticException("Деление на ноль в calculateFunctionB: B - i + 1 = 0");
            }
            double term = 1.0 / denominator;
            sumInv += term;
        }

        double sumXi = 0.0, sumIXi = 0.0;
        for (int i = 1; i <= n; i++) {
            sumXi += xi[i - 1];
            sumIXi += i * xi[i - 1];
        }

        if (Math.abs(sumIXi) < 1e-10) {
            throw new ArithmeticException("Внутренняя сумма i * Xi равна нулю");
        }

        double rhs = sumXi / sumIXi;

        return sumInv - rhs;
    }

    public static ModelLab2 PropCoeffCalc(ModelLab2 model) {
        if (model == null) {
            throw new IllegalArgumentException("Модель не может быть null");
        }

        int n = model.getN();
        double B = model.getB();

        System.out.println("=== PropCoeffCalc ===");
        System.out.println("n = " + n + ", B = " + B);

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

        double calculatedK = n / sum;

        model.setK(calculatedK);
        System.out.println("K = n / sum = " + n + " / " + sum + " = " + calculatedK);
        System.out.println("=== КОНЕЦ PropCoeffCalc ===");

        return model;
    }

    public static ModelLab2 AverageBugTimeCalc(ModelLab2 model) {
        if (model == null) {
            throw new IllegalArgumentException("Модель не может быть null");
        }

        double K = model.getK();
        double B = model.getB();
        int n = model.getN();

        System.out.println("=== AverageBugTimeCalc ===");
        System.out.println("K = " + K + ", B = " + B + ", n = " + n);

        if (K <= 0) {
            throw new IllegalStateException("K должен быть положительным. K = " + K);
        }
        if (B <= n) {
            throw new IllegalStateException("B должен быть больше n. B = " + B + ", n = " + n);
        }

        double denominator = K * (B - n);
        if (Math.abs(denominator) < 1e-15) {
            throw new ArithmeticException("Знаменатель близок к нулю: K * (B - n) = " + denominator);
        }

        double calculatedXNext = 1.0 / denominator;
        model.setXNext(calculatedXNext);

        System.out.println("X_{n+1} = 1 / (K * (B - n)) = 1 / (" + K + " * (" + B + " - " + n + ")) = " + calculatedXNext);
        System.out.println("=== КОНЕЦ AverageBugTimeCalc ===");

        return model;
    }

    /**
     * Рассчитывает время до окончания тестирования
     */
    public static ModelLab2 TestingEndTimeCalc(ModelLab2 model) {
        if (model == null) {
            throw new IllegalArgumentException("Модель не может быть null");
        }

        double K = model.getK();
        double B = model.getB();
        int n = model.getN();

        System.out.println("=== TestingEndTimeCalc ===");
        System.out.println("K = " + K + ", B = " + B + ", n = " + n);

        if (K <= 0) {
            throw new IllegalStateException("K должен быть положительным. K = " + K);
        }
        if (B <= n) {
            throw new IllegalStateException("B должен быть больше n. B = " + B + ", n = " + n);
        }

        // Формула: T = (1/K) * ln(B/(B - n))
        double ratio = B / (B - n);
        double calculatedT = (1.0 / K) * Math.log(ratio);

        model.setTEnd(calculatedT);

        System.out.println("T = (1/K) * ln(B/(B-n)) = (1/" + K + ") * ln(" + ratio + ") = " + calculatedT);
        System.out.println("=== КОНЕЦ TestingEndTimeCalc ===");

        return model;
    }
}