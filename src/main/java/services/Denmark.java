package services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ParameterUtils;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Denmark {
    private static int count = 0;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void startDKBot() throws IOException {
        getDKAnnouncements();
    }

    private static String getPageContent(String link) throws Exception {
        URL url = new URL(link);
        URLConnection urlConnection = url.openConnection();
        HttpsURLConnection httpsUrlConnection = (HttpsURLConnection) urlConnection;
        SSLSocketFactory sslSocketFactory = createSslSocketFactory();
        httpsUrlConnection.setSSLSocketFactory(sslSocketFactory);
        try (InputStream inputStream = httpsUrlConnection.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder line = new StringBuilder();
            String line1;
            while ((line1 = reader.readLine()) != null) {
                line.append(line1);
            }
            return line.toString();
        }
    }

    private static SSLSocketFactory createSslSocketFactory() throws Exception {
        TrustManager[] byPassTrustManagers = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        }};
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, byPassTrustManagers, new SecureRandom());
        return sslContext.getSocketFactory();
    }

    private static void getDKAnnouncements() throws IOException {
        count = 0;
        int index = 1;
        int sindex = 1;
        int totalPages = 44;
        String dateEnd = new SimpleDateFormat("yyyy:MM:dd").format(new Date());
        String dateStart = new SimpleDateFormat("yyyy:MM:dd").format(ParameterUtils.getDateBeforeDays(new Date(), 28));
        Document response;
        for (int pageNumber = 0; pageNumber <= totalPages; pageNumber++) {
            String url = "https://oasm.dfsa.dk/uk/searchannouncementresult.aspx?headline=&name=&RealAnnouncerCVR=&CVRnumber=&announcmentsId=-1&informationTypeId=-1&announcementTypeId=-1&languageId=-1&nationality=-1&pubDateFrom=" + dateStart + ":00:00&pubDateTo=" + dateEnd + ":12:21&regDateFrom=1999:01:01:00:00&regDateTo=2050:01:01:00:00&index=" + index + "&sindex=" + sindex + "&historic=1&postponed=0";
            String testUrl = "https://oasm.dfsa.dk/uk/searchannouncementresult.aspx?headline=&name=&CVRnumber=&RealAnnouncerCVR=&announcmentsId=-1&informationTypeId=-1&announcementTypeId=-1&languageId=-1&nationality=-1&pubDateFrom=2020:10:15:00:00&pubDateTo=2020:11:12:12:21&regDateFrom=1999:01:01:00:00&regDateTo=2050:01:01:00:00&index=7&sindex=61&historic=1&postponed=0";
            try {

                Document document = Jsoup.parse(getPageContent(testUrl));
                Elements rows = document.select("table>tbody");
                if(rows.size()==0) break;

                for (Element row : rows) {
                    String select = row.select("tr> td:nth-child(1)").text();
                    String announcement = row.select("tr.detail> td:nth-child(3) > strong > a").text();
                    String announcementLink = row.select("tr.detail > td:nth-child(3)> strong > a").attr("href");
                    String company = row.select(" tr.detail > td:nth-child(4)").text();
                    String g = "gh";
                }
                index += 1;
                sindex += 10;
            } catch (Exception e) {
                return;
            }
        }


    }
}