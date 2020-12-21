package services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;


public class USA_form {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void scrape() {
        int i = 1;
        while (true) {
            String res = getContent(i);
            Document parse = Jsoup.parse(res);
            Elements elements = parse.select("body > div > table>tbody>tr:not(:first-child)");
            elements.forEach(USA_form::processRows);
//            System.out.println(elements);
            System.out.println("*********************************************************************** pagenumber" + Math.ceil(i / 80));
            if (!res.contains("[NEXT]")) break;
            i += 80;

        }
    }

    private static void processRows(Element row)  {

        String companyName = row.select("td:nth-child(2)").text();
        String companyUrl = " https://www.sec.gov/" + row.select("td:nth-child(2)>a").attr("href");
        String formType = row.select("td:nth-child(4)").text();
        String fillingDate = row.select("td:nth-child(5)").text();
//        System.out.println(MessageFormat.format("{0},{1},{2},{3}",companyName,formType,fillingDate,companyUrl));
        System.out.println("------------------------------------"+companyUrl);
        processFillingDetail(companyUrl);
    }

    private static void processFillingDetail(String url) {
        try {
            Document document = Jsoup.connect(url).get();
//        String cikNumber = document.body().select("#filerDiv > div.companyInfo > span > a").text().replace("(see all company filings)", "").trim();
            String cikNumber = document.body().select("#filerDiv > div.companyInfo > span > a").text().replace("(see all company filings)", "").trim();
//            System.out.println(cikNumber);
            String reportDate = document.body().select("#formDiv > div.formContent > div:nth-child(2) > div:nth-child(2)").text().trim();
            String acceptedDate = document.body().select("#formDiv > div.formContent > div:nth-child(1) > div:nth-child(4)").text().trim();
            String effectivnessDate = document.body().select("#formDiv > div.formContent > div:nth-child(2) > div:nth-child(4)").text().trim();

            if (document.select("#formDiv").size() == 2) {
                Elements formLinks = document.select("#formDiv>div>table>tbody>tr>td:nth-child(4)");
                //#formDiv > div > table
                formLinks.forEach(t -> {
                    boolean found = "information table".equals(t.text().toLowerCase());
                    if (found) {
                        Element parent = t.parent();
                        if (parent.select(":nth-child(3)").text().contains("html")) {
                            System.out.println("https://www.sec.gov/" + parent.select(":nth-child(3)>a").attr("href")); // link for form table
                        }
                    }


                });


            }
        } catch (IOException ex) {
            System.out.println(ex.getCause());
        }
    }
    private static String getContent(int i) {
        String response = "";
        String source = "https://www.sec.gov/cgi-bin/srch-edgar?text=13F-%2A&start=" + i + "&count=80&first=2020&last=2020";
        try {
            URL url = new URL(source);
            HttpsURLConnection getConnection = (HttpsURLConnection) url.openConnection();
            getConnection.setConnectTimeout(5000);
            getConnection.setReadTimeout(10000);
            getConnection.setRequestMethod("GET");
            getConnection.setRequestProperty("cache-control", "max-age=0");
            getConnection.setRequestProperty("accept-language", "en-US,en;q=0.9");
            getConnection.setRequestProperty("sec-fetch-dest", "document");
            getConnection.setRequestProperty("sec-fetch-mode", "navigate");
            getConnection.setRequestProperty("sec-fetch-site", "none");
            getConnection.setRequestProperty("sec-fetch-user", "?1");
            getConnection.setRequestProperty("upgrade-insecure-requests", "1");
            getConnection.setRequestProperty("cookie", "_ga=GA1.2.1561981629.1607411981; _gid=GA1.2.1231345048.1607914830; ak_bmsc=E3C979C8045C8E2B8BBDBF0042C08F267D38DEC747440000AED5D65FEC777506~plBXsIrlmP55KUybPTVAo5+/Qv+/5KCm7ibMA/rcnvwq2tveNeZ/zbtNw67ynE1GhsFjcFYx021enNpPl75avAEDq9Eqi2YqN04xlLZgZmC144E9A/p7PI0yDrIyTh9XlWy5LQ/ieHkSaNSoc1pAbEvU9a/o2P1oSF9KImkpiVLaYwZeIb7mG/Eq1jkkJGFlnCfkr+y09gpKPH0/WvSdjCqksYdX4VoIjFltm+tAIIWHE=; bm_sv=1FCB47A769ECBDE91B8C7C83D5043E8F~qRNI4Yx+beme6uzBtew0v0S6gDXhBEfgAdD5rte96ihwL3pOTP9r8KUmnnydbncjwTUuuuGtvGTfDlo1HNwlDeUbEcrXph39OkkXKdN4Qr7CWitAO2fFNiGV+2JB/RfK4r4r7qgzHu5VZj4ZgqYzuA==; _gat=1; _gat_GSA_ENOR0=1; _gat_GSA_ENOR1=1; _4c_=bVNNj9sgEP0rKw57qEIC2MY40qqqWrXqobdWPUYYiI3iBgtIvNso%2F70ziTf7ofpiz5s3j%2FHM40Sm3u3JmktWN4KLQjLGFmTnnhJZn0j0Fl9HsibWScOZqKlSlaOldluqXclpxYtWbQulpVZkQR5Ri1dc1Y0STVWdF8TuXzS2%2BjDk1zTFipKzAmh%2BzDMPmym5YIWUrHzLRQS5z4qa%2FDcfp5vUnGhYXb2lIgJUM87UEznEAST7nMe0Xq2maVomZ5ZdOK5M52nr96sUTU%2Bd7XT8mN1jfuDFV%2Frhfutjyg%2BCCXY%2F6PkL%2BjLBOtDjzZLzJQcg%2F4WwYJgbY7AHkzf5aUTO5Nq7ZHeQsO7ojdtM3uYei6tCvqC9810PUyJKloiOESlLUUEw%2Bb0N0%2FvCGb0V1kIA2sYwJYfFn%2FsY%2Frg7hdwAKye%2FLwUJwui2LsYLC6LkMzY6D2QGwCZXjF6xEVeCBwzB6AH54K0F%2BfZp8%2Bv7l0tTkjeKSwEjuewYIyD8jL7rXPzhch%2FAcBBr67MPez2Qq39eWcfifxgYdPLGurTLYSTnebVMMVZLsAEuO2dYJ0yK4QOM4801FZwtW63AxorTUtmWNqUuqNJlZRupRNvyZ2PhvYCdgSSfJbm6Kp7P%2FwA%3D");
            getConnection.setRequestProperty("referrerPolicy", "strict-origin-when-cross-origin");
            getConnection.setRequestProperty("mode", "cors");

            int statusCode = getConnection.getResponseCode();
            if (statusCode > 299) throw new RuntimeException("US-13F RESPONSE CODE :" + statusCode);

            try (InputStream inputStream = getConnection.getInputStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder line = new StringBuilder();
                String line1;
                while ((line1 = reader.readLine()) != null) {
                    line.append(line1);
                }
                response = line.toString();
            }
        } catch (Exception ex) {
            System.out.println(ex.getCause());
        }
        return response;
    }
}
