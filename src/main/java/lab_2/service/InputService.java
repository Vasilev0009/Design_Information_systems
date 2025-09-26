package lab_2.service;

import lab_2.model.ModelLab2;
import java.util.Scanner;

//Класс для получения ввода от пользователя
public class InputService {
    static Scanner in = new Scanner(System.in);
    public static void getUserInput(ModelLab2 model){
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Введите верхнюю границу интервала: ");
                model.setUpperPointer(Integer.parseInt(in.nextLine()));
                if (model.getUpperPointer() <= model.getNumberOfBugDetected()){
                    System.out.println("Ошибка! Верхняя граница должна быть больше: " + model.getNumberOfBugDetected() );
                    continue;
                }
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Введите корректное число.");
            }
        }
        model.setLowerPointer(model.getNumberOfBugDetected());
        model.setEpsilon(0.001);
        System.out.println ("По умолчанию нижняя граница равна: " + model.getLowerPointer() + " Погрешность: " + model.getEpsilon());
        System.out.println ("Хотите ввести нижнюю границу и значение погрешности в ручную?");
        System.out.print ("Введите Yes или No: ");
        while (validInput) {
                String choice;
                choice = in.nextLine().trim();

                if (choice.equalsIgnoreCase("yes")) {
                    getUserData(model);
                    validInput = false;
                }

                else if (choice.equalsIgnoreCase("no")) {
                    validInput = false;
                }
                else {
                    System.out.print("Ошибка: Введите Yes или No: ");
                }

            }
    }
    public static void getUserData(ModelLab2 model){
        Scanner in = new Scanner(System.in);
        double lowerBordInterval = model.getLowerPointer();
        boolean validInput = false;

        while (!validInput) {
            try {

                System.out.print("Введите нижнюю границу интервала: ");
                model.setLowerPointer(Integer.parseInt(in.nextLine()));
                if (model.getLowerPointer() < lowerBordInterval || model.getLowerPointer() >= model.getUpperPointer()){
                    System.out.println("Ошибка! Нижняя граница должна быть больше: " + lowerBordInterval +
                            " и меньше: " + model.getUpperPointer() );
                    continue;
                }
                validInput =  true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Введите корректное число.");
            }
        }
        while (validInput) {
            try {
                System.out.print("Введите желаемую погрешность: ");
                model.setEpsilon(Double.parseDouble(in.nextLine()));
                validInput =  false;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Введите корректное число.");
            }
        }
    }
}