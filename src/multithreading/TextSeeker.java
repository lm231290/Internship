package multithreading;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;


public class TextSeeker implements QueueConsumer{

    public TextSeeker(PriorityQueue<File> queue, String textToBeFound, ReentrantLock lock) {
        this.queue = queue;
        this.textToBeFound = textToBeFound;
        this.lock = lock;
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
    public void operate(Queue queue) {
        while(true) {
            if (queue.size() == 0)
                continue;

            lock.lock();
            File file = (File) queue.poll();
            lock.unlock();

            if (checkFile(file, textToBeFound))
                result.add(file);

            if (queue.size() == 0) {
                return;
            }
        }
    }
    public void run() {
        operate(queue);
    }

    public ArrayList<File> getResult() {
        return (ArrayList<File>) result.clone();
    }
}
