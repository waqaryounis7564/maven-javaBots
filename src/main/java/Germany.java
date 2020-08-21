import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlMenu;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sun.plugin.dom.html.HTMLButtonElement;

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
            HtmlPage pg = menu.click();
//            System.out.println(pg.asXml());
            DomElement nm = pg.querySelector("#area7 > div > div > div > div:nth-child(7) > label");
//            System.out.println(nm.asXml());
            HtmlPage ff = nm.click();
            DomElement bb = ff.getElementByName("search-button");
//           System.out.println(bb.);
            HtmlPage annsPage = bb.click();
//            System.out.println(annsPage.asXml());

            List<Object> ko = annsPage.getByXPath("/html/body/div[1]/section[2]/div/div/div/div/div[6]/div[2]/div[3]/div/a");
            DomElement no = (DomElement) ko.get(0);
            HtmlPage result = no.click();

            Document document = Jsoup.parse(result.asXml());
            List<Object> tb = result.getByXPath("/html/body/div[1]/section/div/div/div/div/div[2]/div[2]");
            DomElement cc = (DomElement) tb.get(0);

            System.out.println(cc.asXml());
            System.out.println("--------------------");
            System.out.println(document.select("table"));

           for(int i=0;i<40;i++){
               List<Object> nxtBtn = result.getByXPath("/html/body/div[1]/section/div/div/div/div/div[1]/div[3]/a");

               DomElement btn = (DomElement) nxtBtn.get(0);
               HtmlPage vp = btn.click();
               Document doc = Jsoup.parse(vp.asXml());
               System.out.println(doc.select("table"));
               System.out.println("*****************************");
               result=vp;
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
