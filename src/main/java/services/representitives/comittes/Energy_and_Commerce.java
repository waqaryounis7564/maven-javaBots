package services.representitives.comittes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Energy_and_Commerce {
    public void scrape() throws IOException {
        Document document = Jsoup.connect("https://energycommerce.house.gov/about-ec/membership").get();
        System.out.println(this.getClass().getSimpleName());

        System.out.println("DEMOCRATIC");
        Elements democrats = document.select("#block-system-main > div > div > div > div.panel-panel.panel-col-middle > div > div:nth-child(1) > div > div > div>div");
        democrats.forEach(m -> System.out.println(m.text()));
        System.out.println("-----------------------------------------------");
        System.out.println("REPUBLICAN MEMBERS");

        Elements republican = document.select("#block-system-main > div > div > div > div.panel-panel.panel-col-middle > div > div:nth-child(5) > div > div > div>div");
        republican.forEach(m-> System.out.println(m.text()));

    }
}
