package multithreading;

import java.util.Queue;

public interface QueueProducer<T> extends Runnable{

    void produce(Queue<T> queue);
}
