package services.representitives.comittes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Financial_Services {
    public static void scrape() throws IOException {
        Document document = Jsoup.connect("https://financialservices.house.gov/about/committee-membership.htm").get();
        Elements data = document.select("#ctl00_ctl24_ctl00_Text > table > tbody>tr");
        data.forEach(elm->{
            Elements select = elm.select("td:nth-child(1)");
            Elements select1 = elm.select("td:nth-child(2)");
        });





    }

}
