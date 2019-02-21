import mycollections.MyCollection;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        LinkedList<Integer> list = new LinkedList<>();

        int n = 1000000;

        MyCollection<Integer> collection = new MyCollection<>();
        long timeForMyCollection = System.nanoTime() * -1;
        for (int i = 0; i < n; i++) {
            collection.addToMiddle(i);
        }
        timeForMyCollection += System.nanoTime();

        System.out.print("\nMyCollection:\t" + timeForMyCollection);


        long timeForLinkedList = System.nanoTime() * -1;
        for(int i = 0; i < n; i++)
            list.add(list.size()/2, i);
        timeForLinkedList -= System.nanoTime();
        System.out.println("LinkedList:\t" + timeForLinkedList);
    }
}
