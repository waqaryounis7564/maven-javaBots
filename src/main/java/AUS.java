

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

public class AUS {
    public static void scrapeDate() throws IOException {
        downloadPage("https://asic.gov.au/Reports/Daily/2010/06/RR20100617-001-SSDailyAggShortPos.csv");
    }

    private static void downloadPage(String link) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(link).openConnection());
        httpURLConnection.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36");
        try (InputStream inputStream = httpURLConnection.getInputStream()) {

            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_16)) {

                try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                    String line = bufferedReader.readLine();
                    while (line != null) {
                        if (line.startsWith("Product")) {
                            line = bufferedReader.readLine();
                            continue;
                        }
                        line=String.join(",", line.split("\t"));
                        line = bufferedReader.readLine();
                        System.out.println(line);
//
//                        consumeLine(line);
                        line = bufferedReader.readLine();
                    }
                }
            }
        }

    }


    private static List<String> getUrls() {
        List<String> urls = new ArrayList<>();
        for (int days = 1; days <= 3729; days++) {

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

}

