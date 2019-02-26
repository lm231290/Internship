
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Lambda {
    public static void main (String[] args) {
        A a1 = new A("a1");
        A a2 = new A("a2");
        A a3 = new A("a3");

        B b1 = new B("b1");
        b1.listA.add(a1);
        b1.listA.add(a2);

        B b2 = new B("b2");
        b2.listA.add(a3);
        b2.listA.add(a2);

        C c1 = new C();
        c1.listB.add(b1);
        c1.listB.add(b2);

        C c2 = new C();
        c2.listB.add(b2);

        List<C> list = Arrays.asList(c1, c2);
        Map<A, ArrayList<B>> resultMap = new HashMap<>();

        Map<A, List<B>> result = list.stream()
            .flatMap(c -> c.getListB().stream())
            .flatMap(b -> b.getListA().stream().map(a -> new Pair<>(a, b)))
            .collect(
                Collectors.toMap(
                    Pair::getKey,
                    pair -> Collections.singletonList(pair.getValue()),
                    (left, right) -> Stream.concat(left.stream(), right.stream()).collect(Collectors.toList())
                )
            );
        System.out.println(result);

//        list.stream().forEach(
//            c -> c.listB.forEach(
//                b -> b.listA.forEach(
//                    a -> {
//                        ArrayList<B> tempList;
//                        if(!resultMap.containsKey(a)) {
//                            tempList = new ArrayList<>();
//                            tempList.add(b);
//                            resultMap.put(a, tempList);
//                        } else {
//                            tempList = resultMap.get(a);
//                            if (!tempList.contains(b))
//                                tempList.add(b);
//                        }
//                    }
//                )
//            )
//        );

//        System.out.println(resultMap.toString());

        return;

//        List<Car> cars = Arrays.asList(
//                new Car("AA1111BX", 2007),
//                new Car("AK5555IT", 2010),
//                new Car(null, 2012),
//                new Car("", 2015),
//                new Car("AI3838PP", 2017));
//
//
//
//        cars.stream()
//                .filter(car -> car.getYear() > 2010)
//                .map(Car::getNumber)
//                .filter(c -> c != null && !c.isEmpty())
//                .forEach(System.out::println);

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

class A{
    public A(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return (int)name.charAt(name.length()-1);
    }

    String name;
    @Override
    public String toString() {
        return name;
    }
}
class B{
    public B(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return (int)name.charAt(name.length()-1);
    }

    public List<A> getListA() {
        return new ArrayList<>(listA);
    }

    String name;
    List<A> listA = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }
}
class C {
    public List<B> getListB() {
        return new ArrayList<>(listB);
    }

    List<B> listB = new ArrayList<>();
}

class Pair<K, V>{
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    K key;
    V value;

    public void setValue(V value) {
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}