import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebRequest;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Korea {
    private static String downloadPage(String url) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);

        }
        in.close();
        return response.toString();

    }

    public static void scrapeData() throws IOException {
        String url = "";
        for (int i = 0; i <= 6; i++) {
            String dateStart = new SimpleDateFormat("yyyy-MM-dd").format(ParameterUtils.getDateBeforeDays(new Date(), i));
            for (int j = 1; j <= 5; j++) {
                url = "https://kind.krx.co.kr/disclosure/todaydisclosure.do?method=searchTodayDisclosureSub&currentPageSize=100&pageIndex=" + j + "&orderMode=0&orderStat=D&marketType=&forward=todaydisclosure_sub&searchMode=&searchCodeType=&chose=S&todayFlag=Y&repIsuSrtCd=&selDate=" + dateStart + "&searchCorpName=";
                Document document = Jsoup.parse(downloadPage(url));
                int tableIsEmpty = document.select("tbody>tr").size();
                if (tableIsEmpty == 1) continue;
                processDocument(document, dateStart);
            }
        }
    }

    private static void processDocument(Document document, String date) {
        System.out.println(date);
        Elements rows = document.select("tbody>tr");
        for (Element row : rows) {
            System.out.println("date: " + row.select("td.first.txc").text());
            System.out.println("companyName: " + row.select("#companysum").text());
            System.out.println("Heading: " + row.select("td:nth-child(3) > a").text());

            System.out.println("popUp : ");
            System.out.println("********************************************************");
        }


        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}
