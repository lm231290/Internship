package multithreading.on_synchronized_blocks;

import multithreading.QueueConsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class TextSeeker implements QueueConsumer {
    public TextSeeker(String textToBeFound) {
        this.textToBeFound = textToBeFound;
    }
    
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


        while(true) {
            if (queue.size() == 0)
                continue;

            synchronized (this) {
                File file = (File) queue.poll();

                if (checkFile(file, textToBeFound))
                    result.add(file);

                if (queue.size() == 0)
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
