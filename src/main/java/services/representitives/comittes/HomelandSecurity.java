package services.representitives.comittes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HomelandSecurity {

    public static void scrape() throws IOException {
        Document document = Jsoup.connect("https://homeland.house.gov/about/membership/").get();
        System.out.println("DEMOCRATIC");
        Elements democractic = document.select("#main_column > section.majority > div > aside > div>div");
        democractic.forEach(m -> System.out.println(m.text()));
        System.out.println("--------------------------------------------");
        System.out.println("REPUBLICAN");
        Elements minority = document.select("#main_column > section.minority > div > aside > div>div");
        minority.forEach(m -> System.out.println(m.text()));


    }

}
