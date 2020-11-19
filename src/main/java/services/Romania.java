package services;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;

public class Romania {
    public static void scrapeData() {
        Connection.Response response = null;
        String path = "https://www.bvb.ro/FinancialInstruments/SelectedData/CurrentReports";
        try {
            response = Jsoup.connect(path)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .timeout(10000)
                    .execute();
            processPage(response.parse());

        } catch (RuntimeException | IOException ex) {
            System.out.println(ex.getMessage());

        }
    }


    private static void processPage(Document document) throws RuntimeException {
        Elements announcements = document.select("#gvv > tbody>trT");
        if (announcements.size()==0) throw new RuntimeException("NO ROWS AVAILABLE FOR ROMANIA");
        for (Element announcement : announcements) {
            if (announcement.select("td").size() != 6) continue;
            String strTicker = announcement.select("td:nth-child(1)").text().trim();
            String strCompanyName = announcement.select("td:nth-child(2)").text().trim();
            String strAnnsDesc = "";
            if (announcement.select("td:nth-child(3)>span").size() > 0) {
                strAnnsDesc = announcement.select("td:nth-child(3)>span").text();
            } else {
                strAnnsDesc = announcement.select("td:nth-child(3)>input").attr("value").trim();
            }
            String strReleasedDate = announcement.select("td:nth-child(4)").text().trim();
            String href = "";
            if (announcement.select("td:nth-child(6)>a").size() > 2) {
                href = announcement.select("td:nth-child(6)>a").get(1).attr("href");
            } else {
                href = announcement.select("td:nth-child(6)>a").attr("href");
            }
            String savingUrl = "";
            if (href.contains("http://www.bvb.ro")) {
                savingUrl = href;
            } else {
                savingUrl = "http://www.bvb.ro" + href;
            }
            if (iskeyWordAvailable(strAnnsDesc)) {
                System.out.println(savingUrl + "     " + strAnnsDesc + "   " + strTicker);

            }
            if (strTicker.isEmpty() && strCompanyName.isEmpty() && strAnnsDesc.isEmpty() && strReleasedDate.isEmpty())
                throw new RuntimeException("no response get from all columns of Romania");
        }
    }

    private static boolean iskeyWordAvailable(String desc) {
        String[] keys = {
                "share dealing",
                "own securities",
                "repurchase of shares",
                "buyback",
                "own shares",
                "treasury share",
                "treasury stock",
                "buy-back",
                "buying back",
                "re-buying",
                "share repurchase",
                "acquisition of own shares",
                "acquisition of treasury shares",
                "redemption",
                "repurchase",
                "treasury",
//                "buy"
        };
        return Arrays.stream(keys).anyMatch(key -> desc.toLowerCase().contains(key.toLowerCase()));
    }
}