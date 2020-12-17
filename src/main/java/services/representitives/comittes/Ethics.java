package services.representitives.comittes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Ethics {
    public static void scrape() throws IOException {
        Document document = Jsoup.connect("https://ethics.house.gov/about/committee-members").get();
        Elements democrats =document.select("#member_republicans>p");
        democrats.forEach(m-> System.out.println(m.text()));
        System.out.println("-------------------------------");
        Elements republican =document.select("#member_democrats>p");
        republican.forEach(m-> System.out.println(m.text()));
        System.out.println(document.select("#block-system-main > div > div > div > div > div > div > div > div > div > div > p:nth-child(3)").text());




    }
}
