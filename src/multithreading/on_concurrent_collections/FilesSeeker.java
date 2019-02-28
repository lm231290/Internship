package multithreading.on_concurrent_collections;

import multithreading.QueueProducer;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class FilesSeeker implements QueueProducer {
    public FilesSeeker(File root, String extension) {
        this.root = root;
        this.extension = extension;
    }

    private LinkedBlockingQueue<File> queue;
    private File root;
    private String extension;
    private ThreadGroup group = new ThreadGroup("group");

    private void findFiles(File directory, String extension, Queue<File> queue, ThreadGroup group){
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(extension)) {
                queue.add(files[i]);
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
    public void setQueue(Queue queue) {
        this.queue = (LinkedBlockingQueue<File>) queue;
    }

    @Override
    public void produce(Queue queue) {
        if (this.queue == null)
            throw new NullPointerException("Queue is not defined");

        findFiles(root, extension, queue, group);
    }

    @Override
    public void run() {
        produce(queue);
    }
}
