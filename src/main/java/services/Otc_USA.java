package services;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Otc_USA {

    public static void scrapeData() throws MalformedURLException {
        try (WebClient webClient = new WebClient()) {
            URL url = new URL("http://regsho.finra.org/regsho-September.html");
            WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);
            requestSettings.setAdditionalHeader("User-Agent: ", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36 Edg/85.0.564.51");
            Page homepage = webClient.getPage(requestSettings);
            Document document = Jsoup.parse(homepage.getWebResponse().getContentAsString());
            List<Elements> li_other_markets = new ArrayList<>();
            Elements consolidate_elements = document.select("ul:nth-child(14)");
            consolidate_elements.forEach();
//            Elements rows = extractListItems(consolidate_elements);
//            rows.forEach(row -> {
//                try {
//                    downloadXls(extractLink(row));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
            for (int domChild = 16; domChild <= 24; ) {
                Elements elements = document.select("ul:nth-child(" + domChild + ")");
                li_other_markets.add(elements);
                domChild += 2;
            }
            li_other_markets.forEach(Otc_USA::processLink);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
private static void processLink(Elements ul){
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
//                        System.out.println(line);
                        processConsolidate(line);
                        line = bufferedReader.readLine();
                    }
                }
            }
        }
        System.out.println("----------------------------------------------------------------------------------------------");

    }


    private static void processConsolidate(String line) {
        System.out.println(line);
    }

    private static void processMarkets(String line) {
        System.out.println(line);
    }

}
