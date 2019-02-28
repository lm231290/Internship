package multithreading;

import java.util.ArrayList;
import java.util.Queue;

public class ConsumerProducerManager extends Thread{
    public ConsumerProducerManager(QueueProducer producer, QueueConsumer consumer, Queue queue) {
        this.producer = producer;
        this.consumer = consumer;
        this.queue = queue;
    }

    private QueueProducer producer;
    private QueueConsumer consumer;
    private Thread producerThread;
    private Thread consumerThread;

    private Queue queue;

    public void run() {
        producer.setQueue(queue);
        producerThread = new Thread(producer);
        producerThread.run();

        consumer.setQueue(queue);
        consumerThread = new Thread(consumer);
        consumerThread.run();
    }

    public ArrayList<Object> getResults() {
        return consumer.getResult();
    }


}
