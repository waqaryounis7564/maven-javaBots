import services.Senator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class Testing {
    public static void scrapeData() throws IOException {

        String month = getMonth(10);
        System.out.println(month);
    }

    private static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }
}


