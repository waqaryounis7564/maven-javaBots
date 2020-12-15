package services;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Testable {

    public static void scrape() throws IOException {

        Document document = Jsoup.connect("https://www.sec.gov/Archives/edgar/data/1605070/000117266120002205/0001172661-20-002205-index.htm").get();
        String cikNumber = document.body().select("#filerDiv > div.companyInfo > span > a").text().replace("(see all company filings)", "").trim();
        String reportDate = document.body().select("#formDiv > div.formContent > div:nth-child(2) > div:nth-child(2)").text().trim();
        String acceptedDate = document.body().select("#formDiv > div.formContent > div:nth-child(1) > div:nth-child(4)").text().trim();
        String effectivnessDate = document.body().select("#formDiv > div.formContent > div:nth-child(2) > div:nth-child(4)").text().trim();

        System.out.println(document.body().select("#filerDiv > div.companyInfo > span > a").text());
    }
}




