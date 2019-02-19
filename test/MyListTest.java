import mycollections.MyList;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyListTest {

    MyList<Integer> list;

    @BeforeEach
    public void init(){
        list = new MyList<>();
        list.add(3);
        list.add(1);
        list.add(72);
        list.add(5);
        list.add(12);
        list.add(11);
    }

    @Test
    public void size() {
        assertEquals(6, list.size());

        list.remove(new Integer(72));

        assertEquals(5, list.size());
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void contains() {
    }

    @Test
    public void indexOf() {
    }

    @Test
    public void toArray() {
    }

    @Test
    public void add() {
    }

    @Test
    public void retainAll() {
    }
}
