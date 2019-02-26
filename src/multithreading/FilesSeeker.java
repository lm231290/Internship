package multithreading;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.io.*;

public class FilesSeeker {
    public FilesSeeker(String path, boolean subdirectoriesToo) {
        directory = new File(path);
        searchForFiles();
    }

    private final File directory;
    private ArrayList<File> filesList;

    private void searchForFiles(){
        String[] extensions = new String[]{"txt"};
        Collection files = FileUtils.listFiles(directory, extensions, true);
        filesList = new ArrayList<>(files);
    }

    public ArrayList<File> getFilesList() {
        return (ArrayList<File>) filesList.clone();
    }
}
