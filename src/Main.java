import mycollections.MyCollection;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<>();

        int n = 5000;

        MyCollection<Integer> collection = new MyCollection<>();
        long timeForMyCollection = System.currentTimeMillis() * -1;
        for (int i = 0; i < n; i++) {
            collection.addToMiddle(i);
        }
        timeForMyCollection += System.currentTimeMillis();

        System.out.print("\nMyCollection:\t" + timeForMyCollection);


        long timeForLinkedList = System.currentTimeMillis() * -1;
        for(int i = 0; i < n; i++)
            list.add(list.size()/2, i);
        timeForLinkedList += System.currentTimeMillis();
        System.out.println("\nArrayList:\t" + timeForLinkedList);
        System.out.println( timeForLinkedList - timeForMyCollection );
    }
}
