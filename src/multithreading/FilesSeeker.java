package multithreading;

import java.io.File;
import java.util.Queue;

public class FilesSeeker implements QueueProducer{
    public FilesSeeker(File root, String extension) {
        this.root = root;
        this.extension = extension;
//        this.producingInProgress = producingInProgress;
    }

//    private volatile Boolean producingInProgress;
    private File root;
    private String extension;
    private ThreadGroup group = new ThreadGroup("group");

    private void findFiles(File directory, String extension, Queue<File> queue, ThreadGroup group){
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(extension)) {
                queue.add(files[i]);
//                notifyAll();
                continue;
            }

            if (files[i].isDirectory()) {
                int finalI = i;
                Thread thread = new Thread(group, () -> findFiles(files[finalI], extension, queue, group));
                thread.run();
            }
        }
//        producingInProgress = false;
    }

    @Override
    public void produce(Queue queue) {
        findFiles(root, extension, queue, group);
    }
}
