

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AUS {
    private List<AUSDisclosure> disclosures;

    public static void scrapeDate() throws IOException {
        getUrls().forEach(ur -> {
            try {
                downloadPage(ur);
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        });

    }

    private static void downloadPage(String link) throws IOException, ParseException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(link).openConnection());
        httpURLConnection.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36");
        if (httpURLConnection.getResponseCode() == 404) return;
        try (InputStream inputStream = httpURLConnection.getInputStream()) {

            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_16)) {

                try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                    String line = bufferedReader.readLine();
                    while (line != null) {
                        if (line.startsWith("Product")) {
                            line = bufferedReader.readLine();
                            continue;
                        }
                        processRow(line, link);
                        line = bufferedReader.readLine();
                    }
                }
            }
        }
    }

    private static void processRow(String row, String url) throws ParseException {
        String line = String.join(",", row.split("\t"));
        String[] disclosure = line.split(",");
        AUSDisclosure ausDisclosure = new AUSDisclosure();
        ausDisclosure.setUrl(url);
        ausDisclosure.setIssuer(disclosure[0]);
        ausDisclosure.setIssuerCode(disclosure[1]);
        ausDisclosure.setTotalShortedShares(disclosure[2]);
        ausDisclosure.setTotalShareOutstanding(disclosure[3]);
        ausDisclosure.setPercentOfTotalShareOutstanding(disclosure[4]);
        ausDisclosure.setPositionDate(getPositionDate(url));
        System.out.println(ausDisclosure.getUrl());
        System.out.println(ausDisclosure.getIssuer());
        System.out.println(ausDisclosure.getIssuerCode());
        System.out.println(ausDisclosure.getTotalShortedShares());
        System.out.println(ausDisclosure.getTotalShareOutstanding());
        System.out.println(ausDisclosure.getPercentOfTotalShareOutstanding());
        System.out.println(ausDisclosure.getPositionDate());


        System.out.println("------------------------");
    }


    private static List<String> getUrls() {
        List<String> urls = new ArrayList<>();
        int daysin10years = 3729;
        for (int days = 1; days <= daysin10years; days++) {

            String date = new SimpleDateFormat("yyyy-MM-dd").format(ParameterUtils.getDateBeforeDays(new Date(), days));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");


            Date parsedDate = null;
            try {
                parsedDate = simpleDateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String date1 = sdf1.format(parsedDate);
            String date2 = sdf2.format(parsedDate);
            String url = "https://asic.gov.au/Reports/Daily/" + date1 + "/RR" + date2 + "-001-SSDailyAggShortPos.csv";
            urls.add(url);

        }
        return urls;
    }

    private static String getPositionDate(String url) throws ParseException {
        Pattern pattern = Pattern.compile("(20\\d{2})(\\d{2})(\\d{2})");
        Matcher matcher = pattern.matcher(url);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        String positionDate = null;
        if (matcher.find()) {
            parsedDate = simpleDateFormat.parse(matcher.group());
            positionDate = sdf1.format(parsedDate);
        }
        return positionDate;
    }

}

