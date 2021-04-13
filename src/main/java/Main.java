
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import services.America;
import services.representitives.comittes.*;
import services.sbb.Belgium;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;

import java.text.MessageFormat;

import java.util.*;

class Parent {
    private final String name;

    Parent(String name) {
        this.name = name;
    }
}

class Child extends Parent {
    private String name;

    Child(String name) {
        super(name);
    }
}

public class Main {

    public static void main(String[] args) throws Exception {
        Belgium.crawl();

//        Agriculture america = new Agriculture();
//        america.scrape();
//        OversightandReform.scrape();

//        WebDriver driver = new ChromeDriver();
//        driver.navigate().to("https://www.londonstockexchange.com/news-article/BGHS/Cancellation of euro shares held in treasury/14834659");
//        driver.manage().window().maximize();
//        String content=driver.getPageSource();
//        System.out.println(content);
//        driver.close();
//        driver.quit();

//        Document document = Jsoup.connect("https://www.londonstockexchange.com/news-article/BGHS/Cancellation of euro shares held in treasury/14834659").get();

//        System.out.println(document.body().select("*"));
//ScreenShot();
    }
//    *[@id="news-article-content"]/div[1]

    private static void craw() {
        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            HtmlPage page = webClient.getPage("https://www.londonstockexchange.com/news-article/BGHS/Cancellation of euro shares held in treasury/14834659");
            webClient.waitForBackgroundJavaScript(2000);
            DomElement btn = page.querySelector("#news-article-content > div.news-article-content-wrapper > div.news-intro > div > div.share-wrapper.mobile-flex-wrapper > span > button");
            HtmlPage click = btn.click();
            System.out.println(click.querySelector("body").asXml());

//            List<Object> xPath = page.getByXPath("/html/body/app-root/app-handshake/div/app-page-content/app-news-article-content/section/div[1]");
//            System.out.println(xPath.get(0));


        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
                if (row.getRowNum() == 1) break;
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                rowData.add(cellValue);
            }
            if (rowData.size() == 5) {
                System.out.println(MessageFormat.format("{0},{1},{2},{3},{4}", rowData.get(0), rowData.get(1), rowData.get(2), rowData.get(3), rowData.get(4)));
            }
        }
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
                System.out.println(url);
                readExcel(inputStream);
            }
        } catch (IOException | InvalidFormatException ex) {
            ex.getCause();
        }

    }
}




