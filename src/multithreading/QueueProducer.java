package multithreading;

import java.io.File;
import java.util.Queue;


public interface QueueProducer extends Runnable{
    void setQueue(Queue queue);
    void produce(Queue queue);
    void setExecutor(MyProducerExecutor executor);
    void searchInFolder(File folder);
    String getExtension();
}
