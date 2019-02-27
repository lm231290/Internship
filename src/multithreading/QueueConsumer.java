package multithreading;

import java.util.Queue;

@FunctionalInterface
public interface QueueConsumer<T> {
    default void accept(Queue<T> queue) {
        operate(queue);
    }
    void operate(Queue<T> queue);
}
