package services.representitives.comittes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;

public class Appropriations {
public  static void scrape() throws IOException {
    Document document = Jsoup.connect("https://appropriations.house.gov/about/membership").get();
   /*
   it will need to work on majority and minrity for saving in DB
   * */
    Elements democaratic = document.getElementsByClass("each-member");
    democaratic.forEach(t-> System.out.println(t.text()));

}
}
