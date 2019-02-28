package multithreading;

import java.util.Queue;


public interface QueueProducer extends Runnable{
    void setQueue(Queue queue);
    void produce(Queue queue);
}
