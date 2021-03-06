package services;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import models.Otc_USA_Disclosure;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Otc_USA {
    private static List<Elements> other_markets_elements;
    private static List<Elements> consolidate_elements;

    private static void extractConsolidateData(Document document) {
        consolidate_elements = new ArrayList<>();
        Elements ul = document.select("ul:nth-child(14)");
        consolidate_elements.add(ul);
    }

    private static void extractOtherMarketData(Document document) {
        other_markets_elements = new ArrayList<>();
        for (int domChild = 16; domChild <= 24; ) {
            Elements elements = document.select("ul:nth-child(" + domChild + ")");
            other_markets_elements.add(elements);
            domChild += 2;
        }
    }

    public static void scrapeData() throws MalformedURLException {
        String srcUrl = "http://regsho.finra.org/regsho-";
        for (int month = 1; month <= 12; month++) {
            String monthName = getMonth(month);
            Document document = Jsoup.parse(downloadPage(srcUrl + monthName + ".html"));
            extractConsolidateData(document);
            extractOtherMarketData(document);
            consolidate_elements.forEach(Otc_USA::processLink);
            other_markets_elements.forEach(Otc_USA::processLink);
        }
    }


    private static String downloadPage(String link) {
        String response = "";
        try (WebClient webClient = new WebClient()) {
            URL url = new URL(link);
            WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);
            requestSettings.setAdditionalHeader("User-Agent: ", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36 Edg/85.0.564.51");
            Page homepage = webClient.getPage(requestSettings);
            response = homepage.getWebResponse().getContentAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static void processLink(Elements ul) {
        Elements listItems = extractListItems(ul);
        listItems.forEach(row -> {
            try {
                downloadXls(extractLink(row));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    private static Elements extractListItems(Elements ul) {
        return ul.select("li");

    }

    private static String extractLink(Element ul) {
        return ul.select("a").attr("href");

    }

    private static void downloadXls(String link) throws IOException {
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
                        processLine(line, link);
                        line = bufferedReader.readLine();
                    }
                }
            }
        }
        System.out.println("----------------------------------------------------------------------------------------------");

    }

    private static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }


    private static void processLine(String line, String link) {
        String[] disclosure = line.split("\\|");
        if (disclosure.length != 6) return;
        Otc_USA_Disclosure otc_usa_disclosure=new Otc_USA_Disclosure();
        try {
            System.out.println("DATE : " + parseDate(disclosure[0]));
            System.out.println("Symbol = Ticker :  " + disclosure[1]);
            System.out.println("Short Volume =  Shares Shorted Volume : " + new BigInteger(disclosure[2]));
            System.out.println("Short Exempt Volume = Short Exempt Volume : " + new BigInteger(disclosure[3]));
            System.out.println("Total Volume = Total Volume : " + new BigInteger(disclosure[4]));
            System.out.println("Market : " + disclosure[5]);
            System.out.println("URL :" + link);
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        if (link.contains("CNMS")) {
            saveToConsolidateTable();
        } else saveToOtherMarketTable();
        System.out.println("************************");
    }


    private static void saveToConsolidateTable() {
        System.out.println("consolidated market");
    }

    private static void saveToOtherMarketTable() {
        System.out.println("other market");
    }

    private static String parseDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String output = null;
        try {
            output = outputFormat.format(df.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }
}
