package lab_2;

import lab_2.model.ModelLab2;
import lab_2.service.MathService;

//Клас который запускает выполнения Lab2
public class Lab2Runner {
    public static void runner() {
        ModelLab2 model = new ModelLab2();
        System.out.println("=== Лабораторная работа 2 ===");

        // ДАННЫЕ ДЛЯ ВАРИАНТА 1 — 26 ошибок
        double[] Xi = {
                9, 12, 11, 4, 7, 2, 5, 8, 5, 7, 1, 6, 1, 9, 4, 1, 3, 3, 6, 1, 11, 33, 7, 91, 2, 1
        };



        // Шаг 1: Оценка общего числа ошибок B
        model = MathService.BugCalc(model, Xi);

        // Шаг 2: Оценка коэффициента K
        model = MathService.PropCoeffCalc(model);

        // Шаг 3: Время до следующей ошибки X_{n+1}
        model = MathService.AverageBugTimeCalc(model);

        // Шаг 4: Время до окончания тестирования T
        model = MathService.TestingEndTimeCalc(model);

        // ВЫВОД РЕЗУЛЬТАТОВ
        System.out.printf("Обнаружено ошибок (n): %d%n", model.getN());
        System.out.printf("Оценка общего числа ошибок (B): %.6f%n", model.getB());
        System.out.printf("Коэффициент пропорциональности (K): %.10f%n", model.getK());
        System.out.printf("Время до следующей ошибки (X_{n+1}): %.4f часов%n", model.getXNext());
        System.out.printf("Время до окончания тестирования (T): %.2f часов%n", model.getTEnd());

        System.out.println("\n=== РАСЧЁТЫ ЗАВЕРШЕНЫ ===");
    }
}


