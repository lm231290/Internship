package multithreading.on_concurrent_collections;

import multithreading.QueueProducer;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class FilesSeeker implements QueueProducer {
    public FilesSeeker(File root, String extension) {
        this.root = root;
        this.extension = extension;
    }

    private LinkedBlockingQueue<File> queue;
    private File root;
    private String extension;
    private ExecutorService pool = Executors.newFixedThreadPool(10);

    private void findFiles(File directory, String extension, Queue<File> queue){
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(extension)) {
                queue.add(files[i]);
                continue;
            }

            if (files[i].isDirectory()) {
                int finalI = i;
                pool.execute(() -> findFiles(files[finalI], extension, queue));
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

        findFiles(root, extension, queue);
    }

    @Override
    public void run() {
        produce(queue);
    }
}
