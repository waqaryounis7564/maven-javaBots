import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Representatives {
    public static void scrapeDAta() throws IOException {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36 Edg/83.0.478.44";
        String url = "https://disclosures-clerk.house.gov/PublicDisclosure/FinancialDisclosure/ViewMemberSearchResult";
        List<RepresentativeTrades> representativeTradesList = new ArrayList<>();
        try (final WebClient webClient = new WebClient()) {
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
                String fillingIdText = pdfUrl.split("/")[7];
                String fillingId = fillingIdText.substring(0, fillingIdText.length() - 4);
                if (filling.contains("Term. Exemption") || filling.contains("Termination")) continue;

                Page pdf = webClient.getPage(pdfUrl);
                File file = File.createTempFile("theFile", ".pdf");

                if (pdf.getWebResponse().getContentType().equals("application/pdf")) {
                    try (OutputStream outputStream = new FileOutputStream(file)) {
                        outputStream.write(IOUtils.toByteArray(pdf.getWebResponse().getContentAsStream()));
                        outputStream.flush();
                    }
                }
                try (PDDocument pdDocument = PDDocument.load(file)) {
                    PDFTextStripper tStripper = new PDFTextStripper();
                    String stringPdf = tStripper.getText(pdDocument);
                    String headerLineSplitter = "\\$200\\?";
                    final String lineSeparator = tStripper.getLineSeparator();
                    // Below are possible trade separator so far.
                    String[] tradeSeparators = {
                            "FIlINg STATuS: New",
                            "FILINg STATUS: New",
                            "FILINg STATUS: Amended",
                            "FIlINg STaTuS: New",
                            "FILINg STaTUS: New",
                            "FILING STaTUS: New",
                            "FILING STATUS: New",
                            "FIlINg sTaTus: New"};

                    String[] tradeLines = {};
                    boolean isAmended = false;
                    for (String sep : tradeSeparators) {
                        if (stringPdf.contains(sep)) {
                            if (sep.equals(tradeSeparators[2])) {
                                isAmended = true;
                            }
                            tradeLines = stringPdf.split(sep);
                            break;
                        }
                    }
                    //TODO If we run this in debug mode and we can any file which says trade lines are less than 1 then
                    //TODO We can find new trade seperator.
                    for (String tradeLine : tradeLines) {

                        String finalTradeLine = null;

                        //If trade line is first then skip first 13 lines.
                        if (tradeLine.equals(tradeLines[0])) {
                            String[] lines = tradeLine.split(lineSeparator);
                            if (lines[13].equals("$200?"))
                                finalTradeLine = String.join(" ", Arrays.copyOfRange(lines, 14, lines.length));
                            else
                                finalTradeLine = String.join(" ", Arrays.copyOfRange(lines, 15, lines.length));
                        } else {
                            Pattern pattern = Pattern.compile("\\d+");
                            finalTradeLine = Arrays.stream(tradeLine.split(lineSeparator)).filter(
                                    l -> !l.toLowerCase().startsWith("subholding") &&
                                            !l.toLowerCase().startsWith("description") &&
                                            !l.toLowerCase().startsWith("Filing I".toLowerCase()) &&
                                            !pattern.matcher(l.split(" ")[0]).matches()
                            ).collect(Collectors.joining(" "));
                        }
                        if (finalTradeLine.contains("For the complete"))
                            continue;

                        if (finalTradeLine.toLowerCase().startsWith("subholding of")) {
                            continue;
                        }

                        finalTradeLine = finalTradeLine.trim();
                        //TODO Here we will have trade.

                        String[] tradeSplitter = {"JT\\s", "DC\\s"};
                        String headerSplitter = "\\siD";
                        String theRow;
                        String desc = "";
                        if (finalTradeLine.split(tradeSplitter[1]).length > 1) {
                            theRow = finalTradeLine.split(tradeSplitter[1])[1].split(headerSplitter)[0];
                        } else if (finalTradeLine.split(tradeSplitter[0]).length > 1) {
                            desc = finalTradeLine.split(tradeSplitter[0])[0].split(headerSplitter)[0];
                            theRow = finalTradeLine.split(tradeSplitter[0])[1].split(headerSplitter)[0];
                        } else {
                            theRow = finalTradeLine.split(headerSplitter)[0];
                        }
                        RepresentativeTrades representativeTrades = new RepresentativeTrades();
                        if (isAmended) {
                            representativeTrades.setStatus("Amended");
                        } else representativeTrades.setStatus("New");
                        representativeTrades.setFillingId(fillingId);
                        representativeTrades.setSourceUrl(pdfUrl);
                        if (theRow.split("\\ss\\s").length > 1) {
                            String[] parts = theRow.split("\\ss\\s");
                            if (parts[1].split("\\s").length < 5) continue;

                            representativeTrades.setAssetName(parts[0]);
                            representativeTrades.setTransactionType("Purchase");
                            representativeTrades.setTransactionDate(parts[1].split("\\s")[0]);
                            representativeTrades.setFilingDate(parts[1].split("\\s")[1]);
                            representativeTrades.setValueRange(parts[1].split("\\s")[2] + " - " + parts[1].split("\\s")[4]);
                        } else if (theRow.split("\\ss\\s(partial)\\s").length > 1) {
                            String[] parts = theRow.split("\\ss\\s(partial)\\s");
                            if (parts[1].split("\\s").length < 5) continue;

                            representativeTrades.setAssetName(parts[0]);
                            representativeTrades.setTransactionType("Purchase");
                            representativeTrades.setTransactionDate(parts[1].split("\\s")[0]);
                            representativeTrades.setFilingDate(parts[1].split("\\s")[1]);
                            representativeTrades.setValueRange(parts[1].split("\\s")[2] + " - " + parts[1].split("\\s")[4]);
                        } else if (theRow.split("\\sE\\s").length > 1) {
                            String[] parts = theRow.split("\\sE\\s");
                            if (parts[1].split("\\s").length < 5) continue;

                            representativeTrades.setAssetName(parts[0]);
                            representativeTrades.setTransactionType("Purchase");
                            representativeTrades.setTransactionDate(parts[1].split("\\s")[0]);
                            representativeTrades.setFilingDate(parts[1].split("\\s")[1]);
                            representativeTrades.setValueRange(parts[1].split("\\s")[2] + " - " + parts[1].split("\\s")[4]);
                        } else if (theRow.split("\\sP\\s").length > 1) {
                            String[] parts = theRow.split("\\sP\\s");
                            if (parts[1].split("\\s").length < 5) continue;
                            representativeTrades.setAssetName(parts[0]);
                            representativeTrades.setTransactionType("Purchase");
                            representativeTrades.setTransactionDate(parts[1].split("\\s")[0]);
                            representativeTrades.setFilingDate(parts[1].split("\\s")[1]);
                            representativeTrades.setValueRange(parts[1].split("\\s")[2] + " - " + parts[1].split("\\s")[4]);
                        } else if (theRow.split("\\sS\\s").length > 1) {
                            String[] parts = theRow.split("\\sS\\s");
                            representativeTrades.setAssetName(parts[0]);
                            if (parts[1].split("\\s").length < 5) continue;
                            representativeTrades.setTransactionType("Sales");
                            representativeTrades.setTransactionDate(parts[1].split("\\s")[0]);
                            representativeTrades.setFilingDate(parts[1].split("\\s")[1]);
                            representativeTrades.setValueRange(parts[1].split("\\s")[2] + " - " + parts[1].split("\\s")[4]);
                        } else {
                            System.out.println("Line not resolved" + " " + theRow);
                            System.out.println(pdfUrl);
                        }
                        representativeTradesList.add(representativeTrades);
                        System.out.println(representativeTrades.getFillingId());
                        System.out.println(representativeTrades.getStatus());
                        System.out.println(representativeTrades.getAssetName());
                        System.out.println(representativeTrades.getDescription());
                        System.out.println(representativeTrades.getTransactionType());
                        System.out.println(representativeTrades.getFilingDate());
                        System.out.println(representativeTrades.getSourceUrl());
                        System.out.println(representativeTrades.getGains());
                    }
                    System.out.println("--------");
                }
            }
        }
        saveTheList(representativeTradesList);
    }

    private static void saveTheList(List<RepresentativeTrades> representativeTradesList) {
        //TODO Call the method to save the data to DB.
    }

    private static boolean isNumber(String value) {
        if (value == null)
            return false;
        try {
            Long.valueOf(value);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }


}



