package services.sbb;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import java.io.*;
import java.math.BigDecimal;
import java.net.URL;

import java.util.*;

public class Japan {
    public  void scrape() throws IOException {

        File destFile = new File( "src/main/static","file.csv");
        try (WebClient webClient = new WebClient()) {

            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(true);
//
//            HtmlPs page = webClient.getPage("https://www.fi.se/en/our-registers/net-short-positions/");
//            page.

            Page downloadPage = webClient.getPage("https://www.fi.se/en/our-registers/net-short-positions/GetHistFile/");
            webClient.waitForBackgroundJavaScript(1000);
            try (InputStream contentAsStream = downloadPage.getWebResponse().getContentAsStream()) {
                try (OutputStream out = new FileOutputStream(destFile)) {
                    IOUtils.copy(contentAsStream, out);
                }
            }
//      System.out.println("Output written to " + destFile.getAbsolutePath());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        BufferedReader csvReader = new BufferedReader(new FileReader("src/main/static/file.csv"));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            // do something with the data
            System.out.println(Arrays.toString(data));
        }
        csvReader.close();
    }
    public void readExcelFile() throws IOException {
        String filePath = "src/main/static/file.xls";
        FileInputStream fis = new FileInputStream(new File(filePath));
//        URL url = getClass().getClassLoader().getResource(filePath);
//        if (url == null) {
//            System.out.println("url of the file not found");
//            return;
//        }

        try {
            POIFSFileSystem fs = new POIFSFileSystem(fis);
            XSSFWorkbook wb = new XSSFWorkbook(String.valueOf(fs));
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;

            int rows; // No of rows
            rows = sheet.getPhysicalNumberOfRows();

            int cols = 0; // No of columns
            int tmp = 0;

            // This trick ensures that we get the data properly even if it doesn't start from first few rows
            for(int i = 0; i < 10 || i < rows; i++) {
                row = sheet.getRow(i);
                if(row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if(tmp > cols) cols = tmp;
                }
            }

            for(int r = 0; r < rows; r++) {
                row = sheet.getRow(r);
                if(row != null) {
                    for(int c = 0; c < cols; c++) {
                        cell = row.getCell((short)c);
                        if(cell != null) {
                            System.out.println(cell);
                        }
                    }
                }
            }
        } catch(Exception ioe) {
            ioe.printStackTrace();
        }
    }


    private String convertCellToString(Cell cell) {
        if (cell == null)
            return null;
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        }
        return cell.toString();
    }

    private  void saveRow(Row row) {
        String positionHolder = convertCellToString(row.getCell(0));
        String issuerName = convertCellToString(row.getCell(1));
        String isin = convertCellToString(row.getCell(2));
        String netShortingPosition = convertCellToString(row.getCell(3))
                .replace("\"", "").replace(",", ".");
        String positionDate = convertCellToString(row.getCell(4));
        String netShortPositionAsText = null;
        BigDecimal netShortPosition = new BigDecimal("0");
        try {
            netShortPosition = new BigDecimal(netShortingPosition);
        } catch (NumberFormatException ignore) {
            netShortPositionAsText = netShortingPosition;
        }


        System.out.println(positionHolder);

        System.out.println(issuerName);

        System.out.println(netShortPosition);
        System.out.println(isin);
    }}