package services;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.MalformedURLException;

public class Sweden {
public  static void scrapeData(){
    try(WebClient webClient=new WebClient()){
        HtmlPage homePage=webClient.getPage("https://www.fi.se/sv/vara-register/blankningsregistret/");
        DomElement liveLink=homePage.querySelector("#aktlist");
        liveLink.click();

    } catch (IOException e) {
        e.printStackTrace();
    }

}
}
