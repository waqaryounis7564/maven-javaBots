package services.representitives.comittes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Education_and_Labor {
    public static void scrape() throws IOException {
        String name="waqar";
        Document document = Jsoup.connect("https://edlabor.house.gov/about/membership").get();
        Elements democrats = document.select("#main_column > div.row.members > div > div:nth-child(2) > div > ul>li");
        democrats.forEach(m-> System.out.println(m.text()));
        System.out.println("-----------------------------------------------");
        Elements minorty = document.select("#main_column > div.row.members > div > div:nth-child(6) > div > ul>li");
        minorty.forEach(m-> System.out.println(m.text()));

    }

}
