package services;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Otc_USA {

    public static void scrapeData() throws MalformedURLException {
        try (WebClient webClient = new WebClient()) {
            URL url = new URL("http://regsho.finra.org/regsho-September.html");
            WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);
            requestSettings.setAdditionalHeader("User-Agent: ", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36 Edg/85.0.564.51");
            Page homepage = webClient.getPage(requestSettings);
            Document document = Jsoup.parse(homepage.getWebResponse().getContentAsString());
            List<Elements> li=new ArrayList<>();
            int domChild;
            for ( domChild= 14; domChild <= 24;) {

            Elements elements = document.select("ul:nth-child("+domChild+")" );
                System.out.println(elements);
                li.add(elements);
                domChild+=2;
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------");
            li.forEach(l-> System.out.println(l));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
