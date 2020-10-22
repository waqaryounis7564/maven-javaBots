package services;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Romania {
    public static void scrapeData() throws IOException {
        int jumpOfTen = 30;
        int previousRecords;
        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            final HtmlPage homePage = webClient.getPage("https://www.bvb.ro/FinancialInstruments/SelectedData/CurrentReports");
            for (previousRecords = 1; previousRecords <= jumpOfTen; previousRecords++) {
                getRecords(homePage);
                DomElement prevBtn = homePage.querySelector("#lbleft");
                HtmlPage prevRecords=prevBtn.click();
                getRecords(prevRecords);

            }

        }

    }
    private static void getRecords(HtmlPage page) throws IOException {
        processPage(page.asXml());
        while (true) {
            DomElement nextBtn = page.querySelector("#gvv_next");
            HtmlPage nxtPage = nextBtn.click();
            processPage(nxtPage.asXml());
            String btnClass = nextBtn.getAttribute("class");
            if (btnClass.contains("disabled")) break;
        }
    }

    private static void processPage(String response) {
        Document document = Jsoup.parse(response);
        if (document == null)
            return;
//   Document document = Jsoup.parse(String.valueOf(response));
        Elements tables = document.getElementsByTag("table");
        if (tables == null || tables.size() < 2)
            return;
        Element table = tables.get(2);
        Elements announcements = table.getElementsByTag("tr");
        for (Element announcement : announcements) {
            Elements columns = announcement.getElementsByTag("td");
            if (columns.size() != 6)
                continue;
            String strTicker = columns.first().getElementsByTag("strong").first().text().trim();
            System.out.println(strTicker);
            //  String strISIN = announcement.getElementsByTag("p").text();
            String strCompanyName = columns.get(1).text();
            System.out.println(strCompanyName);
            String strAnnsDesc = columns.get(2).text();
            if (strAnnsDesc.isEmpty() && columns.get(2).getElementsByTag("input").size() > 0)
                strAnnsDesc = columns.get(2).getElementsByTag("input").first().attr("value");
            if (strAnnsDesc.isEmpty())
                System.out.println(announcement.toString());
            String strReleasedDate = columns.get(3).text();
            System.out.println(strReleasedDate);
            //  String strHeadline = announcement.getElementsByTag("td").get(4).text();
            String href = "";
            if (columns.get(5).getElementsByTag("a").size() > 0)
                href = columns.get(5).getElementsByTag("a").first().attr("href");
            if (!href.isEmpty() && href.length() > 1) {
                String savingUrl = "http://www.bvb.ro" + href;
                System.out.println(savingUrl);
            }
        }
    }
}