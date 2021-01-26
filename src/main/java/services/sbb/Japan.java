package services.sbb;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class Japan {
    public static void scrape() {

        String url = "https://disclosure.edinet-fsa.go.jp/E01EW/BLMainController.jsp?uji.verb=W1E63021CXP002005BLogic&uji.bean=ee.bean.parent.EECommonSearchBean&PID=W1E63021&TID=W1E63021&SESSIONKEY=1611655571240&lgKbn=2&pkbn=0&skbn=1&dskb=&askb=&dflg=0&iflg=0&preId=1&sec=&scc=&shb=&snm=&spf1=1&spf2=1&iec=&icc=&inm=&spf3=1&fdc=&fnm=&spf4=1&spf5=2&otd=220&otd=230&cal=1&era=H&yer=&mon=&psr=1&pfs=4&row=100&idx=0&str=&kbn=1&flg=&syoruiKanriNo=&s=S100HXBO";
        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            Page downloadPage = webClient.getPage("https://disclosure.edinet-fsa.go.jp/E01EW/download?uji.verb=W0EZA104CXP002006BLogic&uji.bean=ee.bean.parent.EECommonSearchBean&PID=W1E63021&SESSIONKEY=1611657447526&lgKbn=2&pkbn=0&skbn=1&dskb=&askb=&dflg=0&iflg=0&preId=1&sec=&scc=&shb=&snm=&spf1=1&spf2=1&iec=&icc=&inm=&spf3=1&fdc=&fnm=&spf4=1&spf5=2&otd=220&otd=230&cal=1&era=H&yer=&mon=&psr=1&pfs=4&row=100&idx=0&str=&kbn=1&flg=&syoruiKanriNo=&no=S100KKXX");
//            HtmlAnchor docFile = page.querySelector("#control_object_class1 > div > div.result > table > tbody > tr:nth-child(2) > td:nth-child(7) > div > a");
//            docFile.click();
            webClient.waitForBackgroundJavaScript(1000);
//            Page downloadPage = webClient.getCurrentWindow().getEnclosedPage();
            File destFile = new File("/tmp", "myfile.zip");
            try (InputStream contentAsStream = downloadPage.getWebResponse().getContentAsStream()) {
                try (OutputStream out = new FileOutputStream(destFile)) {
                    IOUtils.copy(contentAsStream, out);
                }
            }
            System.out.println("Output written to " + destFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
