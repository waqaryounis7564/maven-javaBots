package services.SDS;

import models.sds.KoreaDisclosure;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class Korea {
    private Map<String, KoreaDisclosure> disclosures;
    private int count;

    public void scrapeData() {

        disclosures = new HashMap<>();
//            readCsvFile();
        crawlWeb();

        this.disclosures.values().forEach(r -> System.out.println(r.getPositionDate()));
        System.out.println("total counts are:" + count);
    }

    private void crawlWeb() {
        String content = getContent();
        System.out.println(content);

    }

    private String getContent() {
        String response = "";
//        String dateStart = getDate().get("monthAgo");
//        String dateEnd = getDate().get("today");
//        dateStart = MessageFormat.format("''{0}''", dateStart);
//        dateEnd = MessageFormat.format("''{0}''", dateEnd);
        String obj = "mkt_tp_cd=1&isu_cdnm=All&isu_cd=&isu_nm=&isu_srt_cd=&strt_dd=20201220&end_dd=20201230&pagePath=%2Fcontents%2FGLB%2F05%2F0501%2F0501120600%2FGLB0501120600.jsp&code=gRv3qxTLY3PfBaFw%2FxSa%2Bk5pUz3yb5NE11wDz1HH3inyEaI8W00Azc4YA2O%2Fbf8h5qKm4V1erZgsOW3mGUBoSjJK5HeSRdABCUvGD9egjtLbBenRvOdipYFFGtSyDN3GLsJIO3Uz0uzVjcCMAdiez0HxaJGdXuklBLh9ntHVM2XyFlg0YnTxIdUWX3%2BZbA7Y";
        try {
            URL url = new URL(null,"http://global.krx.co.kr/contents/GLB/99/GLB99000001.jspx", new sun.net.www.protocol.https.Handler());
            HttpsURLConnection postConnection = (HttpsURLConnection) url.openConnection();
            postConnection.setConnectTimeout(5000);
            postConnection.setReadTimeout(10000);
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
            postConnection.setRequestProperty("accept-language", "en-US,en;q=0.9");
            postConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            postConnection.setRequestProperty("x-requested-with", "XMLHttpRequest");
            postConnection.setRequestProperty("cookie", "SCOUTER=z79j613sf2lmhh; __utmz=88009422.1609154622.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmc=88009422; JSESSIONID=6595A39529A245165EB875C463E107CB.103tomcat2; __utma=88009422.1537191816.1609154622.1609310585.1609312508.6; __utmb=88009422.2.10.1609312508");
            postConnection.setRequestProperty("referrer", "http://global.krx.co.kr/contents/GLB/05/0501/0501120600/GLB0501120600.jsp");
            postConnection.setRequestProperty("referrerPolicy", "strict-origin-when-cross-origin");
            postConnection.setRequestProperty("mode", "cors");

            postConnection.setDoOutput(true);
            OutputStream os = postConnection.getOutputStream();
            os.write(obj.getBytes());
            os.flush();
            os.close();
            int statusCode = postConnection.getResponseCode();
            if (statusCode > 299) throw new RuntimeException("BRAZIL RESPONSE CODE :" + statusCode);

            try (InputStream inputStream = postConnection.getInputStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder line = new StringBuilder();
                String line1;
                while ((line1 = reader.readLine()) != null) {
                    line.append(line1);
                }
                response = line.toString();
            }
        } catch (Exception ex) {
            System.out.println(ex.fillInStackTrace().toString());
        }
        return response;
    }


    private void readCsvFile() throws IOException {
        String path = "src/main/static/sdsKorea.csv";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(path)))) {
            String line = "";
            while (!((line = br.readLine()) == null)) {
                consumeLine(line);
            }
        }

    }

    private void consumeLine(String line) {

        if (line.startsWith("Date")) return;
        String[] split = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
//        System.out.println(MessageFormat.format("{0},{1},{2},{3},{4},{5},{6},{7}", split[0], split[1], split[2], split[3], split[4], split[5], split[6], split[7]));
        KoreaDisclosure korea = new KoreaDisclosure();
        korea.setPositionDate(split[0]);
        korea.setIsin(split[1]);
        korea.setIssuerName(split[2]);
        korea.setShareShortedVolume(split[3]);
        korea.setShareOutstanding(split[4]);
        korea.setShareShortedValue(split[5]);
        korea.setMarketCap(split[6]);
        korea.setShortPositionPercentage(split[7]);
        disclosures.put(split[0] + split[2], korea);
        count++;

    }
}
