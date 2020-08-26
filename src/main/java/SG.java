import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class SG {
    public static void scrape() throws IOException {
        String res = download();
        JSONObject obj = new JSONObject(res);
        JSONArray jsonArray = obj.getJSONArray("data");
        if (jsonArray == null)
            return;

        for (int i = 0; i < jsonArray.length(); i++) {
            String headline = jsonArray.getJSONObject(i).getString("title");
            String srcUrl = jsonArray.getJSONObject(i).getString("url");
            String date = jsonArray.getJSONObject(i).getString("submission_date");
            String rnsId = jsonArray.getJSONObject(i).getString("id");
            String company = jsonArray.getJSONObject(i).getJSONArray("issuers").getJSONObject(0).getString("issuer_name");

            System.out.println(headline);
            System.out.println(rnsId);
            System.out.println(company);
            System.out.println(srcUrl);
            System.out.println(date);
            System.out.println("----------");
        }


    }

    private static String download() throws IOException {

        String url = "https://api.sgx.com/announcements/v1.1/?periodstart=20200725_160000&periodend=20200826_155959&cat=ANNC&sub=ANNC13&pagestart=0&pagesize=100";
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
            response.append(inputLine);

        }
        in.close();
        return response.toString();
    }
}





