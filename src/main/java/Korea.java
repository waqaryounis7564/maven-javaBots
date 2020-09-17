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
import java.text.ParseException;
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
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);

        }
        in.close();
        return response.toString();

    }

    public static void scrapeData() throws IOException, ParseException {
        String url = "";
        int maxDays = 6;
        int maxPage = 5;
        for (int day = 0; day <= maxDays; day++) {
            String dateStart = new SimpleDateFormat("yyyy-MM-dd").format(ParameterUtils.getDateBeforeDays(new Date(), day));
            for (int page = 1; page <= maxPage; page++) {
                url = "https://kind.krx.co.kr/disclosure/todaydisclosure.do?method=searchTodayDisclosureSub&currentPageSize=100&pageIndex=" + page + "&orderMode=0&orderStat=D&marketType=&forward=todaydisclosure_sub&searchMode=&searchCodeType=&chose=S&todayFlag=Y&repIsuSrtCd=&selDate=" + dateStart + "&searchCorpName=";
                Document document = Jsoup.parse(downloadPage(url));
                int tableIsEmpty = document.select("tbody>tr").size();
                if (tableIsEmpty == 1) continue;
                processDocument(document, dateStart);
            }
        }
    }

    private static void processDocument(Document document, String date) throws ParseException {
        System.out.println(date);
        Elements rows = document.select("tbody>tr");
        String prefixUrl = "http://kind.krx.co.kr/common/disclsviewer.do?method=search&acptno=";
        String postFixUrl = "&docno=&viewerhost=&viewerport=";
        String dateTime = "";
        String companyName = "";
        String heading = "";
        String rnsId = "";
        String savingUrl = "";
        String releasedTime = "";
        for (Element row : rows) {
            dateTime = row.select("td.first.txc").text();
//           System.out.println("date: " + date + dateTime);
            companyName = row.select("#companysum").text();
//            System.out.println("companyName: " + companyName);
            heading = row.select("td:nth-child(3) > a").text();
//            System.out.println("Heading: " + heading);
            rnsId = row.select("td:nth-child(3) > a").attr("onClick").split("'")[1];
            savingUrl = prefixUrl + rnsId + postFixUrl;
            releasedTime = date + " " + dateTime;
//            System.out.println("popUp : " + savingUrl);
            if (heading.contains("자기주식")) {
//                System.out.println("company Name is :"+companyName);
//                System.out.println(savingUrl);
                KoreaDisclosure disclosure = new KoreaDisclosure();
                disclosure.setCompanyName(companyName);
                disclosure.setHeadline(heading);
                disclosure.setRnsId(rnsId);
                disclosure.setSavingUrl(savingUrl);
                Date parsedTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(releasedTime);
                dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(parsedTime);
                disclosure.setDateTime(dateTime);
                System.out.println(disclosure.getHeadline());

            }

            System.out.println("********************************************************");
        }


        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private static void saveToDB(KoreaDisclosure disclosure) {
//        if (!botsRepo.checkIsUrlAlreadyExist(disclosure.getSavingUrl(), countryId)) {
//            botsRepo.insertAnnouncement(disclosure.getHeadline(),disclosure.getDateTime(),14,3,disclosure.getSavingUrl(),countryId,disclosure.getCompanyName(),disclosure.getRnsId())
    }
}
