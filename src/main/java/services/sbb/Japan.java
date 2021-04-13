package services.sbb;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Japan {
    public static void scrape() {

        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);

            Page downloadPage = webClient.getPage("https://disclosure.edinet-fsa.go.jp/E01EW/download?uji.verb=W0EZA104CXP002006BLogic&uji.bean=ee.bean.parent.EECommonSearchBean&PID=W1E63021&SESSIONKEY=1611657447526&lgKbn=2&pkbn=0&skbn=1&dskb=&askb=&dflg=0&iflg=0&preId=1&sec=&scc=&shb=&snm=&spf1=1&spf2=1&iec=&icc=&inm=&spf3=1&fdc=&fnm=&spf4=1&spf5=2&otd=220&otd=230&cal=1&era=H&yer=&mon=&psr=1&pfs=4&row=100&idx=0&str=&kbn=1&flg=&syoruiKanriNo=&no=S100KKXX");
//            HtmlAnchor docFile = page.querySelector("#control_object_class1 > div > div.result > table > tbody > tr:nth-child(2) > td:nth-child(7) > div > a");
//            docFile.click();
            System.out.println(downloadPage.getWebResponse().getStatusCode());
            webClient.waitForBackgroundJavaScript(1000);
//            Page downloadPage = webClient.getCurrentWindow().getEnclosedPage();
            File destFile = new File("/tmp", "myfile.zip");
            try (InputStream contentAsStream = downloadPage.getWebResponse().getContentAsStream()) {
                try (OutputStream out = new FileOutputStream(destFile)) {
                    IOUtils.copy(contentAsStream, out);
                }
            }
            System.out.println("Output written to " + destFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//package com.x2iq.sbb.bots.service;
//
//        import com.gargoylesoftware.htmlunit.Page;
//        import com.gargoylesoftware.htmlunit.WebClient;
//        import com.x2iq.sbb.bots.repo.BotsRepo;
//        import com.x2iq.sbb.common.ParameterUtils;
//        import com.x2iq.sbb.common.RequestSetter;
//        import org.apache.commons.io.IOUtils;
//        import org.jsoup.Jsoup;
//        import org.jsoup.nodes.Document;
//        import org.jsoup.nodes.Element;
//        import org.jsoup.select.Elements;
//        import org.slf4j.Logger;
//        import org.slf4j.LoggerFactory;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.stereotype.Service;
//
//        import javax.net.ssl.HttpsURLConnection;
//        import javax.net.ssl.SSLContext;
//        import javax.net.ssl.TrustManager;
//        import javax.net.ssl.X509TrustManager;
//        import java.io.*;
//        import java.net.HttpURLConnection;
//        import java.net.URL;
//        import java.net.URLEncoder;
//        import java.security.KeyManagementException;
//        import java.security.NoSuchAlgorithmException;
//        import java.security.cert.CertificateException;
//        import java.security.cert.X509Certificate;
//        import java.text.MessageFormat;
//        import java.text.ParseException;
//        import java.text.SimpleDateFormat;
//        import java.util.*;
//
//@Service
//public class JPBotService implements BotService {
//
//
//    private static final int toolId = 97;
//    private static int count = 0;
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    private TrustManager[] trustManagers = new TrustManager[]{
//            new X509TrustManager() {
//                @Override
//                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//                }
//
//                @Override
//                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//                }
//
//                @Override
//                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//            }
//    };
//    private BotsRepo botsRepo;
//
//    @Autowired
//    public JPBotService(BotsRepo botsRepo) {
//        this.botsRepo = botsRepo;
//    }
//
//    private static byte[] getPostData(String url, int pageNumber) throws UnsupportedEncodingException {
//        String dateFrom, dateTo;
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//
//        dateFrom = simpleDateFormat.format(ParameterUtils.getDateBeforeDays(new Date(), 10));
//        dateTo = simpleDateFormat.format(new Date());
//        Map<String, Object> params = new LinkedHashMap<>();
//        if (pageNumber != 0)
//            params.put("t0", dateFrom);
//        params.put("t1", dateTo);
//        params.put("p", pageNumber);
//        params.put("q", null);
//        StringBuilder postData = new StringBuilder();
//        for (Map.Entry<String, Object> param : params.entrySet()) {
//            if (postData.length() != 0) postData.append("&");
//            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
//            postData.append("=");
//            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
//        }
//        return postData.toString().getBytes("UTF-8");
//    }
//
//    private static HttpURLConnection getResponseForJPURL(String url, int pageNumber) throws IOException {
//
//        String dateFrom, dateTo;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//        dateFrom = simpleDateFormat.format(ParameterUtils.getDateBeforeDays(new Date(), 10));
//        dateTo = simpleDateFormat.format(new Date());
//        URL urls = new URL(url + "?t0=" + dateFrom + "&t1=" + dateTo + "&q=&p=" + pageNumber + "");
//        HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
//
//        RequestSetter requestSetter = new RequestSetter();
//        requestSetter.setMethod("POST");
//        requestSetter.setAccept("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//        requestSetter.setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
//        requestSetter.setHost("www.release.tdnet.info");
//        requestSetter.setReferer("https://www.release.tdnet.info/onsf/TDJFSearch_e/I_initial");
//        requestSetter.setOrigin("https://www.release.tdnet.info");
//        requestSetter.setContentType("application/x-www-form-urlencoded");
//        requestSetter.setAcceptLanguage("en-US,en;q=0.9");
//        requestSetter.setAcceptEncoding("gzip, deflate,br");
//        requestSetter.setIsDoInput(true);
//        requestSetter.setIsDoOutput(true);
//        conn = requestSetter.getHttpUrlConnection(conn);
//        conn.setRequestProperty("Cache-Control", "max-age=0");
//        conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
//        conn.setDoOutput(true);
//        return conn;
//    }
//
//    @Override
//    public String getName() {
//        return "JPBot";
//    }
//
//    @Override
//    public boolean startBot() {
//        count = 0;
//        try {
//            startJPBot();
//        } catch (NoSuchAlgorithmException | IOException | KeyManagementException e) {
//            logger.error(e.getMessage());
//        }
//        return botsRepo.setToolCompleted(true, count, toolId) > 0;
//    }
//
//    private HttpsURLConnection getConnection(int i) throws IOException, NoSuchAlgorithmException, KeyManagementException {
//
//        SSLContext sc = SSLContext.getInstance("SSL");
//
//        sc.init(null, trustManagers, new java.security.SecureRandom());
//        String siteUrl = "https://disclosure.edinet-fsa.go.jp/E01EW/BLMainController.jsp?uji.verb=W1E63020CXW1E6A020DSPSch&uji.bean=ee.bean.parent.EECommonSearchBean&PID=W1E63020&TID=W1E63021&SESSIONKEY=1426256819167&lgKbn=2&pkbn=0&skbn=0&dskb=&dflg=0&iflg=0&preId=1&row=100&idx=" + i + "&syoruiKanriNo=&sec=&scc=&snm=&spf1=1&spf2=1&iec=&icc=&inm=&spf3=1&fdc=&fnm=&spf4=1&spf5=2&otd=220&otd=230&cal=1&era=H&yer=&mon=&psr=1&pfs=4";
//
//        URL url = new URL(siteUrl);
//        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
//        httpsURLConnection.setSSLSocketFactory(sc.getSocketFactory());
//        httpsURLConnection.setRequestMethod("GET");
//        RequestSetter requestSetter = new RequestSetter();
//        requestSetter.setAccept("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//        requestSetter.setAcceptEncoding("gzip, deflate, br");
//        requestSetter.setHost("disclosure.edinet-fsa.go.jp");
//        requestSetter.setUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
//        httpsURLConnection = requestSetter.getHttpUrlConnection(httpsURLConnection);
//        httpsURLConnection.setRequestProperty("Cookie", "style=null; JSESSIONID=6KRM6C9K6CQJ0D9H6GS3ACR370Q3GC1K61IJ4E1HCPI6AD8KFI2MC08000500000.E01EW_001; FJNADDSPID=0Z8Fm6");
//        httpsURLConnection.setRequestProperty("Connection", "keep-alive");
//        return httpsURLConnection;
//    }
//
//    private void startJPBot() throws NoSuchAlgorithmException, IOException, KeyManagementException {
//
//        if (botsRepo.checkIsValidCountry(toolId) != 392) {
//            return;
//        }
//        for (int i = 0; i <= 2900; i = i + 100) {
//
//            String response = getResponseFromHttpConnection(getConnection(i));
//            if (response != null) {
//
//                Document document = Jsoup.parse(response);
//                Elements elements = document.getElementsByClass("resultTable");
//                if (elements.size() == 1) {
//                    Element element = elements.first();
//                    Elements rows = element.getElementsByTag("tr");
//                    for (Element row : rows) {
//
//                        if (rows.first() == row)
//                            continue;
//                        Elements columns = row.children();
//                        if (columns.size() > 8) {
//
//                            String date = columns.get(0).text();
//                            String code = columns.get(2).text();
//                            String companyName = columns.get(3).text();
//                            String headline = companyName + " " + code;
//                            String savingUrl = "";
//                            String href;
//                            date = JPDateToNormalDate(date);
//                            if (date == null)
//                                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//                            if (row.getElementsByTag("a").size() > 1) {
//                                href = row.getElementsByTag("a").get(2).attr("href");
//                                String[] hrefSplitted = href.split("&");
//                                href = "";
//                                for (String hrefPart : hrefSplitted) {
//                                    if (hrefPart.equals(hrefSplitted[0]) && hrefPart.contains("download?")) {
//                                        String[] fixingVariation = hrefPart.split("\\?");
//                                        fixingVariation[fixingVariation.length - 1] = "1";
//                                        hrefPart = "";
//                                        for (String str :
//                                                fixingVariation) {
//                                            hrefPart = hrefPart.concat(str);
//                                            if (!str.equals(fixingVariation[fixingVariation.length - 1]))
//                                                hrefPart = hrefPart.concat("?");
//                                        }
//                                    }
//                                    if (Objects.equals(hrefPart, hrefSplitted[hrefSplitted.length - 2]))
//                                        continue;
//                                    href = href.concat(hrefPart);
//                                    if (!hrefPart.equals(hrefSplitted[hrefSplitted.length - 1]))
//                                        href = href.concat("&");
//                                }
//                                savingUrl = "https://disclosure.edinet-fsa.go.jp" + href;
//                            }
//                            if (!botsRepo.checkIsUrlAlreadyExist(savingUrl, 392)) {
//                                botsRepo.insertAnnouncement(headline, date, 14, 3, savingUrl, 392, null, null);
//                                String no = savingUrl.split("&s=")[1];
//                                String src = "https://disclosure.edinet-fsa.go.jp/E01EW/download?uji.verb=W0EZA104CXP002006BLogic&uji.bean=ee.bean.parent.EECommonSearchBean&PID=W1E63021&SESSIONKEY=1611744231537&lgKbn=2&pkbn=0&skbn=1&dskb=&askb=&dflg=0&iflg=0&preId=1&sec=&scc=&shb=&snm=&spf1=1&spf2=1&iec=&icc=&inm=&spf3=1&fdc=&fnm=&spf4=1&spf5=2&otd=220&otd=230&cal=1&era=H&yer=&mon=&psr=1&pfs=4&row=100&idx=" + i + "&str=&kbn=1&flg=&syoruiKanriNo=&no=" + no;
//                                backupRecord(src, code, date);
//                                count++;
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//
//        for (int i = 1; i < 10; i++) {
//            try {
//                processAnnouncementOnSecondUrl("https://www.release.tdnet.info/onsf/TDJFSearch_e/TDJFSearch_e", i);
//            } catch (Exception ex) {
//                logger.info(ex.getMessage());
//            }
//        }
//        logger.info("Announcement Added in JP: " + count);
//    }
//
//    private String JPDateToNormalDate(String date) {
//        Date sqlDate;
//        int year = 0;
//        String pattern = null;
//        if (date.startsWith("R2")) {
//            pattern = "'R2'.MM.dd HH:mm";
//            year = Calendar.getInstance().get(Calendar.YEAR) - 1;
//        } else {
//            pattern = "'R3'.MM.dd HH:mm";
//            year = Calendar.getInstance().get(Calendar.YEAR);
//        }
//        try {
//            sqlDate = new SimpleDateFormat(pattern).parse(date);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(sqlDate);
////      int year = calendar.get(Calendar.YEAR);
////      year = year + 50;
//            calendar.set(Calendar.YEAR, year);
//            return ParameterUtils.getDateInStringFromDate(calendar.getTime());
//        } catch (ParseException e) {
//            try {
//                sqlDate = new SimpleDateFormat("'H'yyyy.MM.dd HH:mm").parse(date);
//            } catch (ParseException e1) {
//                return null;
//            }
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(sqlDate);
//            int years = calendar.get(Calendar.YEAR);
//            years = years + 1988;
//            calendar.set(Calendar.YEAR, years);
//            return ParameterUtils.getDateInStringFromDate(calendar.getTime());
//        }
//    }
//
//    private void processAnnouncementOnSecondUrl(String url, int pageNumber) throws IOException {
//        final int toolId = 97;
//        final int countryId = botsRepo.checkIsValidCountry(toolId);
//
//        if (countryId > 0) {
//            String response = null;
//            response = getResponseFromHttpPostRequest(getResponseForJPURL(url, pageNumber), getPostData(url, pageNumber));
//
//            if (response != null && response.length() > 0) {
//                Document document = Jsoup.parse(response);
//                Elements announcements = document.getElementsByTag("table");
//                for (Element element : announcements.get(1).getElementsByTag("tr")) {
//
//                    String date = element.getElementsByTag("td").get(0).text();
//                    String ticker = element.getElementsByTag("td").get(1).text();
//                    String companyName = element.getElementsByTag("td").get(2).text();
//                    String headline = element.getElementsByTag("td").get(4).text();
//                    String headlineLink = element.getElementsByTag("a").attr("href");
//                    String headlineUrl = "https://www.release.tdnet.info" + headlineLink;
//
//                    if (headline.toLowerCase().contains("share dealing")
//                            || headline.toLowerCase().contains("own securities")
//                            || headline.toLowerCase().contains("own shares")
//                            || headline.toLowerCase().contains("buy back")
//                            || headline.toLowerCase().contains("share repurchase")
//                            || headline.toLowerCase().contains("treasury stock")
//                            || headline.toLowerCase().contains("purchase of shares")
//                            || headline.toLowerCase().contains("own shares buyback")
//                            || headline.toLowerCase().contains("buy-back")
//                    ) {
//                        if (!botsRepo.checkIsUrlAlreadyExist(headlineUrl, countryId)) {
//                            botsRepo.insertAnnouncement(headline, date, 14, 3, headlineUrl, 392, companyName, "", ticker, "");
//                            count++;
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private void backupRecord(String url, String headLine, String date) {
//        try (WebClient webClient = new WebClient()) {
//            webClient.getOptions().setCssEnabled(false);
//            webClient.getOptions().setJavaScriptEnabled(false);
//            Page downloadPage = webClient.getPage(url);
//            webClient.waitForBackgroundJavaScript(1000);
//            File destFile = new File("/tmp", MessageFormat.format("{0}-{1}-{2}.zip", headLine, date.substring(0,10),System.currentTimeMillis() / 1000));
//            try (InputStream contentAsStream = downloadPage.getWebResponse().getContentAsStream()) {
//                try (OutputStream out = new FileOutputStream(destFile)) {
//                    IOUtils.copy(contentAsStream, out);
//                }
//            }
//            System.out.println("Output written to " + destFile.getAbsolutePath());
//        }catch (IOException ex){
//            logger.info(ex.getMessage());
//        }
//    }
//}
