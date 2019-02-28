package multithreading;

import java.io.File;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class FilesSeeker implements QueueProducer{
    public FilesSeeker(File root, String extension, PriorityQueue queue, ReentrantLock lock) {
        this.root = root;
        this.extension = extension;
        this.queue = queue;
        this.lock = lock;
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
    public void produce(Queue queue) {
        findFiles(root, extension, queue, group);
    }

    @Override
    public void run() {
        produce(queue);
    }
}
