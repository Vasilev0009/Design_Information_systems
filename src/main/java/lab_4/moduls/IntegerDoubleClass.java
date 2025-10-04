// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com

package lab_4.moduls;

import java.util.Random;

public class IntegerDoubleClass {
    static Random rnd = new Random();
    static double[] vals = new double[] {1.0, 2.0, 3.0};

    public static void example1(){
        boolean flag = rnd.nextBoolean();
        Number n = flag ? Integer.valueOf(1).intValue() : Double.valueOf(2.0).doubleValue();
        System.out.println(String.format("1) Число: %f", n));
    }

    public static void example2(){
        while(true)
        {
            try
            {
                boolean flag1 = rnd.nextBoolean();
                boolean flag2 = rnd.nextBoolean();
                Integer n = flag1 ? 1 : flag2 ? 2 : null;
                System.out.println(String.format("2) Число: %d", n));
            }
            catch (Exception ex)
            {
                System.out.println(String.format("2) Вызвано исключение: %s", ex.getMessage()));
                return;
            }
        }
    }

    public static double getValue(int idx) {
        try
        {
            return (idx < 0 || idx >= vals.length) ? null : vals[idx];
        }
        catch (Exception ex)
        {
            System.out.println(String.format("3) Вызвано исключение: %s", ex.getMessage()));
            return 0;
        }
    }
}
