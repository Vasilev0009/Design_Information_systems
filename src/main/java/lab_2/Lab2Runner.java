package lab_2;

import lab_2.model.ModelLab2;
import lab_2.service.FileService;
import lab_2.service.InputService;
import lab_2.service.MathService;
import util.ConsolePrinter;

//Клас который запускает выполнения Lab2
public class Lab2Runner {
    public static void runner() {
        ModelLab2 model = new ModelLab2();
        System.out.println("=== Лабораторная работа 2 ===");

        // Получение данных из файла
        FileService.readDataFromFile("tableBugTime.txt", model);
        ConsolePrinter.printArray("Данные из файла:", model.getBugTime());

        // Получение числа найденных ошибок
        model.setNumberOfBugDetected(model.getBugTime().length) ;

        // Получение данных от пользователя
        double[] userData = InputService.getUserInput();
        ConsolePrinter.printArray("Данные от пользователя:", userData);

        // Вычисляем общее число багов в программе
        MathService.BugCalc(model, userData);
        System.out.println("Общее число багов в программе B = " + model.getNumberBug());

        //Вычисляем коэффициент пропорциональности
        MathService.PropCoeffCalc(model);
        System.out.println("Коэффициент пропорциональности K = " + model.getPropCoeff());
        System.out.println("Время до начала тестирования = " + model.getPropCoeff());

        //Вычисляем среднее время до появления ошибки
        MathService.AverageBugTimeCalc(model);
        System.out.println("Среднее время до появления ошибки X(n+1) = " + model.getAverageTimeToBug());

        //Вычисляем время до окончания тестирования
        MathService.TestingEndTimeCalc(model);
        System.out.println("Время до окончания тестирования t(k) = " + model.getTimeEndTesting());
    }
}