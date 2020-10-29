package services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.ParameterUtils;

import java.io.IOException;

public class America {
    public static void scrapeData() throws IOException {
        getHistory("20-f", 2020, 2020);

    }

    private static void getHistory(String keyWord, int yearStart, int yearEnd) throws IOException {
        int start = 1;
        while (true) {
            String url = "https://www.sec.gov/cgi-bin/srch-edgar?text=" + keyWord + "&start=" + start + "&count=100&first=" + yearStart + "&last=" + yearEnd;
            System.out.println("sout");
            Document document = Jsoup.connect(url).get();
            int pageCompleted = document.select("body>div>table>tbody>tr").size();
            System.out.println("no of rows : " + pageCompleted);
            Elements rows = document.getElementsByTag("div").first().getElementsByTag("table").get(1).getElementsByTag("tr");
            for(Element row :rows){
                String companyName=row.select("td:nth-child(2)").text();
                if(companyName.contains("Company")) continue;
                System.out.println(companyName);
                String formType=row.select("td:nth-child(4)").text();
                String headline=formType+" - "+companyName;
                System.out.println(headline);
                String date=row.select("td:nth-child(5)").text();
                if(date.equals(""))continue;
                String  postUrl=row.select( "td:nth-child(3) > a:nth-child(2)").attr("href");
                String fillingDate=parsedDate(date);
                String savingUrl="https://www.sec.gov"+postUrl;
                System.out.println(fillingDate);
                System.out.println(savingUrl);
                System.out.println("--------------------------------------");



            }

            System.out.println(url);
            start += 100;


            if (pageCompleted <= 1) break;
            System.out.println("end");
        }

    }
    private static String parsedDate(String date){
        String thisFormat = "MM/dd/yyyy";
        String requiredFormat = "yyyy-MM-dd";
        return ParameterUtils.getDateInYourFormat(date, thisFormat, requiredFormat);
    }
}
