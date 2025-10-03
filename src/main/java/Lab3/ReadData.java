package Lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ReadData {
    private static final String SEPARATOR = ";";

    /**
     * Читает все строки из файла, обрезая пробельные символы
     * Соответствует вызову в main: ArrayList<String> lines = readData.fileLineReader(dataFile);
     */
    public ArrayList<String> fileLineReader(String filename) {
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла '" + filename + "': " + e.getMessage(), e);
        }

        return lines;
    }

    /**
     * Конвертирует строку с разделителями в массив double
     * Соответствует вызовам в main: readData.stringToDoubleArray(lines, 0)[0]
     */
    public double[] stringToDoubleArray(ArrayList<String> lines, int index) {
        validateIndex(lines, index);

        String line = lines.get(index);
        String[] parts = line.split(SEPARATOR);
        double[] result = new double[parts.length];

        for (int i = 0; i < parts.length; i++) {
            result[i] = parseDoubleSafe(parts[i].trim(), line, i);
        }

        return result;
    }

    /**
     * Конвертирует строку с разделителями в список Double
     * Соответствует вызовам в main: readData.stringToDoubleList(lines, 9)
     */
    public ArrayList<Double> stringToDoubleList(ArrayList<String> lines, int index) {
        double[] array = stringToDoubleArray(lines, index);
        ArrayList<Double> result = new ArrayList<>();
        for (double d : array) {
            result.add(d);
        }
        return result;
    }

    /**
     * Вспомогательный метод для чтения одиночного double значения
     * Упрощает вызовы в main, убирая необходимость использовать [0]
     */
    public double readSingleDouble(ArrayList<String> lines, int index) {
        double[] values = stringToDoubleArray(lines, index);

        if (values.length != 1) {
            throw new IllegalArgumentException(
                    String.format("Ожидалось одно значение в строке %d, но найдено %d", index, values.length)
            );
        }

        return values[0];
    }

    /**
     * Вспомогательный метод для чтения одиночного int значения
     * Упрощает чтение целочисленных параметров
     */
    public int readSingleInt(ArrayList<String> lines, int index) {
        return (int) readSingleDouble(lines, index);
    }

    // Вспомогательные приватные методы

    private void validateIndex(ArrayList<String> lines, int index) {
        if (index < 0 || index >= lines.size()) {
            throw new IllegalArgumentException(
                    String.format("Индекс %d за пределами диапазона [0, %d]", index, lines.size() - 1)
            );
        }
    }

    private double parseDoubleSafe(String value, String originalLine, int position) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(
                    String.format("Некорректное числовое значение '%s' в позиции %d строки: '%s'",
                            value, position, originalLine)
            );
        }
    }
}