package services.representitives.comittes;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import services.common.Child;

import java.io.IOException;

public class Agriculture {


    public void scrape() throws IOException {

        Document document = Jsoup.connect("https://cha.house.gov/subcommittees/joint-committee-congress-library-116th-congress").get();
        Elements minorityMembers = document.select("#twoColLeft > div > ul>li");
//        System.out.println("Minority");
//        minorityMembers.forEach(li -> System.out.println(li.text()));
//        System.out.println("-------------------------------------------------");
//        System.out.println("majority");
//
//        Elements majority = document.select("h3");
//        majority.forEach(h-> System.out.println(h.text()));

    }
}
