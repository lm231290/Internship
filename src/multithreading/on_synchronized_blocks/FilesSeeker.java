package multithreading.on_synchronized_blocks;

import multithreading.QueueProducer;

import java.io.File;
import java.util.PriorityQueue;
import java.util.Queue;

public class FilesSeeker implements QueueProducer {
    public FilesSeeker(File root, String extension) {
        this.root = root;
        this.extension = extension;
    }

    private PriorityQueue<File> queue;
    private File root;
    private String extension;
    private ThreadGroup group = new ThreadGroup("group");

    private void findFiles(File directory, String extension, ThreadGroup group){
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(extension)) {
                synchronized (this) {
                    queue.add(files[i]);
                }
                continue;
            }

            if (files[i].isDirectory()) {
                int finalI = i;
                Thread thread = new Thread(group, () -> findFiles(files[finalI], extension, group));
                thread.run();
            }
        }
    }

    @Override
    public void setQueue(Queue queue) {
        this.queue = (PriorityQueue<File>) queue;
    }

    @Override
    public void produce(Queue queue) {
        if (this.queue == null)
            throw new NullPointerException("Queue is not defined");

        findFiles(root, extension, group);
    }

    @Override
    public void run() {
        produce(queue);
    }
}
