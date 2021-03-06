package services.representitives.comittes;

import models.RepComittee;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;

public class Appropriations {
public  static void scrape() throws IOException {
    Document document = Jsoup.connect("https://appropriations.house.gov/about/membership").get();
    Elements data = document.select("#block-system-main > div > div > div > div.center-wrapper > div.panel-col-first.panel-panel > div > div:nth-child(1)");
    RepComittee member=new RepComittee();

    Elements democaratic = document.getElementsByClass("each-member");
    democaratic.forEach(t-> System.out.println(t.text()));

}
}
