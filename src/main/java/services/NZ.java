package services;

import models.NZDisclosure;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NZ {
    private static ArrayList<String> keyWords;

    public static void scrapeData() throws IOException {
        keyWords = new ArrayList<String>(
                Arrays.asList("own securities", "buyback",
                        "buy-back", "buying back", "re-buying",
                        "appendix 3e", "temit", "treasury", "acquisition", "own share", "repurchase")
        );
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet("https://announcements.nzx.com/announcements/index.json");
            HttpResponse response = client.execute(request);
            Scanner sc = new Scanner(response.getEntity().getContent());
            JSONObject jsonObject = null;
            while (sc.hasNext()) {
                jsonObject = new JSONObject(sc.nextLine());
            }
            if (jsonObject == null) return;
            JSONArray announcements = jsonObject.getJSONArray("items");
            announcements.forEach(announcement -> {
                try {
                    processAnnouncement((JSONObject) announcement);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void processAnnouncement(JSONObject object) throws ParseException {
        String headline = object.getString("title");
        String releasedDate = object.getString("pubDate");
        String ticker = object.getString("companyCode");
        BigInteger annsId = object.getBigInteger("announcementId");
        String annsUrl = "https://announcements.nzx.com/detail/" + annsId;

        if (containWords(headline, keyWords)) {
            NZDisclosure disclosure = new NZDisclosure();
            String parsedDate = parseDate(releasedDate);
            disclosure.setHeadline(headline);
            disclosure.setReleasedDate(parsedDate);
            disclosure.setTicker(ticker);
            disclosure.setAnnsUrl(annsUrl);
            saveToDB();
        }

    }


    private static String parseDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEE,dd MMM yyyy hh:mm:ss z");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date parsedDate = simpleDateFormat1.parse(date);
        return simpleDateFormat2.format(parsedDate);
    }

    private static boolean containWords(String headline, List<String> keyWords) {
        return keyWords.stream().anyMatch(keyWord -> headline.toLowerCase().contains(keyWord.toLowerCase()));
    }

    private static void saveToDB() {

        System.out.println("save to DB");
    }
}
