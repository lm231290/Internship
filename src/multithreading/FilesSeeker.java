package multithreading;

import java.io.File;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class FilesSeeker implements QueueProducer{
    public FilesSeeker(File root, String extension) {
        this.root = root;
        this.extension = extension;
        this.queue = queue;
    }

    private PriorityQueue<File> queue;
    private File root;
    private String extension;
    private ThreadGroup group = new ThreadGroup("group");
    private ReentrantLock lock;

    private void findFiles(File directory, String extension, Queue<File> queue, ThreadGroup group){
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(extension)) {
                lock.lock();
                queue.add(files[i]);
                lock.unlock();
//                notifyAll();
                continue;
            }

            if (files[i].isDirectory()) {
                int finalI = i;
                Thread thread = new Thread(group, () -> findFiles(files[finalI], extension, queue, group));
                thread.run();
            }
        }
    }

    @Override
    public void setQueue(PriorityQueue queue) {
        this.queue = queue;
    }

    @Override
    public void produce(PriorityQueue queue) {
        if (this.queue == null)
            throw new NullPointerException("Queue is not defined");

        if (this.lock == null)
            throw new NullPointerException("Lock is not defined");

        findFiles(root, extension, queue, group);
    }

    @Override
    public void run() {
        produce(queue);
    }

    public void setLock(ReentrantLock lock) {
        this.lock = lock;
    }
}
