package services.SDS;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Swedes {
    private static int count;

    public static void crawl() {
        count = 0;
        try {
            readCsvFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("total rows are  --- > " + count);
    }

    private static void readCsvFile() throws IOException {
        String path = "static/see.csv";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.ISO_8859_1))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(",,,,,")) break;
                System.out.println(line + "------------->" + count);
                count++;
            }
        }

    }
}
