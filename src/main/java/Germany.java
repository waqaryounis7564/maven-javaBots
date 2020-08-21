import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class Germany {
    public static void scrape() throws IOException {
        downloadPage();

    }

    private static void downloadPage() throws IOException {
        String url = "https://www.bundesanzeiger.de";
        try (WebClient webClient = new WebClient()) { //BrowserVersion.FIREFOX_60
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(true);

            final HtmlPage page = webClient.getPage("https://www.bundesanzeiger.de");
            DomElement menu = page.querySelector("#item1 > li:nth-child(1) > a");
            HtmlPage mainPage1 = menu.click();
//            System.out.println(pg.asXml());
            DomElement mainPage2 = mainPage1.querySelector("#area7 > div > div > div > div:nth-child(7) > label");
//            #area7 > div > div > div > div:nth-child(7) > label
//            System.out.println(nm.asXml());
            HtmlPage mainPage3 = mainPage2.click();
            DomElement searchBtn = mainPage3.getElementByName("search-button");
//           System.out.println(bb.);
            HtmlPage annsPage1 = searchBtn.click();
//            System.out.println(annsPage.asXml());

            List<Object> firstAnn = annsPage1.getByXPath("/html/body/div[1]/section[2]/div/div/div/div/div[6]/div[2]/div[3]/div/a");
//
//
            DomElement firstAnchor = (DomElement) firstAnn.get(0);
            HtmlPage result = firstAnchor.click();
            //todo handle scenrio when anns of that day is not upload and get not found Error in german.

            Document document = Jsoup.parse(result.asXml());
            List<Object> tb = result.getByXPath("/html/body/div[1]/section/div/div/div/div/div[2]/div[2]");

            DomElement rowDiv = (DomElement) tb.get(0);

            System.out.println("cc :"+ rowDiv.asXml());
            System.out.println("--------------------");
            System.out.println(document.select("table"));
// 40 is hard coded ** we get total page multiply it with 20 to get total anns for loop
           for(int i=0;i<40;i++){
               List<Object> nxtBtn = result.getByXPath("/html/body/div[1]/section/div/div/div/div/div[1]/div[3]/a");

               DomElement btn = (DomElement) nxtBtn.get(0);
               HtmlPage newResultPg = btn.click();
               Document doc = Jsoup.parse(newResultPg.asXml());
               System.out.println(doc.select("table"));
               System.out.println("*****************************");
               result= newResultPg;
           }





//            HtmlPage result=ko.click();
//            System.out.println(result.asXml());
            //            HtmlPage jo = ko.click();
//            System.out.println(jo.asXml());
//            DomElement btn = pg.querySelector("#area7 > div > div > div > div:nth-child(7) > label > font > font");
//            System.out.println(btn);
//            btn.setNodeValue("Capital market / short sales");
//            DomElement np = pg.querySelector("#id299");
//            HtmlPage nh = np.click();


//            System.out.println(nh.asXml());
        }
    }
}
