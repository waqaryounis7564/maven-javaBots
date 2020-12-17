package services.representitives.comittes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Foreign_Affairs {
    public static void scrape() throws IOException {
        Document document = Jsoup.connect("https://foreignaffairs.house.gov/members").get();
        Elements majority =document.select("#group_023196f4-9b03-4d07-abcb-1bc826100f7c > div > div>div>div>h3");
        majority.forEach(m-> System.out.println(m.text()));
        System.out.println("--------------------------------------------");
        Elements minority =document.select("#group_de388de1-82de-404f-bf5a-9ac3d4847497 > div > div>div>div>h3");
        minority.forEach(m-> System.out.println(m.text()));





    }

}
