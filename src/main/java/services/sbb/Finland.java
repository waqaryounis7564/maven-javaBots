package services.sbb;


import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Date;


public class Finland {
    public void crawl() {
        Long today = today();
        System.out.println(today);
    }

    public long getTimeStamp(long time) {
        Timestamp timestamp = new Timestamp(time);
        return timestamp.getTime();

    }

    private Long today() {
        Date date = new Date();
        return date.getTime();
    }

    public static long getMonthAgoDate(int month) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -month);
        return cal.getTime().getTime();
    }

    public String getUrl(String fromDate, String toDate, int recordStart) {
        return "https://api.news.eu.nasdaq.com/news/query.action?type=json&showAttachments=true&showCnsSpecific=true&showCompany=true&callback=handleResponse&countResults=false&freeText=&company=&market=Main%20Market%2C+Helsinki&cnscategory=Changes+in+company%27s+own+shares&fromDate=" + fromDate + "&toDate=" + toDate + "&globalGroup=exchangeNotice&globalName=NordicMainMarkets&displayLanguage=en&language=&timeZone=CET&dateMask=yyyy-MM-dd+HH%3Amm%3Ass&limit=20&start=" + recordStart + "&dir=DESC";
    }

    private String GetReponse(String url) throws IOException {
        StringBuilder response = new StringBuilder();
        URL src = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection) src.openConnection();
        httpConn.setRequestMethod("GET");
        httpConn.setRequestProperty("authority", "api.news.eu.nasdaq.com");
        httpConn.setRequestProperty("sec-ch-ua", "^\\^");
        httpConn.setRequestProperty("sec-ch-ua-mobile", "?0");
        httpConn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        httpConn.setRequestProperty("accept", "*/*");
        httpConn.setRequestProperty("sec-fetch-site", "cross-site");
        httpConn.setRequestProperty("sec-fetch-mode", "no-cors");
        httpConn.setRequestProperty("sec-fetch-dest", "script");
        httpConn.setRequestProperty("referer", "http://www.nasdaqomxnordic.com/");
        httpConn.setRequestProperty("accept-language", "en-US,en;q=0.9");

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream() : httpConn.getErrorStream();
        try (InputStreamReader inputStreamReader = new InputStreamReader(responseStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
        }
        String result = response.toString();
        int startIndex = result.indexOf("{");
        int lastIndex = result.lastIndexOf(")");
        return result.substring(startIndex, lastIndex);
    }

    private void processJsonData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
//            jsonObject

        }catch (JSONException ex){

        }


    }
}
