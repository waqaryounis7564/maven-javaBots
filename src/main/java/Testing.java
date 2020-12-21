
import thread.DownloadFile;

import java.io.IOException;


public class Testing {
    public static void scrapeData() throws IOException {
        System.out.println(Thread.currentThread().getName());
        for (int i = 0; i < 5; i++) {
        System.out.println(Thread.currentThread().getName());
        Thread thread = new Thread(new DownloadFile());
            thread.start();
        }

    }

}





