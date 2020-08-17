import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Testing {
    public static void scrapeData() throws IOException {
        Document document = Jsoup.connect("https://www.congress.gov/members?pageSize=250&q={%22congress%22:[%22116%22,%22115%22,%22114%22,%22113%22,112],%22chamber%22:%22Senate%22}&searchResultViewType=expanded\n").get();
        Elements anchors = document.select("ol>li>span>a");
        String url = "https://bioguideretro.congress.gov/Home/MemberDetails?memIndex=";
        List<Senator> senators = new ArrayList<>();
        for (Element li : anchors) {
            Senator senator = new Senator();
            String name = li.text();
            String id = li.attr("href").split("/")[5];
            senator.setName(name);
            senator.setUrl(url + id);
//            setBio(senator,senator.getUrl());
            senators.add(senator);

        }
senators.forEach(t-> {
    try {
        setBio(t,t.getUrl());
    } catch (IOException e) {
        e.printStackTrace();
    }
});

    }
    private static void setBio(Senator senator, String url) throws IOException {
        Document document1=Jsoup.connect(url).get();
        String p=document1.select("biography").text();
        senator.setBioGraphy(p);

    }
}
