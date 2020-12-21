package services;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.MessageFormat;

public class Testable {

    public static void scrape() throws IOException {

        Document document = Jsoup.connect("https://www.sec.gov/Archives/edgar/data/1602730/000160273020000001/xslForm13F_X01/abacus4q19.xml").get();
        Elements rows=document.select("body > table:nth-child(3) > tbody>tr:nth-child(n+4)");
        rows.forEach(r->{
        String nameOfIssuer=r.select("td:nth-child(1)").text();
        String titleOfClass=r.select("td:nth-child(2)").text();
        String cusip=r.select("td:nth-child(3)").text();
        String value=r.select("td:nth-child(4)").text();
        String sshPrnamt=r.select("td:nth-child(5)").text();
        String sshPrnamtType=r.select("td:nth-child(6)").text();
        String call=r.select("td:nth-child(7)").text();
        String investmentDiscretion=r.select("td:nth-child(8)").text();
        String otherManager=r.select("td:nth-child(9)").text();
        String sole=r.select("td:nth-child(10)").text();
        String shared=r.select("td:nth-child(11)").text();
        String none=r.select("td:nth-child(12)").text();
            System.out.println( MessageFormat.format("{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11}",nameOfIssuer,titleOfClass,cusip,value,sshPrnamt,sshPrnamtType,call,investmentDiscretion,otherManager,sole,shared,none)
            );
            });
//        System.out.println(nameOfIssuer);

//body > table:nth-child(3) > tbody > tr:nth-child(4) > td:nth-child(1)
//        System.out.println(document.body().select("#filerDiv > div.companyInfo > span > a").text());
    }
}




