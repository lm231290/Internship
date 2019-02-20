import mycollections.MyList;
import org.junit.jupiter.api.*;

import java.util.Iterator;

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

        list.remove(72);

        assertEquals(5, list.size());
    }

    @Test
    public void isEmpty() {
        assertEquals(false, list.isEmpty());
        list.removeAll(list);
//        TODO fix this test fault
        assertEquals(true, list.isEmpty());
    }

    @Test
    public void contains() {

        assertEquals(true, list.contains(72));
        assertEquals(false, list.contains(13));

    }

    @Test
    public void indexOf() {
        assertEquals(2, list.indexOf(72));
        assertEquals(-1, list.indexOf(73));

    }

    @Test
    public void iterator() {
        Iterator iterator = list.iterator();
        Object[] array = list.toArray();
        int index = 0;
        while(iterator.hasNext()) {
            assertEquals(array[index], iterator.next());
            index++;
        }

    }

    @Test
    public void resize() {
        for (int i = 0; i < 100; i++) {
            assertEquals(true, list.add(i));
        }
    }

    @Test
    public void toArray() {
    }

    @Test
    public void retainAll() {
    }
}
