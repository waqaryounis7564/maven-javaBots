package services.representitives.comittes;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Agriculture {

    public static void scrape() throws IOException {
        Document document = Jsoup.connect("https://agriculture.house.gov/about/members.htm").get();
        Elements minorityMembers = document.select("#twoColLeft > div > ul>li");
        System.out.println("Minority");
        minorityMembers.forEach(li -> System.out.println(li.text()));
        System.out.println("-------------------------------------------------");
        System.out.println("majority");

        Elements majority = document.select("h3");
        majority.forEach(h-> System.out.println(h.text()));

    }
}
