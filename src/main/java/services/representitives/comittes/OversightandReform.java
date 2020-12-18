package services.representitives.comittes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class OversightandReform {
    public static void scrape() throws IOException {
        Document document = Jsoup.connect("https://oversight.house.gov/members").get();
        Elements democrats = document.select("#block-system-main > div > div > div > div.panel-col-bottom.panel-panel > div > div:nth-child(1) > div > div > div > table > tbody>tr>td");
        Elements leader=document.select("#block-system-main > div > div > div > div.center-wrapper>div");

        leader.forEach(y-> System.out.println(y.text()));

        System.out.println("DEmocratic");
        democrats.forEach(m -> System.out.println(m.text().replace("Official Website »","")));

        System.out.println("------------------");
        System.out.println("Republican");
        Elements republican=document.select("#block-system-main > div > div > div > div.panel-col-bottom.panel-panel > div > div:nth-child(3) > div > div > div > table > tbody>tr>td");
        republican.forEach(m -> System.out.println(m.text()));


    }

}
