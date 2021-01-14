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

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws Exception {
        String url = "https://market-reports.thecse.com/CSEListed/SemiMonthly/ShortPositions/CSEListed.SemiMonthly.Market.ShortPositions.2012-09-15.xls";
        downloadExcel(url);
    }

    private static void downloadExcel(String url) {
        try {
            URL link = new URL(url);
            HttpsURLConnection getConnection = (HttpsURLConnection) link.openConnection();
            getConnection.setConnectTimeout(10000);
            getConnection.setReadTimeout(10000);
            getConnection.setRequestMethod("GET");
            getConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            try (InputStream inputStream = getConnection.getInputStream()) {
                readExcel(inputStream);
            }
        } catch (IOException | InvalidFormatException ex) {
            ex.getCause();
        }

    }

    private static void readExcel(InputStream input) throws IOException, InvalidFormatException {
        Workbook wb = WorkbookFactory.create(input);
        Sheet sheet = wb.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            ArrayList<String> rowData = new ArrayList<>();
            while (cellIterator.hasNext()) {

                if (row.getRowNum() <= 2) break;

                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                    rowData.add(cellValue);


            }
            if (rowData.size() == 4 && !rowData.stream().limit(2).allMatch(String::isEmpty)) {
                String shorted = rowData.get(2).replace("(", "").replace(")", "").replace(",", "").trim();
                String netChange = rowData.get(3).replace("(", "").replace(")", "").replace(",", "").trim();
                if (netChange.toLowerCase().contains("net")) continue;
                if (shorted.equals("- 0") && netChange.equals("- 0") || shorted.equals("0") && netChange.equals("0")) continue;
//                if (shorted.equals("- 0") && !netChange.equals("- 0")) {
//                    shorted = "0";
//                }
                System.out.println("issuer name-->" + rowData.get(0));
                System.out.println("ticker-->" + rowData.get(1));
                System.out.println("no_of_shorted_shares_position-->" + shorted);
                System.out.println("net change-->" + netChange);
                System.out.println("//////////////////////////////");

            }
        }


    }

    /*
    * ISSUE = Issuer name
Symbol= Ticker
No of shares = no_of_shorted_shares_position
Market = CSE hee rakhni hai
NET CHANGE = Net change
    *
    * */

//\d{4}-\d{2}-\d{2}

}

