import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Header {
    public static void scrape() throws IOException {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36 Edg/83.0.478.44";
        String url="https://api.sgx.com/announcements/v1.1/securitylist";
        Connection.Response response = Jsoup.connect(url)
                .userAgent(userAgent)
                .followRedirects(true)
                .ignoreHttpErrors(true)
                .ignoreContentType(true)
                .header("Accept", "application/json")
                .header("Accept-Language", "en-US,en;q=0.9").
                        followRedirects(false).timeout(15000)
                .method(Connection.Method.GET)
                .execute();


        Map<String, String> headers =response.headers();

        System.out.println(response.hasHeader("authorizationtoken"));
    }
}
