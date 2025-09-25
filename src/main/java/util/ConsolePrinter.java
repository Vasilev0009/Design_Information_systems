package util;

import java.util.Arrays;
//Класс для вывода в консоль
public class ConsolePrinter {
    public static void printArray(String message, double[] array) {
        String value; // Будет хранить значение(-я)

        // Проверяем, что массив не null
        if (array == null) {
            value = "Массив равен null";
        }
        else
        // Проверяем, что массив не пустой
        if (array.length == 0) {
            value = "Массив пуст";
        }
        else value = Arrays.toString(array); // В ином случае заполняем переменную value значениями из массива

        System.out.println(message + " " + value); // Выводим в консоль
    }
}
