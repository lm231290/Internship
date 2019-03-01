package multithreading.on_locks;

import multithreading.QueueConsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;


public class TextSeeker implements QueueConsumer {

    public TextSeeker(String textToBeFound) {
        this.textToBeFound = textToBeFound;
    }
    private ReentrantLock lock;
    private PriorityQueue<File> queue;
    private ArrayList<File> result = new ArrayList<>();
    private String textToBeFound;

    boolean checkFile(File file, String textToBeFound) {
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine())
                if (scanner.nextLine().contains(textToBeFound))
                    return true;
        } catch (FileNotFoundException e) { }
        return false;
    }

    @Override
    public void setQueue(Queue queue) {
        this.queue = (PriorityQueue<File>) queue;

    }

    @Override
    public void operate(Queue queue) {
        if (this.queue == null)
            throw new NullPointerException("Queue is not defined");

        if (this.lock == null)
            throw new NullPointerException("Lock is not defined");


        while(queue != null) {
            if (queue.size() == 0)
                continue;

            lock.lock();
            File file = (File) queue.poll();
            lock.unlock();

            if (checkFile(file, textToBeFound)) {
                lock.lock();
                result.add(file);
                lock.unlock();
//                System.out.println(file.getName());
            }


        }
    }

    public void run() {
        operate(queue);
    }

    public void setLock(ReentrantLock lock) {
        this.lock = lock;
    }

    public ArrayList<Object> getResult() {
        return (ArrayList<Object>) result.clone();
    }
}
