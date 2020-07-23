import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Representatives {
    public static void scrapeDAta() throws IOException {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36 Edg/83.0.478.44";
        String url = " https://disclosures-clerk.house.gov/PublicDisclosure/FinancialDisclosure/ViewMemberSearchResult";
        try (final WebClient webClient = new WebClient();) {
            URL ur = new URL(url);
            WebRequest requestSettings = new WebRequest(ur, HttpMethod.POST);
            requestSettings.setRequestBody("LastName=&FilingYear=2020&State=&District=");
            Page response = webClient.getPage(requestSettings);

            String src = response.getWebResponse().getContentAsString();
            Document document = Jsoup.parse(src);
            String href = "https://disclosures-clerk.house.gov/";
            Elements rows = document.select("tbody>tr");

            for (Element row : rows) {

                String pdfUrl = href + row.select("td:nth-child(1)>a").attr("href");
                String name = row.select("td:nth-child(1)").text();
                String office = row.select("td:nth-child(2)").text();
                String fillingYear = row.select("td:nth-child(3)").text();
                String filling = row.select("td:nth-child(4)").text();
                if (filling.contains("Term. Exemption")) continue;
                PdfManager pdfManager = new PdfManager();

                Page pdf = webClient.getPage(pdfUrl);
                if (pdf.getWebResponse().getContentType().equals("application/pdf")) {
                    System.out.println("Pdf downloaded");
                    IOUtils.copy(pdf.getWebResponse().getContentAsStream(),
                            new FileOutputStream("transaction.pdf"));
                    System.out.println("Pdf file created");

                }
                pdfManager.setFilePath("transaction.pdf");
                try {
                    String text = pdfManager.toText();

                    System.out.println(text);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
//                try (PDDocument pdDocument = PDDocument.load(new File("transaction.pdf"))) {
//                    pdDocument.getClass();
//
//                    if (!pdDocument.isEncrypted()) {
//
//                        PDFTextStripper tStripper = new PDFTextStripper();
//                       // stripper.setSortByPosition(true);
//
//                      //  PDFTextStripper tStripper = new PDFTextStripper();
//
//                        String pdfFileInText = tStripper.getText(pdDocument);
//                        //System.out.println("Text:" + st);
//
//                        // split by whitespace
//                        String[] lines = pdfFileInText.split("\\r?\\n");
//                        for (String line : lines) {
//                            System.out.println(line);
//                        }
//                    }
//                }
//                    PDFTextStripperByArea stripper = new PDFTextStripperByArea();
//                    stripper.setSortByPosition(true);
//                    PDFTextStripper tStripper = new PDFTextStripper();
//                    String stringPdf = tStripper.getText(pdDocument);
//                    String[] lines = stringPdf.split("\\n");
//                    String namePattern = "Name:\\s+(.+)";
//                    String filingPattern="Filing\\sID+(.+)";
//                    Pattern p = Pattern.compile(filingPattern);
//                    String price = "";
//                    for (String line : lines) {
//                        Matcher m = p.matcher(line);
//                        if (m.find()) {
//                            price = m.group(1);
//                        }
//                    }
//                    if (!price.isEmpty()) {
//                        System.out.println("NAme found: " + price);
//                    } else {
//                        System.out.println("Name not found"+" "+pdfUrl);
//                    }
//                }

//                System.out.println(name);
//                System.out.println(href);
//                System.out.println(office);
//                System.out.println(fillingYear);
//                System.out.println(filling);
                            System.out.println("---------------------------");
                        }


                    }
                }
            }


