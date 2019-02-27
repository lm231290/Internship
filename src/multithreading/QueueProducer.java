package multithreading;

import java.util.Queue;

public interface QueueProducer<T> {

    void produce(Queue<T> queue);
}
