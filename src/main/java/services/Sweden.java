package services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Sweden {
    public static void scrapeData() {
        Map<String, String> issuers;
        try {
            Document homePage = getPageContent("https://www.fi.se/sv/vara-register/blankningsregistret/");
            Elements tableRows = homePage.select("#aktuella > tbody>tr");
            issuers = tableRows.stream().
                    map(Sweden::consumeRow)
                    .collect(Collectors.toMap(entry -> entry.get("issuerName"), entry -> entry.get("issuerUrl")));
            issuers.forEach(Sweden::getCurrentPositions);
            issuers.forEach(Sweden::getHistoricalPositions);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static Map<String, String> consumeRow(Element row) {
        Map<String, String> issuerDetail = new HashMap<>();
        String issuerName = row.select(" td:nth-child(1) > a").text();
        String issuerUrl = row.select(" td:nth-child(1) > a").attr("href");
        issuerDetail.put("issuerName", issuerName);
        issuerDetail.put("issuerUrl", "https://www.fi.se/" + issuerUrl);
        return issuerDetail;
    }

    private static void getCurrentPositions(String issuerName,String url) {
        try {
            Document document=getPageContent(url);
            Elements elements = document.select("#aktuella > tbody>tr");
            System.out.println(url);
            System.out.println(issuerName);
            elements.forEach(Sweden::getDisclosure);
            System.out.println("-------------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getHistoricalPositions(String issuerName,String url) {
        try {
            Document document=getPageContent(url);
            Elements elements = document.select("#historiska > tbody>tr");
            System.out.println(url);
            System.out.println(issuerName);
            elements.forEach(Sweden::getDisclosure);
            System.out.println("-------------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Document getPageContent(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    private static void getDisclosure(Element row){
        System.out.println(row.select("td:nth-child(1)").text());
        System.out.println(row.select("td:nth-child(2)").text());
        System.out.println(row.select("td:nth-child(3)").text().replace(",", "."));
        System.out.println(row.select("td:nth-child(4)").text());
        System.out.println("***************");

    }
}
