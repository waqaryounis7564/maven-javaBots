import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.Calendar;
import java.util.Date;

public class Testing {
public static void  scrapeData() throws ParseException {

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    Date dat = new Date();
    String todate = dateFormat.format(dat);

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -4);
    Date todate1 = cal.getTime();
    String fromdate = dateFormat.format(todate1);


    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date startDate = formatter.parse(fromdate);
    Date endDate = formatter.parse(todate);
//    Here's the legacy java.util.Calendar approach in case you aren't on Java8 yet:

    Calendar start = Calendar.getInstance();
    start.setTime(startDate);
    Calendar end = Calendar.getInstance();
    end.setTime(endDate);

    for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
        // Do your job here with `date`.
        System.out.println(date);
    }
}
}
