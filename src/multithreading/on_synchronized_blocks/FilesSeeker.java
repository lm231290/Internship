package multithreading.on_synchronized_blocks;

import multithreading.MyProducerExecutor;
import multithreading.QueueProducer;

import java.io.File;
import java.util.PriorityQueue;
import java.util.Queue;

public class FilesSeeker implements QueueProducer {
    public FilesSeeker(File root, String extension) {
        this.root = root;
        this.extension = extension;
    }
    private MyProducerExecutor executor;
    private PriorityQueue<File> queue;
    private File root;
    private String extension;

    private void findFiles(File directory, String extension){
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
                searchInFolder(files[finalI]);
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

        findFiles(root, extension);
    }

    @Override
    public void searchInFolder(File folder) {
        try {
            FilesSeeker newFileSeeker = new FilesSeeker(folder, this.extension);
            newFileSeeker.setQueue(queue);
            newFileSeeker.setExecutor(executor);
            executor.execute(newFileSeeker);
        } catch (NullPointerException e) {
            System.out.println("Executor is not defined");
        }
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public void setExecutor(MyProducerExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void run() {
        produce(queue);
    }
}
