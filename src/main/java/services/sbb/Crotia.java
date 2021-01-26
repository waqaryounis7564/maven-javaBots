package services.sbb;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.ParameterUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crotia {
    private static boolean historic;

    public static void scrape() {
        String url = "https://eho.zse.hr/en/issuer-announcements";
        Document document = null;
        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);

            HtmlPage page = webClient.getPage(url);
            consumeRow(page.asXml());
            DomElement nxtBtn = page.querySelector("#c4 > div > div > div.row.pt-2.pb-4 > div > div > nav > ul > li:nth-child(24) > a");
            while (true) {
                HtmlPage page2 = nxtBtn.click();
                consumeRow(page2.asXml());
             nxtBtn = page2.querySelector("#c4 > div > div > div.row.pt-2.pb-4 > div > div > nav > ul > li:nth-child(24) > a");
                if(historic) break;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void consumeRow(String row) {
        Document document = Jsoup.parse(row);
        String headLine = document.select("#c4 > div > div > div.row.pt-2.pb-4 > div > div > div:nth-child(2) > div > a > h5").text();
        String companyName = headLine.split("-")[0];
        String dateTime = document.select("#c4 > div > div > div.row.pt-2.pb-4 > div > div > div:nth-child(2) > div > small").text();
        try {
            dateTime = ParameterUtils.getDateInStringFromDate(new SimpleDateFormat("dd.MM.yyyy. HH:mm").parse(dateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateEnd(dateTime);
        String url = "https://zse.hr/en/zag" + document.select("#c4 > div > div > div.row.pt-2.pb-4 > div > div > div:nth-child(2) > div > a").attr("href");
        Matcher matcher = Pattern.compile("\\d+").matcher(url);
        String rnsID = matcher.find() ? matcher.group(0) : null;

        System.out.println(MessageFormat.format("{0},{1},{2}",companyName,url,dateTime));
    }

    private static void dateEnd(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsed = null;
        try {
            parsed = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(parsed);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        if (month < 10 && year == 2021) historic = false;
        else historic = (month <= 10);
    }


}
