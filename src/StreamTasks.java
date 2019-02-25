import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StreamTasks {

    public static void main(String[] args) throws FileNotFoundException {
       System.out.println(task2208());
    }


    public static List<People> task2208() {
        String[] strings = {"name", "Ivanov", "country", "Ukraine", "city", "Kiev", "age", null};
        ArrayList<People> list = new ArrayList<>();
        list.add(new People("Ivanov", "Ukraine", "Kiev", null));
        list.add(new People("Ivanov", "Ukraine", "Kiev", "25"));


        return list.stream().peek((p) -> p.setCity("1")
        ).filter((p) -> p.getAge() != null).collect(Collectors.toList());
    }

}

class People {
    public People(String name, String country, String city, String age) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.age = age;
    }
    private String name, country, city, age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}


