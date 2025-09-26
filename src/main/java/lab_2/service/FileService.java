package lab_2.service;
import lab_2.model.ModelLab2;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.io.IOException;
import java.io.InputStream;


//Класс для чтения информации из файла
public class FileService {

    //Метод чтения из файла
    public static void readDataFromFile(String filename, ModelLab2 model) {
        try {
            // Получаем InputStream из ресурсов
            InputStream inputStream = FileService.class.getClassLoader().getResourceAsStream(filename);

            // Проверяем не пуст ли файл
            String[] numberStrings = getStrings(filename, inputStream);
            double[] numbers = new double[numberStrings.length]; // Создаём массив типа double длиной, равной длине массива строк

            // Заполняем массив double данными из массива строк, преобразуя данные
            for (int i = 0; i < numberStrings.length; i++) {
                numbers[i] = Double.parseDouble(numberStrings[i]);
            }

            // Записываем получившийся массив в модель
            model.setBugTime(numbers);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            model.setBugTime(new double[0]); // Возвращаем пустой массив в случае ошибки
        } catch (NumberFormatException e) {
            System.err.println("Ошибка преобразования числа: " + e.getMessage());
            model.setBugTime(new double[0]); // Возвращаем пустой массив в случае ошибки
        }

    }

    private static String @NotNull [] getStrings(String filename, InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("Файл не найден: " + filename);
        }

        // Создаем BufferedReader для чтения
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String content = reader.readLine(); // Читаем строку с данными
        reader.close(); // Закрываем буфер чтения

        // Разбиваем строку на массив строк (разбиваем по пробелу)
        return content.trim().split("\\s+");
    }
}