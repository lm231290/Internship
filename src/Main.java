import multithreading.*;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        QueueProducer producer = new FilesSeeker(
                new File("C:\\Users\\Вован\\IdeaProjects"),
                ".txt");
        QueueConsumer consumer = new TextSeeker("toBeFound");

        TextFindingOperationManager manager =
                new TextFindingOperationManager(producer, consumer);

        manager.run();
        manager.join();



        System.out.println(manager.getResults());

        /*
        int n = 5000;
        ArrayList<Integer> list = new ArrayList<>();
        MyCollection<Integer> collection = new MyCollection<>();

        long timeForMyCollection = System.currentTimeMillis() * -1;

        for (int i = 0; i < n; i++)
            collection.addToMiddle(i);

        timeForMyCollection += System.currentTimeMillis();
        System.out.print("\nMyCollection:\t" + timeForMyCollection);

        long timeForLinkedList = System.currentTimeMillis() * -1;

        for(int i = 0; i < n; i++)
            list.add(list.size()/2, i);

        timeForLinkedList += System.currentTimeMillis();
        System.out.println("\nArrayList:\t" + timeForLinkedList);
        System.out.println( timeForLinkedList - timeForMyCollection );
        */
    }
}
