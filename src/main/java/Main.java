import services.SDS.Korea;
import services.Testable;
import services.USA_form;

public class Main {

    public static void main(String[] args) throws Exception {
//        USA_form.scrape();
//    Testable.scrape();
//        Testing.scrapeData();
        Korea korea=new Korea();
        korea.scrapeData();
    }



}
