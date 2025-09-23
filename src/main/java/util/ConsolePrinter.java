package util;

import java.util.Arrays;

public class ConsolePrinter {
    public static void printArray(String message, double[] array) {
        String value = "";

        // Проверяем, что массив не null
        if (array == null) {
            value = "Массив равен null";
        }
        else
        // Проверяем, что массив не пустой
        if (array.length == 0) {
            value = "Массив пуст";
        }
        else value = Arrays.toString(array);

        System.out.println(message + " " + value);
    }
}
