package multithreading;

import java.io.File;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class TextFindingOperationManager extends Thread{
    public TextFindingOperationManager(QueueProducer producer, QueueConsumer consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    private QueueProducer producer;
    private QueueConsumer consumer;
    private Thread producerThread;
    private Thread consumerThread;

    private PriorityQueue queue = new PriorityQueue<>();
    private ReentrantLock lock;

    public void run() {
        lock = new ReentrantLock();
        producer.setLock(lock);
        producer.setQueue(queue);
        producerThread = new Thread(producer);
        producerThread.run();

        consumer.setLock(lock);
        consumer.setQueue(queue);
        consumerThread = new Thread(consumer);
        consumerThread.run();
    }

    public ArrayList<File> getResults() {
        return consumer.getResult();
    }


}
