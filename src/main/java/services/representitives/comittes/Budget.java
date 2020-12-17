package services.representitives.comittes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Budget {
    public static void scrape() throws IOException {
        Document document = Jsoup.connect("https://budget.house.gov/about/membership").get();
        Elements mixMembers = document.getElementsByClass("each-member-name");
        mixMembers.forEach(m-> System.out.println(m.text()));
    }
}
