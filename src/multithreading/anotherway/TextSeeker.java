package multithreading.anotherway;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextSeeker {
    public TextSeeker(String textToBeFound, String directory) {
        this.textToBeFound = textToBeFound;
        this.directory = new File(directory);
    }
    private String textToBeFound;
    private File directory;

    private volatile ArrayList<File> resultList = new ArrayList<>();

    private void checkForText(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine())
            if (scanner.nextLine().contains(textToBeFound)) {
                resultList.add(file);
                return;
            }
    }

    private ThreadGroup group = new ThreadGroup("group");

    private void findFiles(File folder) {
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(".txt")) {
                int finalI = i;
                Thread thread = new Thread(group, () -> {
                    try {
                        checkForText(files[finalI]);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                thread.run();
            }
            if (files[i].isDirectory()) {
                int finalI = i;
                Thread thread = new Thread(group, () -> findFiles(files[finalI]));
                thread.run();
            }
        }
    }

    public ArrayList<File> getResult() throws InterruptedException {
        findFiles(directory);
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
