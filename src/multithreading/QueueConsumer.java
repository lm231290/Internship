package multithreading;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;


public interface QueueConsumer<T> extends Runnable{
    void setQueue(PriorityQueue<T> queue);
    void operate(PriorityQueue<T> queue);
    ArrayList<T> getResult();
    void setLock(ReentrantLock lock);
}
