package multithreading;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class TextSeeker {
    public TextSeeker(String textToBeFound, String path) {
        this.textToBeFound = textToBeFound;
        FilesSeeker filesSeeker = new FilesSeeker(path, true);
        filesToBeChecked = new PriorityQueue<>(filesSeeker.getFilesList());
    }
    private String textToBeFound;
    private volatile Queue<File> filesToBeChecked;
    private volatile ArrayList<File> result;
    private ThreadGroup group;

    public ArrayList<File> getResult() throws InterruptedException {
        checkForText();
        while (group.activeCount() != 0) {
            wait(10);
        }
        return (ArrayList<File>) result.clone();
    }

    private void checkForText() {
        result = new ArrayList<>();
        group = new ThreadGroup("group");
        for (int i = 0; i < filesToBeChecked.size(); i++) {

            Thread thread = new Thread(group, () -> {
                    File file = filesToBeChecked.poll();
                    try {
                        Scanner scanner = new Scanner(file);
                        while(scanner.hasNextLine())
                            if (scanner.nextLine().contains(textToBeFound)) {
                                result.add(file);
                                return;
                            }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            );
            thread.run();
        }
    }
}