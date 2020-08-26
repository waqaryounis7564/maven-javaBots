import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SG {
    public static void scrape() throws IOException {

        String url = "https://www.sgx.com/securities/company-announcements?ANNC=ANNC13&from=20200726&to=20200826&page=1&pagesize=100";
        try (WebClient webClient = new WebClient()) {
//            URL ur = new URL(url);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
//           WebRequest requestSettings = new WebRequest(ur, HttpMethod.GET);
            final HtmlPage page = webClient.getPage(url);
//            Document doc = Jsoup.parse(page.getWebResponse().getContentAsString());
            webClient.waitForBackgroundJavaScript(20000);
            String result = page.asXml();
            Document res = Jsoup.parse(result);
            System.out.println(res);

        }
    }

}



