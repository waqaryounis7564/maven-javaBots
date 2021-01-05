package services.SDS;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.Iterator;

//
//import javax.net.ssl.HttpsURLConnection;
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.URL;
//
public class OtcCanada {
    public static void scrape() throws IOException, InvalidFormatException {
        try (InputStream inp = new FileInputStream(new File("src/main/static/canda.xls"))) {
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Now let's iterate over the columns of the current row
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    if (row.getRowNum() == 1) break;
                    Cell cell = cellIterator.next();
                    String cellValue = dataFormatter.formatCellValue(cell);
                    System.out.print(cellValue + "\t");

                }
                System.out.println("****");
            }


        }


    }
}


//
//    }
//    private String getContent(int page) {
//        String response = "";
////        String startDate = getDates().get("startDate");
////        String endDate = getDates().get("endDate");
////        String obj = "mkt_tp_cd=1&isu_cdnm=All&isu_cd=&isu_nm=&isu_srt_cd=&strt_dd=" + startDate + "&end_dd=" + endDate + "&pagePath=%2Fcontents%2FGLB%2F05%2F0501%2F0501120600%2FGLB0501120600.jsp&code=gRv3qxTLY3PfBaFw%2FxSa%2Bk5pUz3yb5NE11wDz1HH3inyEaI8W00Azc4YA2O%2Fbf8h5qKm4V1erZgsOW3mGUBoSjJK5HeSRdABCUvGD9egjtLbBenRvOdipYFFGtSyDN3GLsJIO3Uz0uzVjcCMAdiez0HxaJGdXuklBLh9ntHVM2XyFlg0YnTxIdUWX3%2BZbA7Y&curPage="+page;
//        try {
//            URL url = new URL("https://www.iiroc.ca/industry/marketmonitoringanalysis/Pages/consolidated-short-position-report.aspx");
//            HttpsURLConnection postConnection = (HttpsURLConnection) url.openConnection();
//            postConnection.setConnectTimeout(10000);
//            postConnection.setReadTimeout(10000);
//            postConnection.setRequestMethod("POST");
//            postConnection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
//            postConnection.setRequestProperty("cache-control", "max-age=0");
//            postConnection.setRequestProperty("accept-language", "en-US,en;q=0.9");
//            postConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
//            postConnection.setRequestProperty("sec-ch-ua", "\"Google Chrome\";v=\"87\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"87\"");
//            postConnection.setRequestProperty("cookie", "SCOUTER=z79j613sf2lmhh; __utmz=88009422.1609154622.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmc=88009422; JSESSIONID=6595A39529A245165EB875C463E107CB.103tomcat2; __utma=88009422.1537191816.1609154622.1609310585.1609312508.6; __utmb=88009422.2.10.1609312508");
//            postConnection.setRequestProperty("referrer", "http://global.krx.co.kr/contents/GLB/05/0501/0501120600/GLB0501120600.jsp");
//            postConnection.setRequestProperty("referrerPolicy", "strict-origin-when-cross-origin");
//            postConnection.setRequestProperty("mode", "cors");
//
//            postConnection.setDoOutput(true);
//            OutputStream os = postConnection.getOutputStream();
//            os.write(obj.getBytes());
//            os.flush();
//            os.close();
//            int statusCode = postConnection.getResponseCode();
//            if (statusCode > 299) throw new RuntimeException("BRAZIL RESPONSE CODE :" + statusCode);
//
//            try (InputStream inputStream = postConnection.getInputStream()) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//                StringBuilder line = new StringBuilder();
//                String line1;
//                while ((line1 = reader.readLine()) != null) {
//                    line.append(line1);
//                }
//                response = line.toString();
//            }
//        } catch (Exception ex) {
//            System.out.println(ex.fillInStackTrace().toString());
//        }
//        return response;
//    }
//}
