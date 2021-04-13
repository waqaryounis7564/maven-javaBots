package services.representitives.comittes;

import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Ethics {
    public  void scrape() throws IOException {
        Document document = Jsoup.connect("https://ethics.house.gov/about/committee-members").get();
        Elements democrats = document.select("#member_republicans");
        String[] split = democrats.toString().split("<h3>");
        if (split.length == 3) {
            Document democrat = Jsoup.parse(split[1]);
            Document republican = Jsoup.parse(split[2]);
            democrat(democrat);
            republican(republican);
        }

    }

    private static void democrat(Document document) {
//        document.select("p").forEach(f -> System.out.println( + f.text()));
    }

    private static void republican(Document document) {
//        document.select("p").forEach(f -> System.out.println("repub" + f.text()));
    }
}
