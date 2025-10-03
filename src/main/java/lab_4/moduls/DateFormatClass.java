// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package lab_4.moduls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatClass {
    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void example(){
        System.out.println("-Исходный метод-");
        for(int i = 1; i <= 5; i++) {
            System.out.println("Дата №" + (i) + ": " + DateFormatClass.getDate());
        }

        System.out.println("\n-Исправленный метод-");
        for(int i = 1; i <= 5; i++) {
            System.out.println("Дата №" + (i) + ": " + DateFormatClass.getDateFix());
        }
    }

    // Изначальная версия. Из примера.
    public static String getDate() {
        return format.format(new Date());
    }

    // Исправленная версия. Создаём новый экземпляр каждый раз.
    private static String getDateFix() {
        DateFormat format_local = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return format_local.format(new Date());
    }

}
