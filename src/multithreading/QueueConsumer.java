package multithreading;

import java.util.Queue;


public interface QueueConsumer<T> extends Runnable{
    default void accept(Queue<T> queue) {
        operate(queue);
    }


    void operate(Queue<T> queue);
}
