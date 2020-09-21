package services;

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

import java.io.*;
import java.net.URL;

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
                if (filling.contains("Term. Exemption") || filling.contains("Termination")) continue;

                Page pdf = webClient.getPage(pdfUrl);
                if (pdf.getWebResponse().getContentType().equals("application/pdf")) {
//                    System.out.println("Pdf downloaded");
                    IOUtils.copy(pdf.getWebResponse().getContentAsStream(),
                            new FileOutputStream("transaction.pdf"));
//                    System.out.println("Pdf file created");
                }

                try (PDDocument pdDocument = PDDocument.load(new File("transaction.pdf"))) {

                    PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                    stripper.setSortByPosition(true);
                    PDFTextStripper tStripper = new PDFTextStripper();
                    String stringPdf = tStripper.getText(pdDocument);
                    String[] lines = stringPdf.split("^(?!.* {2}).+");

                    if (lines.length > 1) {


                        String result = lines[1].replaceAll("[\\t\\n\\r]+", " ");
//                        System.out.println(result);
                        int startIndex = result.indexOf("?") + 1;
                        if (startIndex > 1) {
                            int endIndex = result.indexOf("*");

//                        System.out.println(result);
//                        int end = result.indexOf("]");
                            String pr = result.substring(startIndex, endIndex);
//                            System.out.println(pr);
                            String[] anns1 = pr.split(" ");
                            int count = 0;
                            if (pr.contains("gfedc")) {
                                for (String ann : anns1) {
                                    if (ann.contains("gfedc")) {
                                        count++;
                                    }
                                }
                                String[] anns2 = pr.split(" gfedc ", count);
                                for (String ann : anns2) {
                                    if (ann.toLowerCase().contains("id owner ") || ann.toLowerCase().startsWith("id owner")) {
                                        ann = ann.replace("ID Owner Asset Transaction Type Date Notification Date Amount Cap. Gains > $200?", "");
                                    }
//                                    System.out.println(ann.indexOf("["));
                                    ;
                                    System.out.println(ann);
                                }
                            } else {
                                for (String ann : anns1) {
                                    if (ann.contains("gfedcb")) {
                                        count++;
                                    }
                                }
                                String[] anns2 = pr.split(" gfedcb ", count);
                                for (String ann : anns2) {
                                    if (ann.toLowerCase().contains("id owner ") || ann.toLowerCase().startsWith("id owner")) {
                                        ann = ann.replace("ID Owner Asset Transaction Type Date Notification Date Amount Cap. Gains > $200?", "");
                                    }
//                                    System.out.println(ann.indexOf("["));
                                    System.out.println(ann);
                                }
                            }

                            pdDocument.close();
                            System.out.println(pdfUrl);
                        }
                    }
                    System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
//                        Pattern p = Pattern.compile("\\$\\d+(?:,+\\d+)..........");
//                        Matcher m = p.matcher(result);
//                        if (m.find()) {
//                            System.out.println(m.group());
//                        }
//                    int endIndex = 0;
//                    for (String line : lines) {
//                        endIndex++;
//                        if (line.contains("* For the complete list of asset type abbreviations,")) break;
//                    }
//                    for (int line = 2; line <= endIndex - 1; line++) {
//                        System.out.println(lines[line]);
//                    }

//                    if (lines.length > 14) {
//                        String[] newArray = Arrays.copyOfRange(lines, 14, endIndex - 1);
//                        for (String ln : newArray) {
//
//                        }
//                    }
                    //}

                }

            }
        }
    }
}



