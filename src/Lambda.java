
import java.util.function.BiFunction;


public class Lambda {
    public static void main (String[] args) {





        System.out.println(calculate(10D, 30D, (value1, value2) -> value1 + value2));
    }

    public static double calculate(Double x, Double y, BiFunction<Double, Double, Double> biFunction){
        return biFunction.apply(x, y);
    }
}

class Car {
    private String number;
    private int year;

    public Car(String number, int year) {
        this.number = number;
        this.year = year;
    }

    public String getNumber() {
        return number;
    }

    public int getYear() {
        return year;
    }
}

