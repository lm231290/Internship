package multithreading;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class TextSeeker implements QueueConsumer{

    public TextSeeker(LinkedBlockingQueue<File> queue, String textToBeFound) {
        this.queue = queue;
        this.textToBeFound = textToBeFound;
//        this.producingInProgress = producingInProgress;
    }
//    private volatile Boolean producingInProgress;
    private Thread operatingThread;

    private LinkedBlockingQueue<File> queue;
    private ArrayList<File> result = new ArrayList<>();
    private String textToBeFound;

    boolean checkFile(File file, String textToBeFound) {
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine())
                if (scanner.nextLine().contains(textToBeFound))
                    return true;
        } catch (FileNotFoundException e) { }
        return false;
    }

    public Thread getOperatingThread() {
        return operatingThread;
    }

    @Override
    public void operate(Queue queue) {
        if (operatingThread != null) {
            operatingThread.interrupt();
            operatingThread = null;
        }
        operatingThread = new Thread(null, () -> {
                while(true) {
                    if (queue.size() == 0)
                        continue;
                    File file = (File) queue.poll();

                    if (checkFile(file, textToBeFound))
                        result.add(file);

                    if (queue.size() == 0) {
                        return;
                    }
                }
            });
        operatingThread.run();
    }

    public ArrayList<File> getResult() {
        return (ArrayList<File>) result.clone();
    }
}
