import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Canda {

    public static void scrap() {
        String url = "https://www.iiroc.ca/news/Pages/Short-Sale.aspx";

        try (WebClient webClient = new WebClient()) {
            URL ur = new URL(url);
            WebRequest requestSettings = new WebRequest(ur, HttpMethod.POST);
//            requestSettings.setAdditionalHeader();
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            HtmlPage page = webClient.getPage(requestSettings);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            System.out.println(page.asXml());
//            page.querySelector("#WebPartWPQ3 > table:nth-child(1) > tbody");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
/**
 * public void post() throws Exception
 * {
 * <p>
 * URL url = new URL("YOURURL");
 * WebRequest requestSettings = new WebRequest(url, HttpMethod.POST);
 * <p>
 * requestSettings.setAdditionalHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
 * requestSettings.setAdditionalHeader("Referer", "REFURLHERE");
 * requestSettings.setAdditionalHeader("Accept-Language", "en-US,en;q=0.8");
 * requestSettings.setAdditionalHeader("Accept-Encoding", "gzip,deflate,sdch");
 * requestSettings.setAdditionalHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3");
 * requestSettings.setAdditionalHeader("X-Requested-With", "XMLHttpRequest");
 * requestSettings.setAdditionalHeader("Cache-Control", "no-cache");
 * requestSettings.setAdditionalHeader("Pragma", "no-cache");
 * requestSettings.setAdditionalHeader("Origin", "https://YOURHOST");
 * <p>
 * requestSettings.setRequestBody("REQUESTBODY");
 * <p>
 * Page redirectPage = webClient.getPage(requestSettings);
 * }
 **/

