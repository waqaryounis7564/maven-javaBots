package services.representitives.comittes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Armedservices {
    public static void scrape() throws IOException {
        Document document = Jsoup.connect("https://armedservices.house.gov/meet-our-members").get();
        Elements mixMembers = document.getElementsByTag("h3");
        mixMembers.forEach(m-> System.out.println(m.text()));
    }
}
