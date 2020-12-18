package services.representitives.comittes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NaturalResources {
    public static void scrape() throws IOException {
        Document document = Jsoup.connect("https://naturalresources.house.gov/about/our-members").get();
        Elements democrats = document.select("#main_column > div.row.members > div:nth-child(1) > div > div > ul>li");


        System.out.println("DEmocratic");
        democrats.forEach(m -> System.out.println(m.text().replace("Official Website »","")));

        System.out.println("------------------");
        System.out.println("Republican");
        Elements republican=document.select("#main_column > div.row.members > div:nth-child(2) > div > div > ul>li");
        republican.forEach(m -> System.out.println(m.text().replace("Official Website »","")));


    }

}
