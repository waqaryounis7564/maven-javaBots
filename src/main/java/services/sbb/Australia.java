package services.sbb;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Australia {
    public static void scrapeData() throws IOException {
        URL url = new URL("https://asx.api.markitdigital.com/asx-research/1.0/markets/announcements?itemsPerPage=1000&summaryCountsDate=2020-11-23&includeFacets=true");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Bearer 83ff96335c2d45a094df02a206a39ff4");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String output;

        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }

        in.close();
        JSONObject jsonObject = new JSONObject(response.toString());
        JSONArray jsonArray = jsonObject.
                getJSONObject("data").
                getJSONArray("items");

     for (int i=0;i<jsonArray.length();i++){
         String string = jsonArray.getJSONObject(i).
                 getJSONArray("companyInfo")
                 .getJSONObject(0).
                         getString("symbol");
         System.out.println(string);
     }

    }
}


//
//import com.gargoylesoftware.htmlunit.HttpMethod;
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.WebRequest;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//public class Australia {
//    public static void scrapeData(){
//try(WebClient webClient=new WebClient()){
//    webClient.getOptions().setUseInsecureSSL(true);
//    webClient.getOptions().setCssEnabled(false);
//    webClient.getOptions().setJavaScriptEnabled(true);
//    webClient.getOptions().setThrowExceptionOnScriptError(false);
////    webClient.getCookieManager().setCookiesEnabled(true);
//    WebRequest requestSettings = new WebRequest(new URL("https://www2.asx.com.au/markets/trade-our-cash-market/announcements"), HttpMethod.GET);
//    HtmlPage htmlPage=webClient.getPage(requestSettings);
//    webClient.waitForBackgroundJavaScript(100000);
//    String requestBody = requestSettings.getRequestBody();
//    System.out.println(requestBody);
////    for (Object object : objects) {
////        System.out.println(object.toString());
////    }
////    String response=htmlPage.asXml();
////    Document document = Jsoup.parse(response);
////    Elements rows = document.select("#markets_announcements > div > div:nth-child(6) > table > tbody>tr");
////    System.out.println(rows);
//
//} catch (IOException e) {
//    e.printStackTrace();
//}
//    }
//}
