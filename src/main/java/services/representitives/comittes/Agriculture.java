package services.representitives.comittes;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Agriculture {


    public String scrape() {
        StringBuilder response = new StringBuilder();
        URL src = null;
        HttpURLConnection httpConn = null;
        InputStream responseStream = null;
        String srcUrl = "https://www.sec.gov/Archives/edgar/data/1458023/000121390021009904/0001213900-21-009904-index.htm";
        try {
            URL url = new URL(srcUrl);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36 Edg/90.0.818.62");
            httpConn.setRequestProperty("https.proxyHost", "121.125.54.228");
            httpConn.setRequestProperty("http.proxyPort", "3128");
            responseStream =  httpConn.getInputStream();
            try (InputStreamReader inputStreamReader = new InputStreamReader(responseStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            ) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        String result = response.toString();
        return result;

    }
}
