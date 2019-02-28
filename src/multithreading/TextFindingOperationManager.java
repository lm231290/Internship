package multithreading;

import java.io.File;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class TextFindingOperationManager extends Thread{
    public TextFindingOperationManager(String extension, File root, String textToBeFound) {
        this.extension = extension;
        this.root = root;
        this.textToBeFound = textToBeFound;
    }

    private String textToBeFound;
    private String extension;
    private File root;
    private FilesSeeker producer;
    private TextSeeker consumer;
    private Thread producerThread;
    private Thread consumerThread;

    private PriorityQueue<File> queue = new PriorityQueue<>();
    private ReentrantLock lock;

    public void run() {
        lock = new ReentrantLock();

        producer = new FilesSeeker(root, extension, queue, lock);
        producerThread = new Thread(producer);
        producerThread.run();
        consumer = new TextSeeker(queue, textToBeFound, lock);
        consumerThread = new Thread(consumer);
        consumerThread.run();
    }

    public ArrayList<File> getResults() {
        return consumer.getResult();
    }


}
