import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HK {
    public static void scrape() throws IOException {
        String url = "https://www.sfc.hk/web/EN/regulatory-functions/market-infrastructure-and-trading/short-position-reporting/aggregated-short-positions-of-specified-shares.html";
        List<String> urls = new ArrayList<>();
        Document document = Jsoup.connect(url).get();
        Elements tds = document.select("tbody>tr>td:nth-child(3)");
        for (Element link : tds) {
            String a = link.select("a").attr("href");
            urls.add("https://www.sfc.hk/web/EN/" + a);
        }

        urls.forEach(ur -> {
            try {
                downloadPage(ur);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void downloadPage(String link) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(link).openConnection());
        try (InputStream inputStream = httpURLConnection.getInputStream()) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
                try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        if (line.startsWith("Date")) {
                            line = bufferedReader.readLine();
                            continue;
                        }
                        consumeLine(line);
                        line = bufferedReader.readLine();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("----------------------------------------------------------------------------------------------");

    }

    private static void consumeLine(String line) throws ParseException {
        processCsvLine(line);
    }

    private static void processCsvLine(String line) throws ParseException {
        String[] anns = line.split(",");
        Disclosure disclosure = new Disclosure();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf1.parse(anns[0]);


        disclosure.setDate(sdf2.format(date));
        disclosure.setStockNumber(Integer.parseInt(anns[1]));
        disclosure.setIssuerName(anns[2]);
        disclosure.setAggregatedShortedShares(anns[3]);
        disclosure.setValueOfShortedShares(anns[4]);
        System.out.println(disclosure.getDate());

        // call db
        System.out.println("*************");

    }

}

