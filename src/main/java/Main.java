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
        String res=getDayStringOld("2020/12/24");
        System.out.println(res);
    }
    public static String getDayStringOld(String  date) throws ParseException {
        SimpleDateFormat format1=new SimpleDateFormat("yyyy/MM/dd");
        Date dt1= null;
        try {
            dt1 = format1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2=new SimpleDateFormat("EEEE");
        String finalDay=format2.format(dt1);

        Calendar c = Calendar.getInstance();
        c.setTime(dt1); // Now use today date.
        c.add(Calendar.DATE, 2); // Adding 5 days
        String output = format1.format(c.getTime());
        System.out.println(format2.format(format1.parse(output)));

if(finalDay.equals("Thursday")){
    c.add(Calendar.DATE, 2);
}
        return finalDay;
    }


}
