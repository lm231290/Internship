package multithreading;

import java.io.File;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsumerProducerManager {
    public ConsumerProducerManager(QueueProducer producer, QueueConsumer consumer, Queue queue) {
        this.producer = producer;
        this.consumer = consumer;
        this.queue = queue;
    }

    private QueueProducer producer;
    private QueueConsumer consumer;
//    private Thread producerThread;
//    private Thread consumerThread;

    ExecutorService executor = Executors.newFixedThreadPool(10);

    private Queue queue;

    public void run() {
        consumer.setQueue(queue);
        executor.execute(consumer);
        producer.setQueue(queue);
        producer.setExecutor(newProducer -> newProducer(newProducer));
        executor.execute(producer);
    }

    private void newProducer(QueueProducer newProducer) {
        newProducer.setExecutor(producer -> newProducer(producer));
        executor.execute(newProducer);
    }

    public ArrayList<Object> getResults() {
        return consumer.getResult();
    }


}
