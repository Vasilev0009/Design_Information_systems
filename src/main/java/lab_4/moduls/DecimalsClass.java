package lab_4.moduls;
import java.math.BigDecimal;

public class DecimalsClass {
    public static void example1(){
        System.out.println(new BigDecimal(1.1));
    }

    public static void example2(){
        BigDecimal d1 = new BigDecimal("1.1");
        BigDecimal d2 = new BigDecimal("1.10");
        System.out.println(d1.equals(d2));
    }
}
