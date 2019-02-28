package multithreading;

import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public interface QueueProducer<T> extends Runnable{
    void setQueue(PriorityQueue<T> queue);
    void produce(PriorityQueue<T> queue);
    void setLock(ReentrantLock lock);
}
