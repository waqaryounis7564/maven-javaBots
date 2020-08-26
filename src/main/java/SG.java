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

        String url = "https://api.sgx.com/announcements/v1.1/?periodstart=20200725_160000&periodend=20200826_155959&cat=ANNC&sub=ANNC13&pagestart=0&pagesize=20";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("authorizationtoken", "842FLqEItEE94BX0k7pXT0zXn4l4RCVmUwuK9eXyQUk5FWySkcgvM7/ouIxtpYHPRrOHcTcVoBMOcI/Ynst/aw5AF3+efJCeR7B7OnqgJCeZZuHrzIamEuJNK0/zgZCq");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }
        response.append(inputLine);
        in.close();
    }
}





