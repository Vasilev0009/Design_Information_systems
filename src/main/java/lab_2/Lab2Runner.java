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
        InputService.getUserInput(model);
        System.out.println("Данные от пользователя: " + model.getLowerPointer() + ", " + model.getUpperPointer() + ", " + model.getEpsilon());

        // Вычисляем общее число багов в программе
        MathService.BugCalc(model);
        System.out.println("Общее число багов в программе B = " + model.getNumberBug());

        //Вычисляем коэффициент пропорциональности
        MathService.PropCoeffCalc(model);
        System.out.printf("Коэффициент пропорциональности K =  %.4f%n", model.getPropCoeff());

        //Вычисляем среднее время до появления ошибки
        MathService.AverageBugTimeCalc(model);
        System.out.println("Среднее время до появления ошибки X(n+1) =  " +  Math.round(model.getAverageTimeToBug( )* 100) /100.0);

        //Вычисляем время до окончания тестирования
        MathService.TestingEndTimeCalc(model);
        System.out.println("Время до окончания тестирования t(k) = " + Math.round(model.getTimeEndTesting() * 100) /100.0);
    }
}