package services.sbb;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.ParameterUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Australia {
    private static ArrayList<String> keyWords = new ArrayList<>(Arrays.asList(
            "buy-back",
            "BUYBACK",
            "FORM 484",
            "FORM-484",
            "3C",
            "3D",
            "3E",
            "3F",
            "APPENDIX 3E",
            "APPENDIX-3E",
            "CAPITAL RETURN",
            "cancellation of shares",
            "Share Cancellation",
            "Ordinary Shares"
    ));


    public static void scrapeData() throws IOException {
        URL url = new URL("https://asx.api.markitdigital.com/asx-research/1.0/markets/announcements?itemsPerPage=1000&summaryCountsDate=2020-11-23&includeFacets=true");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Bearer 83ff96335c2d45a094df02a206a39ff4");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("GET");
        int statusCode = conn.getResponseCode();
        if (statusCode != 200)
            throw new RuntimeException(" NO RESPONSE AVAILABLE FOR AUSTRALIA, STATUS CODE" + statusCode);
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
        Iterator<Object> iterator = jsonArray.iterator();
        int count=1;
        while (iterator.hasNext()) {
            JSONObject obj = (JSONObject) iterator.next();
            try {
                String ticker = obj
                        .getJSONArray("companyInfo")
                        .getJSONObject(0)
                        .getString("symbol");
                String companyName = obj
                        .getJSONArray("companyInfo")
                        .getJSONObject(0)
                        .getString("displayName");
                String date = obj.getString("date");
                String documentKey = obj.getString("documentKey");
                String headLine = obj.getString("headline");
                String annsUrl = "https://cdn-api.markitdigital.com/apiman-gateway/ASX/asx-research/1.0/file/" + documentKey + "?access_token=83ff96335c2d45a094df02a206a39ff4";
                date = ParameterUtils.getDateInYourFormat(date, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd HH:mm:ss");
                count++;
                System.out.println(
                        MessageFormat.format("{0},{1},{2},{3}::::{4}::::::", ticker, headLine, annsUrl, documentKey,count));

            } catch (JSONException ex) {
                ex.getCause();
            }
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
