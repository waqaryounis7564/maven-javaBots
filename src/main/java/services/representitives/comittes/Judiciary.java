package services.representitives.comittes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Judiciary {
    public static void scrape() throws IOException {
        Document document = Jsoup.connect("https://judiciary.house.gov/about/members.htm").get();
        Elements leaders = document.select("#ctl00_ctl27_ctl00_Text > div.leadership.democratic>div");
        leaders.forEach(m -> System.out.println(m.select("div:not(ul)").text().replace("facebook","").replace(" twitter","").replace("instagram","")));

        System.out.println("------------------");

        System.out.println("DEmocratic");
        Elements demoCratic=document.select("#ctl00_ctl27_ctl00_Text > div.member-list.democratic>div");
        demoCratic.forEach(t-> System.out.println(t.text().replace("facebook","").replace(" twitter","").replace("instagram","")));
String  ranking=document.select("#ctl00_ctl27_ctl00_Text > div.leadership.republican > div").text().replace("facebook","").replace(" twitter","").replace("instagram","");
        System.out.println(ranking);
        System.out.println("Republican");
        Elements republican=document.select("#ctl00_ctl27_ctl00_Text > div.member-list.republican>div");
        republican.forEach(t-> System.out.println(t.text().replace("facebook","").replace(" twitter","").replace("instagram","")));


    }

}
