import services.SDS.Korea;
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
        String res = getDayStringOld("2020/12/28");
        System.out.println(res);
    }

    public static String getDayStringOld(String date) throws ParseException {
        String outputDate=null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        Date dt1 = null;
        try {
            dt1 = format1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format2 = new SimpleDateFormat("EEEE");
        String finalDay = format2.format(dt1);
        Calendar c = Calendar.getInstance();
        c.setTime(dt1);
        if (finalDay.equals("Thursday") || finalDay.equals("Friday")) {
            c.add(Calendar.DATE, 4);
            outputDate = format1.format(c.getTime());
        } else {
            c.add(Calendar.DATE, 2);
            outputDate = format1.format(c.getTime());
        }
        return outputDate;
    }


}
