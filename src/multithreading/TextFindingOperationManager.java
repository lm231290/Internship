package multithreading;

import java.io.File;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class TextFindingOperationManager{
    public TextFindingOperationManager(String extension, File root, String textToBeFound) {
        this.extension = extension;
        this.root = root;
        this.textToBeFound = textToBeFound;
    }
    private String textToBeFound;
    private String extension;
    private File root;
    private volatile PriorityQueue<File> producingQueue = new PriorityQueue<>();
    private volatile LinkedBlockingQueue<File> consumingQueue = new LinkedBlockingQueue<>();
    private FilesSeeker producer;
    private TextSeeker consumer;
    private Thread producerThread;
    private Thread consumerThread;

    private PriorityQueue<File> queue = new PriorityQueue<>();

    public void run() {
        producer = new FilesSeeker(root, extension);
        producerThread = new Thread(() -> producer.produce(queue));
        producerThread.run();
        consumer = new TextSeeker(queue, textToBeFound);
        consumerThread = new Thread(() -> consumer.accept(queue));
        consumerThread.run();
    }

    public ArrayList<File> getResults() {
        return consumer.getResult();
    }


}
