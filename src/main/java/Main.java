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
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {
        new Random().ints(1,10)
                .distinct()
                .limit(10)
                .forEach(System.out::println);
//try {
//        Belgium.backup();
//}catch (CssDataNullException ex){
//    System.out.println(ex.getMessage());
//}


//downloadExcel("https://www.iiroc.ca/Documents/2021/89c51534-baa6-4d48-a11c-9a2ba195b2cb_en.xls");
//    }
//    private static void readExcel(InputStream input) throws IOException, InvalidFormatException {
//        Workbook wb = WorkbookFactory.create(input);
//        Sheet sheet = wb.getSheetAt(0);
//        DataFormatter dataFormatter = new DataFormatter();
//        Iterator<Row> rowIterator = sheet.rowIterator();
//        while (rowIterator.hasNext()) {
//            Row row = rowIterator.next();
//            Iterator<Cell> cellIterator = row.cellIterator();
//            ArrayList<String> rowData = new ArrayList<>();
//            while (cellIterator.hasNext()) {
//                if (row.getRowNum() == 1) break;
//                Cell cell = cellIterator.next();
//                String cellValue = dataFormatter.formatCellValue(cell);
//                rowData.add(cellValue);
//            }
//            if (rowData.size() == 5) {
//                System.out.println(MessageFormat.format("{0},{1},{2},{3},{4}",rowData.get(0),rowData.get(1),rowData.get(2),rowData.get(3),rowData.get(4)));
//            }
//        }
    }

//    private static void downloadExcel(String url) {
//        try {
//            URL link = new URL(url);
//            HttpsURLConnection getConnection = (HttpsURLConnection) link.openConnection();
//            getConnection.setConnectTimeout(10000);
//            getConnection.setReadTimeout(10000);
//            getConnection.setRequestMethod("GET");
//            getConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
//            try (InputStream inputStream = getConnection.getInputStream()) {
//                System.out.println(url);
//                readExcel(inputStream);
//            }
//        } catch (IOException | InvalidFormatException ex) {
//            ex.getCause();
//        }

//    }
    }




