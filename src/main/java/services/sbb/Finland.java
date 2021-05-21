package services.sbb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ParameterUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Finland {
    private static int countryId = 246;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static int count = 0;


    public boolean startCrawling() {
        Long toDate = getTimeStamp(today());
        long fromDate = getTimeStamp(getPreviousYear(4));
        int recordsCount = 0;
        while (true) {
            String url = getUrl(fromDate, toDate, recordsCount);
            String response = getReponse(url);
            processJsonData(response);
            if (isLastPage(response)) break;
            recordsCount += 20;

        }
        return true;
    }

    public long getTimeStamp(long time) {
        Timestamp timestamp = new Timestamp(time);
        return timestamp.getTime();

    }

    private Long today() {
        Date date = new Date();
        return date.getTime();
    }

    public static long getPreviousYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -year);
        return cal.getTime().getTime();
    }

    public String getUrl(long fromDate, long toDate, int recordStart) {
        return "https://api.news.eu.nasdaq.com/news/query.action?type=json&showAttachments=true&showCnsSpecific=true&showCompany=true&callback=handleResponse&countResults=false&freeText=&company=&market=Main%20Market%2C+Iceland&cnscategory=Changes+in+company%27s+own+shares&fromDate=" + fromDate + "&toDate=" + toDate + "&globalGroup=exchangeNotice&globalName=NordicMainMarkets&displayLanguage=en&language=&timeZone=CET&dateMask=yyyy-MM-dd+HH%3Amm%3Ass&limit=20&start=" + recordStart + "&dir=DESC";
    }

    private String getReponse(String url) {
        StringBuilder response = new StringBuilder();
        URL src = null;
        HttpURLConnection httpConn = null;
        InputStream responseStream = null;
        try {
            src = new URL(url);
            httpConn = (HttpURLConnection) src.openConnection();
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
            responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream() : httpConn.getErrorStream();

            try (InputStreamReader inputStreamReader = new InputStreamReader(responseStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            ) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
            }
        } catch (IOException e) {
            logger.error(Arrays.toString(e.getStackTrace()) + "  SomeThing went wrong with connection");
        }
        String result = response.toString();
        try {
            int startIndex = result.indexOf("{");
            int lastIndex = result.lastIndexOf(")");
            result = result.substring(startIndex, lastIndex);
        } catch (IndexOutOfBoundsException ex) {
            logger.error(ex.getMessage() + Arrays.toString(ex.getStackTrace()));
        }
        return result;
    }

    private void processJsonData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONObject("results").getJSONArray("item");
            for (int index = 0; index < jsonArray.length(); index++) {
                JSONObject obj = (JSONObject) jsonArray.get(index);
                String rnsId = obj.get("disclosureId").toString();
                String headline =  obj.get("headline").toString();
                String company =  obj.get("company").toString();
                String url =  obj.get("messageUrl").toString();
                String date =  obj.get("releaseTime").toString();


                insertAnnouncement(company, headline, date, url, rnsId, "");


            }
        } catch (JSONException ex) {
            logger.error(Arrays.toString(ex.getStackTrace()) + ex.getMessage());
        }
    }

    private boolean isLastPage(String response) {
        boolean result = false;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONObject("results").getJSONArray("item");
            result = jsonArray.isEmpty();
        } catch (JSONException ex) {
            logger.error(Arrays.toString(ex.getStackTrace()) + ex.getLocalizedMessage());
            return result = true;
        }
        return result;
    }


    private String getDate(String line) {
        String thisFormat = "yyyy-MM-dd HH:mm:ss";
        String requiredFormat = "yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(thisFormat);
        String date = null;
        try {
            Date parsed = simpleDateFormat.parse(line);
            date = simpleDateFormat.format(parsed);

        } catch (ParseException ex) {
            logger.error(Arrays.toString(ex.getStackTrace()) + ex.getMessage());
        }
        return ParameterUtils.getDateInYourFormat(date, thisFormat, requiredFormat);

    }

    private void insertAnnouncement(String companyName, String headline, String date, String url, String rnsid, String annText) {
        int catId = 14;
        int statId = 3;
        getDate(date);
        System.out.println(MessageFormat.format("{0}------{1}-----{2}", companyName, date, url));

    }

    private String backUpData(String url) {
        String response = null;
        try {
            response = Jsoup.connect(url).get().body().toString();
        } catch (IOException e) {
            logger.error("something went wrong with backup data,unable to get ressponse");
        }
        return response;
    }
}
