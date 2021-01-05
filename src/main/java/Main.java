import services.SDS.Korea;
import services.SDS.OtcCanada;
import services.Testable;
import services.USA_form;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws Exception {
        OtcCanada otcCanada=new OtcCanada();
        otcCanada.scrape();

    }




}
