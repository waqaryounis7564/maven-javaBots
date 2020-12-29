package services.SDS;

import models.sds.KoreaDisclosure;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class Korea {
    private Map<String, KoreaDisclosure> disclosures;
    private int count;

    public void scrapeData() {
        try {
            disclosures = new HashMap<>();
            readCsvFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.disclosures.forEach((k, v) -> System.out.println("key is:" + k + ", and value is" + v.getPositionDate() + v.getShortPositionPercentage()));
        System.out.println("total counts are:" + count);
    }

    private void readCsvFile() throws IOException {
        String path = "src/main/static/sdsKorea.csv";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(path)))) {
            String line = "";
            while (!((line = br.readLine()) == null)) {
                consumeLine(line);
            }
        }

    }

    private void consumeLine(String line) {

        if (line.startsWith("Date")) return;
        String[] split = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
//        System.out.println(MessageFormat.format("{0},{1},{2},{3},{4},{5},{6},{7}", split[0], split[1], split[2], split[3], split[4], split[5], split[6], split[7]));
        KoreaDisclosure korea = new KoreaDisclosure();
        korea.setPositionDate(split[0]);
        korea.setIsin(split[1]);
        korea.setIssuerName(split[2]);
        korea.setShareShortedVolume(split[3]);
        korea.setShareOutstanding(split[4]);
        korea.setShareShortedValue(split[5]);
        korea.setMarketCap(split[6]);
        korea.setShortPositionPercentage(split[7]);
        disclosures.put(split[0] + split[2], korea);
        count++;


    }
}
