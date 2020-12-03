package services;

import utils.ParameterUtils;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BR {
    public static void scrape() throws ParseException {

//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
        String time = "2020-11-30T21:16:23.000Z";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        Date parsed = sdf.parse(time);

//        sdf.setTimeZone(TimeZone.getTimeZone("Etc/GMT-10"));

//        String dt = ParameterUtils.getDateInYourFormat(sdf.format(parsed), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd hh:mm:ss a");
//        System.out.println("Australia :" + dt);
//        Arrays.stream(TimeZone.getAvailableIDs()).forEach(p-> System.out.println(p));
//Here you set to your timezone
//        sdf.setTimeZone(TimeZone.getDefault());
//        String pt = ParameterUtils.getDateInYourFormat(sdf.format(parsed), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd hh:mm:ss a");
//        System.out.println("Pakistan :" + pt);
//        System.out.println("in db "+pt);

String j=ni(time);
        System.out.println(j);

}
private static String ni(String date){
    Date parsed = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    try {
        parsed = sdf.parse(date);
    } catch (ParseException e) {
//        logger.error(e.getMessage());
    }
    sdf.setTimeZone(TimeZone.getTimeZone("Etc/GMT-10"));
    return ParameterUtils.getDateInYourFormat(sdf.format(parsed), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd HH:mm:ss");
}
}

//Australia/ACT
//        Australia/Adelaide
//        Australia/Brisbane
//        Australia/Broken_Hill
//        Australia/Canberra
//        Australia/Currie
//        Australia/Darwin
//        Australia/Eucla
//        Australia/Hobart
//        Australia/LHI
//        Australia/Lindeman
//        Australia/Lord_Howe
//        Australia/Melbourne
//        Australia/NSW
//        Australia/North
//        Australia/Perth
//        Australia/Queensland
//        Australia/South
//        Australia/Sydney
//        Australia/Tasmania
//        Australia/Victoria
//        Australia/West
//        Australia/Yancowinna

