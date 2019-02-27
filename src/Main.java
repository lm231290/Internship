import multithreading.TextFindingOperationManager;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

//        multithreading.producerconsumerway.TextSeeker textSeeker
//                = new multithreading.producerconsumerway.TextSeeker("toBeFound", "C:\\Users\\Вован\\IdeaProjects");
//        System.out.println(textSeeker.getResultList().toString());

        TextFindingOperationManager manager =
                new TextFindingOperationManager(
                    ".txt", new File("C:\\Users\\Вован\\IdeaProjects"),"toBeFound");
        manager.run();

        Main m = new Main();
        synchronized (m) {
            m.wait(1000);
        }
        System.out.println(manager.getResults());

        /*
        int n = 5000;
        ArrayList<Integer> list = new ArrayList<>();
        MyCollection<Integer> collection = new MyCollection<>();

        long timeForMyCollection = System.currentTimeMillis() * -1;

        for (int i = 0; i < n; i++)
            collection.addToMiddle(i);

        timeForMyCollection += System.currentTimeMillis();
        System.out.print("\nMyCollection:\t" + timeForMyCollection);

        long timeForLinkedList = System.currentTimeMillis() * -1;

        for(int i = 0; i < n; i++)
            list.add(list.size()/2, i);

        timeForLinkedList += System.currentTimeMillis();
        System.out.println("\nArrayList:\t" + timeForLinkedList);
        System.out.println( timeForLinkedList - timeForMyCollection );
        */
    }
}
