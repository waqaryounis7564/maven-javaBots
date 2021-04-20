import org.apache.pdfbox.debugger.streampane.Stream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import services.SDS.Canada;
import services.SDS.Korea;
import services.SDS.OtcCanada;
import services.Testable;
import services.USA_form;
import services.common.CssDataNullException;
import services.sbb.Belgium;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {


    public static void main(String[] args) throws Exception {
        nopes();


    }

    private static void nopes() {
        Random randNum = new Random();
        Set<Integer> set = new LinkedHashSet<Integer>();
        while (set.size() <17) {
            set.add(randNum.nextInt(17) + 1);
        }
       set.forEach(d-> System.out.println(d));
    }

}