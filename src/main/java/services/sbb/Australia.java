package services.sbb;


import org.json.JSONArray;
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

public class Australia {
    private static ArrayList<String> keyWords = new ArrayList<>(Arrays.asList(
            "buy-back",
            "Buy-Back",
            "BUYBACK",
            "FORM 484",
            "FORM-484",
            "3C",
            "3D",
            "3E",
            "3F",
            "APPENDIX 3E",
            "Appendix 3E",
            "APPENDIX-3E",
            "CAPITAL RETURN",
            "Cancellation of Shares",
            "cancellation of shares",
            "Share Cancellation",
            "Ordinary Shares"
    ));


    public static void scrapeData() throws IOException {
        URL url = new URL("https://asx.api.markitdigital.com/asx-research/1.0/markets/announcements?itemsPerPage=1000&summaryCountsDate=2020-11-27&includeFacets=true");
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
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            JSONArray objJSONArray = obj.getJSONArray("companyInfo");
            String ticker = "";
            String headLine = "";
            String companyName = "";
            String documentKey = "";
            String date = "";
            String annsUrl = "";
            if (objJSONArray.length() == 0) {
                ticker = obj.getString("symbol");
                headLine = obj.getString("headline");
                documentKey = obj.getString("documentKey");
                date = obj.getString("date");
                annsUrl = "https://cdn-api.markitdigital.com/apiman-gateway/ASX/asx-research/1.0/file/" + documentKey + "?access_token=83ff96335c2d45a094df02a206a39ff4";
                String finalHeadLine = headLine;
                if (keyWords.stream().anyMatch(key -> finalHeadLine.toLowerCase().contains(key.toLowerCase()))) {
                    System.out.println(MessageFormat.format("{0},{1},{2},{3}::::{4}::::::", ticker, headLine, annsUrl, documentKey, i));
                }
            } else {
                ticker = objJSONArray.getJSONObject(0).getString("symbol");
                companyName = obj
                        .getJSONArray("companyInfo")
                        .getJSONObject(0)
                        .getString("displayName");
                date = obj.getString("date");
                documentKey = obj.getString("documentKey");
                headLine = obj.getString("headline");
                annsUrl = "https://cdn-api.markitdigital.com/apiman-gateway/ASX/asx-research/1.0/file/" + documentKey + "?access_token=83ff96335c2d45a094df02a206a39ff4";
                date = ParameterUtils.getDateInYourFormat(date, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd HH:mm:ss");
                String finalHeadLine = headLine;
                if (keyWords.stream().anyMatch(key -> finalHeadLine.toLowerCase().contains(key.toLowerCase()))) {
                    System.out.println(MessageFormat.format("{0},{1},{2},{3}::::{4}::::::", ticker, headLine, annsUrl, documentKey, i));
                }

            }
        }


    }
}



