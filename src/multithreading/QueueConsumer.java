package multithreading;

import java.util.ArrayList;
import java.util.Queue;

public interface QueueConsumer extends Runnable{
    void setQueue(Queue queue);
    void operate(Queue queue);
    ArrayList<Object> getResult();
}
