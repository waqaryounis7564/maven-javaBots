package services.representitives.comittes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HouseAdministration {
    public static void scrape() throws IOException {
        Document document = Jsoup.connect("https://cha.house.gov/about/membership").get();
        System.out.println("DEMOCRATIC");
        Elements seniorDemoratic = document.select("#block-system-main > div > div > div > div.center-wrapper > div.panel-col-first.panel-panel > div > div:nth-child(1)");
        Elements vicePerson=document.select("#block-system-main > div > div > div > div.center-wrapper > div.panel-col-first.panel-panel > div > div:nth-child(3) > div > div > div > div > div > div > div");
        Elements democractic = document.select("#block-system-main > div > div > div > div.center-wrapper > div.panel-col-first.panel-panel > div > div:nth-child(5) > div > div > div>div>div");
        seniorDemoratic.stream().limit(2).forEach(m -> System.out.println(m.text()));
        System.out.println(vicePerson.text());
        democractic.forEach(m -> System.out.println(m.text()));
        System.out.println("--------------------------------------------");
        System.out.println("REPUBLICAN");
        Elements rankingMember=document.select("#block-system-main > div > div > div > div.center-wrapper > div.panel-col-last.panel-panel > div > div:nth-child(1)");
        Elements minority = document.select("#block-system-main > div > div > div > div.center-wrapper > div.panel-col-last.panel-panel > div > div:nth-child(3)>div>div>div>div");
        System.out.println(rankingMember.text());
        minority.forEach(m -> System.out.println(m.text()));


    }

}
