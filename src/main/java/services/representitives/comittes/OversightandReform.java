package services.representitives.comittes;

import netscape.javascript.JSUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OversightandReform {
    public static void scrape() throws IOException {
        Document document = Jsoup.connect("https://www.pensions.senate.gov/content/members").get();
        Elements member1 = document.select("#main_column > section > div:nth-child(1) > div:nth-child(1)");
        Elements member2 = document.select("#main_column > section > div:nth-child(1) > div:nth-child(2) > aside > div.membership__aside-row.row");
        List<Elements> data = new ArrayList<Elements>();
        data.add(member1);
        data.add(member2);
        String prefixUrl = "https://modernizecongress.house.gov";
        if (member1.isEmpty() && member2.isEmpty()) {
//            logger.error("something went wrong with css-Query");
        } else {
            int index = 0;
            for (Elements elm : data) {
                String string = elm.select("a").attr("Style");
                int start = string.indexOf("'");
                int last = string.lastIndexOf("'");
                String name = elm.select("div.membership__bio > div").text();
                String rank = (index == 0) ? "Chairperson" : "Vice Chair";
                String state = elm.select("p.membership__district").text();
                String imageUrl = prefixUrl + string.substring(start + 1, last);
                index = 1;
//                committeeRepo.save(
//                        insertData(imageUrl, name, state, rank, committee, Party.DEMOCRATIC.name(), url)
//                );
            }


        }


        Elements members = document.select("#main_column > section > div.membership__row.row > div:nth-child(1) > aside > ul>li");
        if (members.isEmpty()) {
//            logger.error("unable to get Democratic members list");
            return;
        }
        members.forEach(elm -> {
            String string = elm.select("div.membership__figure-image").attr("Style");
            int start = string.indexOf("'");
            int last = string.lastIndexOf("'");
            String imageUrl = prefixUrl + string.substring(start + 1, last);
            String repName = elm.select("a:nth-child(2) > div").text();
            String repState = elm.select("p").text();
//            committeeRepo.save(
//                    insertData(imageUrl, repName, repState, "", committee, Party.DEMOCRATIC.name(), url)
//            );
        });
    }

}


