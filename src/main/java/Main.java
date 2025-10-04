import lab_2.Lab2Runner;
import java.util.Scanner;
import lab_4.Lab4Runner;
import lab_3.Lab3Runner;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("[2] - Лабораторная работа №2");
        System.out.println("[3] - Лабораторная работа №3");
        System.out.println("[4] - Лабораторная работа №4");
        System.out.println("[5] - Лабораторная работа №5");

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
            case 4:
                Lab4Runner.runner();
                break;
            default:
                System.out.println("Ошибка: введите от 2 до 5!");

        }

        scanner.close();
    }
}