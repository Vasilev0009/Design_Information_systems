package lab_5;

//Главный класс для запуска
public class Lab5Runner {
    public static void runner() {
        //Параметры
        int Q = 5;                  //количество зарегистрированных отказов
        int N = 1000;               //количество экспериментов

        //Для Qв (T_v)
        double tvLeft = 0.7;
        double tvRight = 1.2;
        double T_dop_v = 0.85;      //допустимое среднее время восстановления
        //Для Qп (T_i)
        double tiLeft = 9.0;
        double tiRight = 14.0;
        double T_dop_i = 12.0;      //допустимое время преобразования

        double rho = 0.95;          //базовый критерий надежности
        //Вычисления согласно выбранным формулам
        double P = Reliability.probabilityWithoutFailure(Q, N);
        double Qv = Reliability.averageRecoveryTime(tvLeft, tvRight, T_dop_v);
        double Qp = Reliability.transformationDuration(tiLeft, tiRight, T_dop_i);

        double overall = Metrics.finalGrade(Qv, Qp);
        double P_ij = Metrics.absoluteIndicator(Qv, Qp);
        double K_ij = Metrics.relativeIndicator(P_ij, rho);
        //Вывод результатов
        System.out.printf("Вероятность безотказной работы: %.6f%n", P);
        System.out.printf("Оценка по среднему времени восстановления: %.6f%n", Qv);
        System.out.printf("Оценка по продолжительности преобразования: %.6f%n", Qp);
        System.out.printf("Итоговая оценка: %.6f%n", overall);
        System.out.printf("Абсолютный показатель: %.6f%n", P_ij);
        System.out.printf("Относительный показатель: %.6f%n", K_ij);
        System.out.printf("Фактор надежности: %.6f%n", K_ij);
    }
}
