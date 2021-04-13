package services.representitives.comittes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Foreign_Affairs {
    public static void scrape() throws IOException {
        Document document = Jsoup.connect("https://foreignaffairs.house.gov/members").get();
        Elements majority = document.select("#group_023196f4-9b03-4d07-abcb-1bc826100f7c > div > div>div>div>h3");
        document.select("#group_c8285069-7714-483f-8b41-83b49614fc58 > div > div>div").stream().skip(1)
                .forEach(elm -> {
                    String imgUrl = elm.select("img").attr("src");
                    String repName = elm.select("h3").text();
                    String repState = "";
                });


    }

}
