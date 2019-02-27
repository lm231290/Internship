package multithreading.producerconsumerway;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class TextSeeker {
    public TextSeeker(String textToBeFound, String directory) {
        this.textToBeFound = textToBeFound;
        this.directory = new File(directory);

        Thread thread = new Thread (group, () -> findFiles(this.directory));
        thread.run();
        checkFiles();
        System.out.println(1);
    }
    private String textToBeFound;
    private File directory;
    private boolean end = false;

    private volatile ArrayList<File> resultList = new ArrayList<>();

    private void checkForText(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine())
            if (scanner.nextLine().contains(textToBeFound)) {
                resultList.add(file);
                return;
            }
    }
    private void checkFiles() {
        Thread thread = new Thread(() -> {
            do {
                while (group.activeCount() != 0 || queue.size() != 0) {
                    try {
                        checkForText(queue.poll());
                    } catch (NullPointerException e) {
                    } catch (FileNotFoundException e) {}
                }
            } while (group.activeCount() != 0);
        });
        thread.run();
    }

    private LinkedBlockingQueue<File> queue = new LinkedBlockingQueue<>();
    private ThreadGroup group = new ThreadGroup("group");

    private void findFiles(File folder) {
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(".txt")) {
                queue.add(files[i]);
                continue;
            }

            if (files[i].isDirectory()) {
                int finalI = i;
                Thread thread = new Thread(group, () -> findFiles(files[finalI]));
                thread.run();
            }
        }
    }

    public ArrayList<File> getResultList() throws InterruptedException {
        while (group.activeCount() != 0) {
            wait(10);
        }
        return (ArrayList<File>) resultList.clone();
    }

    public String getTextToBeFound() {
        return textToBeFound;
    }

    public String getDirectory() {
        return directory.getName();
    }
}

