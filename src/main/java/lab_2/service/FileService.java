package lab_2.service;

import lab_2.model.ModelLab2;

import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//Класс для чтения информации из файла
public class FileService {

    //Метод чтения из файла
    public static ModelLab2 readDataFromFile(String filename, ModelLab2 model) {
        List<Double> dataList = new ArrayList<>();

        try {
            // Получаем InputStream из ресурсов
            InputStream inputStream = FileService.class.getClassLoader().getResourceAsStream(filename);

            if (inputStream == null) {
                throw new IllegalArgumentException("Файл не найден: " + filename);
            }

            // Создаем BufferedReader для чтения
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String content = reader.readLine();
            reader.close();

            String[] numberStrings = content.trim().split("\\s+");
            double[] numbers = new double[numberStrings.length];

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

        return model;
    }
}