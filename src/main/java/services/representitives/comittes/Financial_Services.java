package services.representitives.comittes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Financial_Services {
    public static void scrape() throws IOException {
        Document document = Jsoup.connect("https://financialservices.house.gov/about/committee-membership.htm").get();
        Elements mixMembers =document.select("#ctl00_ctl22_ctl00_Text > table > tbody>tr>td");
        mixMembers.forEach(m-> System.out.println(m.text()));





    }

}
