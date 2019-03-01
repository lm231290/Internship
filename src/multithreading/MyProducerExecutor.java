package multithreading;

import java.io.File;
import java.util.Queue;

@FunctionalInterface
public interface MyProducerExecutor {
    void execute(QueueProducer newProducer);
}
