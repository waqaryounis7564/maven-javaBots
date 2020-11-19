package services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Sweden {
    public static void scrapeData() {
        Map<String, String> issuers;
        try {
            Document homePage = getPageContent("https://www.fi.se/sv/vara-register/blankningsregistret/");
            Elements tableRows = homePage.select("#aktuella > tbody>tr");
            String publishedDate = homePage.select("body > div.container > div:nth-child(2) > div > div > div.results > h2").text().replace("Aktuella positioner", "").trim();
            issuers = tableRows.stream().
                    map(Sweden::consumeRow)
                    .collect(Collectors.toMap(entry -> entry.get("issuerName"), entry -> entry.get("issuerUrl")));
//            issuers.forEach((name,url)->getCurrentPositions(name,url,publishedDate));
//            issuers.forEach((name,url)->getHistoricalPositions(name,url,publishedDate));
            getCurrentPositions("SSAB AB", "https://www.fi.se/sv/vara-register/blankningsregistret/emittent/?id=529900329VS14ZIML164", "2020-11-11");
            getHistoricalPositions("SSAB AB", "https://www.fi.se/sv/vara-register/blankningsregistret/emittent/?id=529900329VS14ZIML164", "2020-11-11");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void readCsvFile() throws IOException {
        String path = "src/main/static/history.csv";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(path), StandardCharsets.ISO_8859_1))) {
            String line = "";
            while (!(line = br.readLine()).equals(",,,,,")) {
                consumeLine(line);
            }
        }

    }

    private static void consumeLine(String line) {

        if (line.contains("\"")) {
            String[] split = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            if (split.length < 4) return;
            System.out.println("position holder" + split[0].replace("\"", "").toUpperCase());
            System.out.println("issuer" + split[1].toUpperCase());
            System.out.println("isin" + split[2]);
            System.out.println("positionInPercent" + split[3].replace("\"", ""));
            System.out.println("positionDate" + split[4]);

        } else {
            String[] split2 = line.split(",");
            System.out.println(split2[0].toUpperCase());
            System.out.println(split2[1].toUpperCase());
            System.out.println(split2[2]);
            System.out.println(split2[3]);
            System.out.println(split2[4]);
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

    private static void getCurrentPositions(String issuerName, String url, String publishedDate) {
        try {
            Document document = getPageContent(url);
            Elements elements = document.select("#aktuella > tbody>tr");
//            System.out.println(url);
//            System.out.println(issuerName);
            elements.forEach(row -> getDisclosure(row, issuerName, publishedDate, url));
            System.out.println("-------------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getHistoricalPositions(String issuerName, String url, String publishedDate) {
        try {
            Document document = getPageContent(url);
            Elements elements = document.select("#historiska > tbody>tr");
//            System.out.println(url);
//            System.out.println(issuerName);
            elements.forEach(row -> getDisclosure(row, issuerName, publishedDate, url));
            System.out.println("-------------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Document getPageContent(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    private static void getDisclosure(Element row, String issuerName, String published, String url) {
        System.out.println("issuerName: " + issuerName);
        System.out.println("published date: " + published);
        System.out.println("url :" + url);
        System.out.println("position holder  :" + row.select("td:nth-child(1)").text());
        System.out.println("Isin :" + row.select("td:nth-child(2)").text());
        System.out.println("position as a percent :" + row.select("td:nth-child(3)").text().replace(",", "."));

        System.out.println("position date :" + row.select("td:nth-child(4)").text());
        System.out.println("***************");

    }

    private static void saveToDB() {

    }
}
