import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
public static void  scrapeData()  {
String div1="<table><tbody> <tr><td> Pharma Partners, LLC\n" +
        "                                                <div class=\"text-muted\">\n" +
        "                                                    <em>Company:</em> Pharma Partners, LLC\n" +
        "                                                        &nbsp;(Englewood, CO)\n" +
        "                                                </div>\n" +
        "                                                <div class=\"text-muted\"><em>Description:</em>&nbsp;The LLC funds research and clinical trials on a new class of drugs, metalloporphyrins. The lead drugs are BMX-010 and BMX-001.</div>                                 \n" +
        "                                    </td> </tr> </tbody> </table>";

String div2="<table> <tbody><tr><td>\n" +
        "                                        \n" +
        "                                            E. I. du Pont de Nemours and Company (Exchanged)\n" +
        "                                            <br>\n" +
        "                                            DowDuPont Inc. (Received)\n" +
        "                                        \n" +
        "                                        \n" +
        "                                        \n" +
        "                                    </td></tr></tbody></table>";
Document doc = Jsoup.parse(div2);
    System.out.println(doc.select("td").text());
}
}
