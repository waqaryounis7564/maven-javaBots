
import org.jsoup.Jsoup;
import services.representitives.comittes.Agriculture;


public class Main {


    public static void main(String[] args) throws Exception {
        Agriculture agriculture=new Agriculture();
        String scrape = agriculture.scrape();
        System.out.println(Jsoup.parse( scrape).html());

    }


}




