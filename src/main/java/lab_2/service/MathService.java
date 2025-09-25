package lab_2.service;

import lab_2.model.ModelLab2;

//Класс для математических вычислений
public class MathService {
    public static ModelLab2 BugCalc(ModelLab2 model, double[] userData) {
        /**
         * Рассчитывает оценку общего числа ошибок B методом бисекции.
         * Уравнение: Σ(1 / (B - i + 1)) = Σ(Xi) / Σ(i * Xi)
         */
        double a = userData[0]; // нижняя граница
        double b = userData[1]; // верхняя граница
        double epsilon = userData[2]; // точность
        double c = 0;

        double sumXi = 0;
        double sumiXi = 0;
        int n = model.getNumberOfBugDetected();

        // Вычисляем суммы
        for (int i = 0; i < n; i++) {
            sumiXi += model.getBugTime()[i] * (i + 1);
            sumXi += model.getBugTime()[i];
        }
        // Проверка существования корня на интервале


        System.out.println("| Iter |     a     |     c     |     b     |   f(a)    |   f(c)    |   f(b)    |  b - a    |");

        int iteration = 0;
        while (Math.abs(b - a) > epsilon) {
            iteration++;
            c = (a + b) / 2;



            double fa = calculateFunction(sumXi, sumiXi, n, a);
            double fb = calculateFunction(sumXi, sumiXi, n, b);
            if (fa * fb > 0) {
                throw new IllegalArgumentException("На заданном интервале нет корня или их четное количество");}
            double fc = calculateFunction(sumXi, sumiXi, n, c);

            // Форматированный вывод
            String str = String.format("| %4d | %9.6f | %9.6f | %9.6f | %9.6f | %9.6f | %9.6f | %9.6f |",
                    iteration, a, c, b, fa, fc, fb, b - a);
            System.out.println(str);

            // Правильная логика бисекции
            if (fa * fc < 0) {
                b = c; // корень в левой половине
            } else if (fb * fc < 0) {
                a = c; // корень в правой половине
            } else {
                // f(c) = 0 или особый случай
                break;
            }
        }

        System.out.println("Величина B равна: " + c);
        int roundedCeil = (int) Math.ceil(c);
        System.out.println("Округленное значение B: " + roundedCeil);
        model.setNumberBug(roundedCeil);
        return model;
    }
    //Метод для подсчета итераций методом бисекций
    private static double calculateFunction(double sumXi, double sumiXi, int n, double point) {
        double left = 0;

        // Левая часть уравнения: Σ(1 / (B - i + 1))
        for (int i = 0; i < n; i++) {
            left += 1.0 / (point - i);
        }

        // Правая часть уравнения: n * Σ(Xi) / ((B + 1) * Σ(Xi) - Σ(i * Xi))
        double right = n * sumXi / ((point + 1) * sumXi - sumiXi);

        return left - right; // f(B) = left - right = 0
    }

    //Метод вычисляющий Коэффициенты пропорциональности
    public static void PropCoeffCalc(ModelLab2 model){
        // Рассчитывает коэффициент пропорциональности K по формуле:
        // K = n / Σ[(B - i + 1) * Xi]
        // int B = model.getNumberBug();
        double sum = 0.0;
        if (model.getNumberBug() <= model.getNumberOfBugDetected()) {
            throw new IllegalStateException("Значение B должно быть больше числа обнаруженных ошибок (n)");
            }


        for (int i = 1; i <= model.getNumberOfBugDetected(); i++) {
            sum += (model.getNumberBug()  - i + 1) * model.getBugTime()[i - 1];
        }

        if (sum == 0) {
            throw new ArithmeticException("Сумма (B - i + 1) * Xi равна нулю — невозможно вычислить K");
        }

        model.setPropCoeff(model.getNumberOfBugDetected()/sum);

    }

    //Метод вычисляющий среднее время до появления ошибки
    public static ModelLab2 AverageBugTimeCalc(ModelLab2 model) {
        /**
         * Рассчитывает среднее время до появления (n+1)-й ошибки:
         * X_{n+1} = 1 / [K * (B - n)]
         *
         */
        if (model.getPropCoeff() <= 0) {
            throw new IllegalStateException("K должен быть положительным");
        }

        double xNext = 1.0 / (model.getPropCoeff() * (model.getNumberBug() - model.getNumberOfBugDetected()));

        model.setAverageTimeToBug(xNext);
        return model;
    }
    /**
     * Рассчитывает время до окончания тестирования:
     * T = 1/K + Σ_{i=1}^{B-n} 1/i
     */
    public static ModelLab2 TestingEndTimeCalc(ModelLab2 model) {

        double sum = 0.0;
        for (int i = 1; i <= model.getNumberBug() - model.getNumberOfBugDetected(); i++) {
            sum += 1.0 / i;
        }
        model.setTimeEndTesting(1 / model.getPropCoeff() * sum);
        return model;
    }
}