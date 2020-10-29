package services;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import models.CanadaDisclosure;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Canda {

    public static void scrap() throws IOException {
        //"4/16/2020","/Documents/2020/eabc951d-d11e-445c-bf04-70e18d9e0b86_en.csv"
//        List<CanadaData> historicalData = new ArrayList<>();
        String url = "https://www.iiroc.ca/news/Pages/Short-Sale.aspx";
        Map<String, String> urls = new HashMap<String, String>() {{
            put("4/1/2020", "/Documents/2020/2d243746-e993-4711-aba0-31ca02c1e284_en.csv");
            put("3/16/2020", "/Documents/2020/cf59f5e3-f59c-4be6-a364-d757e936da22_en.csv");
            put("3/2/2020", "/Documents/2020/6604c07a-73fd-4523-8922-5500629e9477_en.csv");
            put("2/17/2020", "/Documents/2020/696e3956-dddf-4341-8a1e-5b516d022cbe_en.csv");
            put("2/3/2020", "/Documents/2020/0ca4115d-9181-4858-a343-37ca36dae5f5_en.csv");
            put("1/16/2020", "/Documents/2020/9fcd7ee9-a01a-48f6-94ad-bbe84fc3ba4f_en.csv");
            put("1/1/2020", "/Documents/2020/716bfeb2-64d5-49d6-93b7-2b80887ddd8b_en.csv");
            put("12/16/2019", "/Documents/2019/d153a3a0-6a0d-4e72-bde8-cbb51f0eccc5_en.csv");
            put("12/2/2019", "/Documents/2019/74547828-89a2-4c12-8681-a1ceab2f3404_en.csv");
            put("11/18/2019", "/Documents/2019/473839f8-52e6-4cff-893e-11837060eeb8_en.csv");
            put("11/1/2019", "/Documents/2019/c6119a10-c050-40f2-a93f-1ccd8ed6384d_en.csv");
            put("10/16/2019", "/Documents/2019/1eb3fc76-a051-43b4-8cfc-709e749d89d4_en.csv");
            put("10/1/2019", "/Documents/2019/f6e144a9-0211-443c-8cab-3cc34dc8c01e_en.csv");
            put("9/16/2019", "/Documents/2019/c805a1ce-4856-49e4-a955-547e70876b2c_en.csv");
            put("9/2/2019", "/Documents/2019/7c2fbb08-2d18-46fe-b996-41caf57cdbb7_en.csv");
            put("8/16/2019", "/Documents/2019/2f2aea15-c8e1-485a-899d-8a52edb8e4ba_en.csv");
            put("8/1/2019", "/Documents/2019/1606464b-1073-4eb1-bb81-006188f4cc4f_en.csv");
            put("7/16/2019", "/Documents/2019/8dc67e17-43ee-46f8-b68e-9aa8467ee6d8_en.csv");
            put("7/1/2019", "/Documents/2019/8950ee29-6722-4388-8cf8-4c81079f1d17_en.csv");
            put("6/17/2019", "/Documents/2019/4ae8891a-2da1-4c58-88b7-1bff50242d44_en.csv");
            put("6/3/2019", "/Documents/2019/604f5384-5058-455f-8ef3-e403cb3e0673_en.csv");
            put("5/16/2019", "/Documents/2019/38df84a3-b8a8-4c02-a065-524df584b8d8_en.csv");
            put("5/1/2019", "/Documents/2019/3d363df9-c75e-4de7-8544-6609a2e59867_en.csv");
            put("4/16/2019", "/Documents/2019/e7717bb9-5879-49ea-b849-c64066086650_en.csv");
            put("4/1/2019", "/Documents/2019/c6f13975-b217-45fb-994d-bd2986b4c65d_en.csv");
            put("3/18/2019", "/Documents/2019/400adb32-3236-4e8f-a4ad-045f2420c18b_en.csv");
            put("3/1/2019", "/Documents/2019/c919a728-bc61-47a0-9162-51b41d0d56f0_en.csv");
            put("2/18/2019", "/Documents/2019/4ca51e25-a476-42bd-bbd9-19f37b696def_en.csv");
            put("2/1/2019", "/Documents/2019/354758b0-861a-430f-b15b-aac77e0a2ee9_en.csv");
            put("1/16/2019", "/Documents/2019/d8277b02-66ea-4848-8c77-95d724f8a222_en.csv");
            put("1/1/2019", "/Documents/2019/c6cbfcae-d825-4df8-9c13-bb47160ba06b_en.csv");
            put("12/17/2018", "/Documents/2018/23e9ef21-cf54-43a5-920d-a09c234b9ea5_en.csv");
            put("12/3/2018", "/Documents/2018/e1b64c18-a435-4ef0-bc43-4cadacbdc48c_en.csv");
            put("11/16/2018", "/Documents/2018/6d5a9ce2-bc26-44fa-a8bc-3e709aec2f17_en.csv");
            put("11/1/2018", "/Documents/2018/2cd68f44-91f2-45cc-89fd-5cb4cd393b33_en.csv");
            put("10/16/2018", "/Documents/2018/0efb7bf6-6f7d-4b24-8cd4-3b9a164563d1_en.csv");
            put("10/1/2018", "/Documents/2018/05d40766-8096-4af2-a4a6-5acfadc1acf4_en.csv");
            put("9/17/2018", "/Documents/2018/4b001c0d-f902-4a63-9823-7a8ac20b5943_en.csv");
            put("9/3/2018", "/Documents/2018/2b36e9a4-58cd-4b44-a04e-5f717a2d3a76_en.csv");
            put("8/16/2018", "/Documents/2018/fa284d99-ec57-43e8-b74f-0441cf4edbf8_en.csv");
            put("8/1/2018", "/Documents/2018/ccce241a-dd82-48d3-ad4b-295dd8627908_en.csv");
            put("7/16/2018", "/Documents/2018/8ad6611b-dafd-4d1a-b101-9a672faadd37_en.csv");
            put("7/2/2018", "/Documents/2018/b1c4f926-4f8d-4d8e-8da9-fe092f2995ae_en.csv");
            put("6/18/2018", "/Documents/2018/8e4426d0-f454-4856-a8ba-5c49219425aa_en.csv");
            put("6/1/2018", "/Documents/2018/64608250-a98c-40bd-88d3-b98c9780225e_en.csv");
            put("5/16/2018", "/Documents/2018/6a24bb95-b353-42dd-a645-bc678c53fc96_en.csv");
            put("5/1/2018", "/Documents/2018/06a7ff9a-77ac-4918-a6ea-aee9f2b811d6_en.csv");
            put("4/16/2018", "/Documents/2018/84d0b3e0-60e9-465b-b5af-b40d15fb8c02_en.csv");
            put("4/2/2018", "/Documents/2018/dafd165d-824a-42b9-a607-8341f57b1fe6_en.csv");
            put("3/16/2018", "/Documents/2018/91028f9c-f90b-4842-a4d0-ddb62122d036_en.csv");
            put("3/1/2018", "/Documents/2018/b6d5decc-0879-4646-bf20-3145cb65e956_en.csv");
            put("2/16/2018", "/Documents/2018/22e8e0d3-9e50-4a47-bc6f-d763a06f5847_en.csv");
            put("2/1/2018", "/Documents/2018/c88d8e3d-aaf0-415a-b233-613fa174a4d5_en.csv");
            put("1/16/2018", "/Documents/2018/8e4a7a8b-0a37-497d-b97e-fb41bfd76f36_en.csv");
        }};

        try (WebClient webClient = new WebClient()) {
            URL ur = new URL(url);
            WebRequest requestSettings = new WebRequest(ur, HttpMethod.POST);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            HtmlPage mainPage = webClient.getPage(requestSettings);
            Document document = Jsoup.parse(mainPage.asXml());
            Elements tbody = document.select("#WebPartWPQ2 > table:nth-child(1) > tbody >tr >" +
                    "td:nth-child(1),#WebPartWPQ2 > table:nth-child(1) > tbody >tr >td:nth-child(2)");
            String[] rows = String.valueOf(tbody).split("</td>");
            for (int i = 0; i < rows.length - 1; ) {
                urls.put(getDate(rows[i++]), getUrl(rows[i++]));
            }

            for (Map.Entry<String, String> entry : urls.entrySet()) {
                downloadUrl(entry.getKey(), "https://www.iiroc.ca" + entry.getValue());
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    private static String getUrl(String td) {
        Pattern pattern = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1");
        Matcher matcher = pattern.matcher(td);
        String url = null;
        if (matcher.find()) {
            url = matcher.group(2);

        }
        return url;
    }

    private static String getDate(String td) {
        Pattern pattern = Pattern.compile("\\d{1,2}\\/\\d{1,2}\\/\\d{4}");
        Matcher matcher = pattern.matcher(td);
        String date = null;
        if (matcher.find()) {
            date = matcher.group();

        }
        return date;
    }

    private static void downloadUrl(String date, String link) throws IOException, ParseException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(link).openConnection());
        httpURLConnection.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36");
        if (httpURLConnection.getResponseCode() == 404) return;
        try (InputStream inputStream = httpURLConnection.getInputStream()) {

            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {

                try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                    String line = bufferedReader.readLine();
                    while (line != null) {
                        processCsvLine(date, link, line);
                        line = bufferedReader.readLine();
                    }
                }
            }
        }
    }

    private static void processCsvLine(String date, String url, String line) throws ParseException {
        String[] anns = line.split(",");
        CanadaDisclosure disclosure = new CanadaDisclosure();
        SimpleDateFormat sdf1 = new SimpleDateFormat("M/d/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date parseDate = sdf1.parse(date);


        try {
            disclosure.setTicker(anns[0]);
            disclosure.setIssuer_name(anns[1].replace("\"", ""));
            disclosure.setMarket(anns[2].replace("\"", ""));
            disclosure.setShort_sale_trades(Long.parseLong(anns[3]));
            disclosure.setPercent_of_total_trades(new BigDecimal(anns[4]));
            disclosure.setShort_selling_volume(Long.parseLong(anns[5]));
            disclosure.setPercent_of_total_traded_volume(new BigDecimal(anns[6]));
            disclosure.setShort_sell_traded_value(Long.parseLong(anns[7]));
            disclosure.setPercent_of_total_traded_value(new BigDecimal(anns[8]));
            disclosure.setUrl(url);
        } catch (NumberFormatException ex) {
//            logger.info(ex.getMessage());
            return;
        }
        System.out.println(disclosure.getReporting_date());
        System.out.println(disclosure.getTicker());
        System.out.println(disclosure.getIssuer_name());
        System.out.println(disclosure.getMarket());
        System.out.println(disclosure.getShort_selling_volume());
        System.out.println(disclosure.getPercent_of_total_traded_volume());
        System.out.println(disclosure.getShort_sell_traded_value());
        System.out.println(disclosure.getPercent_of_total_traded_value());
        System.out.println(disclosure.getUrl());
        // call db
        System.out.println("*************");

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

