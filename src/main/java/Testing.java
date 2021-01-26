
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;


public class Testing {
    public static void scrapeData() throws IOException {
        Connection.Response homePage = Jsoup.connect("https://www.sec.gov/cgi-bin/srch-edgar?text=11%2F07%2F2020+AND+13F-*&first=2020&last=2020").
                method(Connection.Method.GET).
                execute();
        Document document = Jsoup.parse(homePage.body());

        if (document.select("body > div > table > tbody").size() == 0) System.out.println("there is no record");
        else {
            String count = document.select("body > div > b:nth-child(3)").text();
            int counte = Integer.parseInt(count);
            System.out.println("total pages are :" + (int) (Math.ceil(counte / 80.0)));
            System.out.println("total records are:" + count);
        }
    }

}





