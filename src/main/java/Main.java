import lab_2.Lab2Runner;
import lab_3.Lab3Runner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("[2] - Лабораторная работа №2");
        System.out.println("[3] - Лабораторная работа №3");
        System.out.print("Выберите лабораторную работу: ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 2:
                Lab2Runner.runner();
                break;
            case 3:
                Lab3Runner lab3Runner = new Lab3Runner();
                lab3Runner.run();
                break;
            default:
                System.out.println("Ошибка: введите только 2 или 3!");
        }

        scanner.close();
    }
}