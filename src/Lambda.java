
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;


public class Lambda {
    public static void main (String[] args) {


        List<Car> cars = Arrays.asList(
                new Car("AA1111BX", 2007),
                new Car("AK5555IT", 2010),
                new Car(null, 2012),
                new Car("", 2015),
                new Car("AI3838PP", 2017));



        cars.stream()
                .filter(car -> car.getYear() > 2010)
                .map(Car::getNumber)
                .filter(c -> c != null && !c.isEmpty())
                .forEach(System.out::println);

//        System.out.println(calculate(10D, 30D, (value1, value2) -> value1 + value2));
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

