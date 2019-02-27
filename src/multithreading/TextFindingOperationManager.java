package multithreading;

import java.io.File;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class TextFindingOperationManager implements Runnable{
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
    private Thread managingThread;
    private ReentrantLock lock;
//    private volatile Boolean producingInProgress = true;



    private void startProducing() {
        producer = new FilesSeeker(root, extension);
        producerThread = new Thread(null, () -> producer.produce(producingQueue));
        producerThread.run();
    }

    private void startConsuming() {
        consumer = new TextSeeker(consumingQueue, textToBeFound);
        consumer.operate(consumingQueue);
    }

    private void stopConsuming() {
        if (consumer == null)
            return;

        Thread consumerThread = consumer.getOperatingThread();
        consumerThread.interrupt();
    }

    private void stopManaging() {
        managingThread.interrupt();
    }

    public void stop() {
        stopConsuming();
        stopManaging();
    }

    public ArrayList<File> getResults() {
        return consumer.getResult();
    }

    private void manageQueues() {
        lock = new ReentrantLock();
        managingThread = new Thread(null, () -> {
//            Object object = new Object();
            do {

                if (producingQueue.size() == 0) {
                    continue;
//                    try {
//                        synchronized (object) {
//                            object.wait(10);
//                        }
//                    } catch (InterruptedException e) { }
                }

                if (producingQueue.size() > 0) {
//                    lock = new ReentrantLock();
//                    lock.lock();
                    while (producingQueue.size() > 0) {
                        consumingQueue.add(producingQueue.poll());
                    }
//                    lock.unlock();
                }
            } while (producerThread.isAlive());
        });
        managingThread.run();
    }

    @Override
    public void run() {
        startProducing();
        manageQueues();
        startConsuming();
    }
}
