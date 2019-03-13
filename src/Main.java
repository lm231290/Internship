import multithreading.*;
import multithreading.on_locks.FilesSeeker;
import multithreading.on_locks.TextSeeker;

import java.io.File;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {
/*
        QueueProducer producer = new FilesSeeker(
                new File("C:\\Users\\Вован\\IdeaProjects"),".txt");
        QueueConsumer consumer = new TextSeeker("toBeFound");

        PriorityQueue queue = new PriorityQueue();
        ReentrantLock lock = new ReentrantLock();

        ((TextSeeker) consumer).setLock(lock);
        ((FilesSeeker) producer).setLock(lock);

        ManagerOnThreadPool manager =
                new ManagerOnThreadPool(producer, consumer, queue);

        manager.run();

        Main m = new Main();
        synchronized (m) {
            m.wait(4000);
        }

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
