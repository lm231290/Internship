package multithreading;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ManagerOnCustomPool extends Thread implements Manager {
    public ManagerOnCustomPool(QueueProducer producer, QueueConsumer consumer, Queue queue) {
        this.producer = producer;
        this.consumer = consumer;
        this.queue = queue;
    }

    Thread consumerThread;
    Thread producerThread;

    private QueueProducer producer;
    private QueueConsumer consumer;
    private Queue queue;
    private PriorityQueue<Runnable> tasksQueue = new PriorityQueue<>();


    ThreadGroup group = new ThreadGroup("group");

    @Override
    public void run() {
        consumer.setQueue(queue);
        producer.setExecutor(newProducer -> newProducer(newProducer));
        producer.setQueue(queue);

        new Thread(group, producer).start();
        new Thread(consumer).start();

        new Thread(() -> {
            //while at least one producer is active
            while (group.activeCount() > 0) {
                processTasksQueue();
            }
        }).start();


    }

    private void processTasksQueue() {
        while (activeCount() < 11 && tasksQueue.size() != 0) {
            new Thread(group, tasksQueue.poll()).start();
        }
    }

    private void newProducer(QueueProducer newProducer) {
        if (activeCount() < 11) {
//            System.out.println(group.activeCount());
            Thread thread = new Thread(group, newProducer);
            thread.run();
            return;
        }
        tasksQueue.add(newProducer);
    }

    @Override
    public ArrayList<Object> getResults() {
        return consumer.getResult();
    }
}
