import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
        downloadXls("https://www.londonstockexchange.com/news-article/DAL/dalata-hotel-group-plc-holding-s-in-company/14875315");
    }

    private static void downloadXls(String link) throws IOException {
        try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setRedirectEnabled(true);
            HtmlPage page = webClient.getPage("https://www.londonstockexchange.com/news-article/DAL/dalata-hotel-group-plc-holding-s-in-company/14875315");
            Thread.sleep(5000);
            String javaScriptCode = "return arguments[0].shadowRoot";
            DomNode domNode = page.querySelector("#news-article-content > div.news-article-content-wrapper > div.news-article-content-body");
            Object javaScriptResult =  page.executeJavaScript("return arguments[0].shadowRoot", String.valueOf(domNode),1).getJavaScriptResult();
            System.out.println(javaScriptResult);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void processLine(String line) {
        System.out.println(line);
    }
}


