package services.SDS;
import org.jsoup.Connection;
import org.jsoup.Jsoup;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Finland {
    public static void crawl() throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "*/*");
        headers.put("Accept-Language", "en-US,en;q=0.9");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Request-Context", "appId=cid-v1:8f51666e-7798-4375-9563-73d484cc07e8");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36 Edg/92.0.902.73");
        headers.put("Origin", "https://www.finanssivalvonta.fi");
        headers.put("Sec-Fetch-Site", "same-origin");
        headers.put("Sec-Fetch-Mode", "cors");

        headers.put("Referer", "https://www.finanssivalvonta.fi/en/capital-markets/issuers-and-investors/Managers-transactions/shortselling/");
//        String body = z;
//        Connection.Response execute = Jsoup.connect("https://www.finanssivalvonta.fi/api/shortselling/datatable/current/export").headers(headers).requestBody(body).method(Connection.Method.POST).ignoreContentType(true).execute();


//        System.out.println(Arrays.toString(execute.body().split(";")) ) ;

    }
}
