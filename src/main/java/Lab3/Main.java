package Lab3;

import java.util.ArrayList;

public class Main {
    private static final String DATA_FILE = "Data.txt";

    private final MethodsTask1 task1 = new MethodsTask1();
    private final MethodsTask2 task2 = new MethodsTask2();
    private final MethodsTask3 task3 = new MethodsTask3();
    private static final ReadData readData = new ReadData();

    public static void main(String[] args) {
        System.out.println("Текущая рабочая директория: " + System.getProperty("user.dir"));

        String dataFile = "Data.txt";
        ArrayList<String> lines = readData.fileLineReader(dataFile);
        new Main().run();
    }

    public void run() {
        String dataFile = "src/main/java/Lab3/Data.txt";
        ArrayList<String> lines = readData.fileLineReader(dataFile);
        InputData inputData = readInputData();
        printHeader();

        Task1Result task1Result = executeTask1(inputData);
        Task2Result task2Result = executeTask2(inputData, task1Result.getN2());
        executeTask3(inputData);

        printFooter();
    }

    private InputData readInputData() {
        ArrayList<String> lines = (ArrayList<String>) readData.fileLineReader(DATA_FILE);

        return new InputData(
                readData.stringToDoubleArray(lines, 0)[0], // targets
                readData.stringToDoubleArray(lines, 1)[0], // measurements
                readData.stringToDoubleArray(lines, 2)[0], // trackedParams
                readData.stringToDoubleArray(lines, 3)[0], // calcParams
                readData.stringToDoubleArray(lines, 4)[0], // lambda
                readData.stringToDoubleArray(lines, 5)[0], // R0
                (int) readData.stringToDoubleArray(lines, 6)[0], // m
                (int) readData.stringToDoubleArray(lines, 7)[0], // v
                readData.stringToDoubleArray(lines, 8)[0], // workDayHours
                readData.stringToDoubleList(lines, 9), // volumes
                readData.stringToDoubleList(lines, 10), // errors
                readData.stringToDoubleArray(lines, 11)[0] // newProgramV
        );
    }

    private Task1Result executeTask1(InputData data) {
        double n2 = task1.calcN2(data.targets(), data.measurements(), data.trackedParams(), data.calcParams());
        double potentialVolume = task1.calcPotentialVolume(n2);
        double potentialErrors = task1.calcPotentialErrors(potentialVolume, data.lambda());

        printTask1Results(n2, potentialVolume, potentialErrors);

        return new Task1Result(n2, potentialVolume, potentialErrors);
    }

    private Task2Result executeTask2(InputData data, double n2) {
        double k = task2.calcKbasic(n2);
        double K = task2.calcK(n2);
        double N = task2.calcN(K);
        double V = task2.calcV(K);
        double P = task2.calcP(N);
        double TkDays = task2.calcTkDays(N, data.m(), data.v());
        double B = task2.safeCalcB(V);
        double tnHours = task2.calcTnHours(TkDays, data.workDayHours(), B);

        printTask2Results(k, K, N, V, P, TkDays, data.m(), data.v(), B, tnHours);

        return new Task2Result(k, K, N, V, P, TkDays, B, tnHours);
    }

    private void executeTask3(InputData data) {
        System.out.println("Задание 3 (рейтинг программиста и прогноз ошибок):");

        for (int coefficient = 1; coefficient <= 3; coefficient++) {
            ArrayList<Double> ratings = task3.ratingSequence(data.volumes(), data.errors(), data.R0(), data.lambda(), coefficient);
            double lastRating = ratings.get(ratings.size() - 1);
            double plannedErrors = task3.planErrors(lastRating, data.lambda(), data.newProgramV(), coefficient);

            System.out.printf("Коэффициент варианта %d: текущий рейтинг (после всех программ) = %.3f; прогноз ошибок на новую программу = %.3f%n",
                    coefficient, lastRating, plannedErrors);
        }
    }

    private void printHeader() {
        System.out.println("=== Метрики Холстеда — ВАРИАНТ 1 ===\n");
    }

    private void printFooter() {
        System.out.println("\n=== Конец расчётов ===");
    }

    private void printTask1Results(double n2, double potentialVolume, double potentialErrors) {
        System.out.println("Задание 1:");
        System.out.printf("n2* (число независимых параметров): %.0f%n", n2);
        System.out.printf("Потенциальный объем программы V*: %.3f (округлённо %d)%n",
                potentialVolume, Math.round(potentialVolume));
        System.out.printf("Потенциальное число ошибок B*: %.3f (округлённо %d)%n%n",
                potentialErrors, Math.round(potentialErrors));
    }

    private void printTask2Results(double k, double K, double N, double V, double P,
                                   double TkDays, int m, int v, double B, double tnHours) {
        System.out.println("Задание 2:");
        System.out.printf("Число модулей (k) = %.3f%n", k);
        System.out.printf("Число модулей с учётом иерархии (K) = %.3f%n", K);
        System.out.printf("Длина программы (N) = %.3f%n", N);
        System.out.printf("Объём ПО (V) = %.3f%n", V);
        System.out.printf("Количество команд ассемблера (P) = %.3f%n", P);
        System.out.printf("Календарное время программирования (Tk), дни = %.3f (при m=%d, v=%d)%n",
                TkDays, m, v);
        System.out.printf("Потенциальное количество ошибок (B) = %.3f%n", B);

        if (Double.isInfinite(tnHours)) {
            System.out.println("Начальная надёжность tн = бесконечность (B <= 1 или лог невалиден)");
        } else {
            System.out.printf("Начальная надёжность tн = %.3f часов%n%n", tnHours);
        }
    }

    // Record classes для хранения данных
    private record InputData(
            double targets,
            double measurements,
            double trackedParams,
            double calcParams,
            double lambda,
            double R0,
            int m,
            int v,
            double workDayHours,
            ArrayList<Double> volumes,
            ArrayList<Double> errors,
            double newProgramV
    ) {}

    private record Task1Result(
            double n2,
            double potentialVolume,
            double potentialErrors
    ) {
        public double getN2() {
            return n2;
        }
    }

    private record Task2Result(
            double k,
            double K,
            double N,
            double V,
            double P,
            double TkDays,
            double B,
            double tnHours
    ) {}
}
