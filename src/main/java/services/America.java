package services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.ParameterUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class America {
    public static void scrapeData()  {
        Stream.of("20-f","10-q","10-k").forEach(keyWord -> getHistory(keyWord,2020,2020));
    }

    private static void getHistory(String keyWord, int yearStart, int yearEnd) {
        int start = 1;
        while (true) {
            String url = "https://www.sec.gov/cgi-bin/srch-edgar?text=" + keyWord + "&start=" + start + "&count=100&first=" + yearStart + "&last=" + yearEnd;
            System.out.println("start");
            Document document=null;
            try{
             document = Jsoup.connect(url).get();}
            catch (IOException ex){
                System.out.println(ex.getMessage());
            }
            Objects.requireNonNull(document);
            int pageCompleted = document.select("body>div>table>tbody>tr").size();
            if (pageCompleted <= 1) break;
            System.out.println("no of rows : " + pageCompleted);
            Elements rows = document.getElementsByTag("div").first().getElementsByTag("table").get(1).getElementsByTag("tr");
            rows.forEach(America::extractData);
            System.out.println(url);
            start += 100;
            System.out.println("end");
        }

    }
    private static String parsedDate(String date){
        String thisFormat = "MM/dd/yyyy";
        String requiredFormat = "yyyy-MM-dd";
        return ParameterUtils.getDateInYourFormat(date, thisFormat, requiredFormat);
    }
    private static void extractData(Element row){
        String companyName=row.select("td:nth-child(2)").text();
        if(companyName.contains("Company")) return;
        System.out.println(companyName);
        String formType=row.select("td:nth-child(4)").text();
        String headline=formType+" - "+companyName;
//        if(Stream.of("nt 10-","nt 20").anyMatch(word->headline.toLowerCase().contains(word))) return;
        if(headline.toLowerCase().startsWith("nt")) return;
        String date=row.select("td:nth-child(5)").text();
        if(date.equals(""))return;
        String  postUrl=row.select( "td:nth-child(3) > a:nth-child(2)").attr("href");
        String fillingDate=parsedDate(date);
        String savingUrl="http://www.sec.gov"+postUrl;
        System.out.println(headline);
        System.out.println(fillingDate);
        System.out.println(savingUrl);
        System.out.println("--------------------------------------");
    }
}
