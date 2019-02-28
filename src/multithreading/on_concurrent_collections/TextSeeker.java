package multithreading.on_concurrent_collections;

import multithreading.QueueConsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class TextSeeker implements QueueConsumer {
    public TextSeeker(String textToBeFound) {
        this.textToBeFound = textToBeFound;
    }

    private LinkedBlockingQueue<File> queue;
    private CopyOnWriteArrayList<File> result = new CopyOnWriteArrayList<>();
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
        this.queue = (LinkedBlockingQueue<File>) queue;

    }

    @Override
    public void operate(Queue queue) {
        if (this.queue == null)
            throw new NullPointerException("Queue is not defined");


        while(true) {
            if (queue.size() == 0)
                continue;

            File file = (File) queue.poll();

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

    public ArrayList<Object> getResult() {
        return (ArrayList<Object>) result.clone();
    }
}
