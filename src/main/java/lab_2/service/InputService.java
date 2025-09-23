package lab_2.service;

import lab_2.model.ModelLab2;

import java.util.Scanner;

//Класс для получения ввода от пользователя
public class InputService {
    public static double[] getUserInput(ModelLab2 model){
        double[] userData = new double [3];
        Scanner in = new Scanner(System.in);
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Введите верхнюю границу интервала: ");
                userData [0] = Integer.parseInt(in.nextLine());
                if (userData [0] <= model.getNumberOfBugDetected()){
                    System.out.println("Ошибка! Верхняя граница должна быть больше: " + model.getNumberOfBugDetected() );
                    continue;
                }
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Введите корректное число.");
            }
        }
        userData [1] = model.getNumberOfBugDetected();
        userData [2] = 1;
        System.out.println ("По умолчанию нижняя граница равна: " + userData [1] + " Погрешность :" + userData [2]);
        System.out.println ("Хотите ввести нижнюю границу и значение погрешности в ручную?");
        System.out.print ("Введите Yes или No: ");
        while (validInput) {
                String choice;// = new String("");
                choice = in.nextLine().trim();

                if (choice.equalsIgnoreCase("yes")) {
                    getUserData(userData);
                    validInput = false;
                }

                else if (choice.equalsIgnoreCase("no")) {
                    validInput = false;
                }
                else {
                    System.out.print("Ошибка: Введите Yes или No: ");
                }

            }
        in.close();
        return userData;
        }
    public static void getUserData(double [] userData){
        Scanner in = new Scanner(System.in);
        double lowerBordInterval = userData [1];
        boolean validInput = false;

        while (!validInput) {
            try {

                System.out.print("Введите нижнюю границу интервала: ");
                userData [1] = Integer.parseInt(in.nextLine());
                if (userData [1] <= lowerBordInterval || userData[1] >= userData[0]){
                    System.out.println("Ошибка! Нижняя граница должна быть больше: " + lowerBordInterval +
                            "и меньше: " + userData[0] );
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
                userData [2] = Double.parseDouble(in.nextLine());
                validInput =  false;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Введите корректное число.");
            }
        }
        in.close();
    }
}