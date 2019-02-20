import mycollections.MyList;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ArrayList<Integer> list = new ArrayList<>();
        int n = 10000;
        list.removeAll(list);
        long l = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            list.add(list.size()/2, 1);
        }
        System.out.println( System.currentTimeMillis() - l);
    }
}
