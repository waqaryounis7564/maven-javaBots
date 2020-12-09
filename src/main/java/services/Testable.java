package services;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.StringJoiner;

public class Testable {

    public static void scrape() {
    String name="waqar";
//        StringJoiner s= new StringJoiner(">");
//         s.add(name);

        String join="'"+ StringUtils.join(name)+"'";
        String join2="'"+name +"'";

        System.out.println(MessageFormat.format("''{0}''",name));


    }

}




