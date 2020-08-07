import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Ret {

    public static void scrapeDAta() throws IOException {

        List<RepresentativeTrades> representativeTradesList = new ArrayList<>();
        try (final WebClient webClient = new WebClient()) {


            Page pdf = webClient.getPage("https://disclosures-clerk.house.gov//public_disc/ptr-pdfs/2020/20016402.pdf");
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
                    String owner;
                    String headerSplitter = "\\siD";
                    String theRow;
                    if (finalTradeLine.split(tradeSplitter[1]).length > 1) {
                        theRow = finalTradeLine.split(tradeSplitter[1])[1].split(headerSplitter)[0];
                        owner = "DC";

                    } else if (finalTradeLine.split(tradeSplitter[0]).length > 1) {
//                            desc = finalTradeLine.split(tradeSplitter[0])[0].split(headerSplitter)[0];
                        owner = "JT";
                        theRow = finalTradeLine.split(tradeSplitter[0])[1].split(headerSplitter)[0];

                    } else {
                        owner = "";
                        theRow = finalTradeLine.split(headerSplitter)[0];

                    }
                    RepresentativeTrades representativeTrades = new RepresentativeTrades();
                    if (isAmended) {
                        representativeTrades.setStatus("Amended");
                    } else representativeTrades.setStatus("New");

                    representativeTrades.setOwner(owner);

                    if (theRow.split("\\ss\\s").length > 1) {
                        String[] parts = theRow.split("\\ss\\s");
                        if (parts[1].split("\\s").length < 5) continue;

                        Date transaction = validateDate(parts[1].split("\\s")[0], "MM/dd/yyyy");
                        Date notification = validateDate(parts[1].split("\\s")[1], "MM/dd/yyyy");
                        representativeTrades.setAssetName(parts[0]);
                        representativeTrades.setTransactionType("Purchase");
                        representativeTrades.setTransactionDate(transaction);
                        representativeTrades.setNotificationDate(notification);
                        String tr = parts[1].split("\\s")[0];
                        String no = parts[1].split("\\s")[1];
                        String na = "waq";


                        representativeTrades.setValueRange(parts[1].split("\\s")[2] + " - " + parts[1].split("\\s")[4]);
                    } else if (theRow.split("\\ss\\s(partial)\\s").length > 1) {
                        String[] parts = theRow.split("\\ss\\s(partial)\\s");
                        if (parts[1].split("\\s").length < 5) continue;

                        Date transaction = validateDate(parts[1].split("\\s")[0], "MM/dd/yyyy");
                        Date notification = validateDate(parts[1].split("\\s")[1], "MM/dd/yyyy");
                        representativeTrades.setAssetName(parts[0]);
                        representativeTrades.setTransactionType("Purchase");
                        representativeTrades.setTransactionDate(transaction);
                        representativeTrades.setNotificationDate(notification);
                        String tr = parts[1].split("\\s")[0];
                        String no = parts[1].split("\\s")[1];
                        String na = "waq";
                        representativeTrades.setValueRange(parts[1].split("\\s")[2] + " - " + parts[1].split("\\s")[4]);
                    } else if (theRow.split("\\sE\\s").length > 1) {
                        String[] parts = theRow.split("\\sE\\s");
                        if (parts[1].split("\\s").length < 5) continue;
                        Date transaction = validateDate(parts[1].split("\\s")[0], "MM/dd/yyyy");
                        Date notification = validateDate(parts[1].split("\\s")[1], "MM/dd/yyyy");

                        representativeTrades.setAssetName(parts[0]);
                        representativeTrades.setTransactionType("Purchase");
                        representativeTrades.setTransactionDate(transaction);
                        representativeTrades.setNotificationDate(notification);

                        String tr = parts[1].split("\\s")[0];
                        String no = parts[1].split("\\s")[1];
                        String na = "waq";
                        representativeTrades.setValueRange(parts[1].split("\\s")[2] + " - " + parts[1].split("\\s")[4]);
                    } else if (theRow.split("\\sP\\s").length > 1) {
                        String[] parts = theRow.split("\\sP\\s");
                        if (parts[1].split("\\s").length < 5) continue;
                        Date transaction = validateDate(parts[1].split("\\s")[0], "MM/dd/yyyy");
                        Date notification = validateDate(parts[1].split("\\s")[1], "MM/dd/yyyy");
                        representativeTrades.setAssetName(parts[0]);
                        representativeTrades.setTransactionType("Purchase");
                        representativeTrades.setTransactionDate(transaction);
                        representativeTrades.setNotificationDate(notification);

                        String tr = parts[1].split("\\s")[0];
                        String no = parts[1].split("\\s")[1];
                        String na = "waq";
                        representativeTrades.setValueRange(parts[1].split("\\s")[2] + " - " + parts[1].split("\\s")[4]);
                    } else if (theRow.split("\\sS\\s").length > 1) {
                        String[] parts = theRow.split("\\sS\\s");
                        representativeTrades.setAssetName(parts[0]);
                        if (parts[1].split("\\s").length < 5) continue;
                        representativeTrades.setTransactionType("Sales");
                        String tr = parts[1].split("\\s")[0];
                        String no = parts[1].split("\\s")[1];
                        String na = "waq";
                        representativeTrades.setValueRange(parts[1].split("\\s")[2] + " - " + parts[1].split("\\s")[4]);
                    } else {
                        theRow = "Line not resolved" + " " + theRow;

                    }
                    String[] gainText;
                    if (theRow.startsWith("Line")) continue;
                    else if (theRow.contains("000")) {
                        gainText = theRow.split("000 ");
                    } else if (theRow.contains("$767.14")) {
                        gainText = theRow.split(".14");
                    } else if (theRow.contains("$770.65")) {
                        gainText = theRow.split(".65");
                    } else if (theRow.contains("$592.47")) {
                        gainText = theRow.split(".47");
                    } else if (theRow.contains("$348.94")) {
                        gainText = theRow.split(".94");
                    } else if (theRow.contains("$570.26")) {
                        gainText = theRow.split(".26");
                    } else if (theRow.contains("$721.28")) {
                        gainText = theRow.split(".28");
                    } else if (theRow.contains("$24.03")) {
                        gainText = theRow.split(".03");
                    } else if (theRow.contains("$456.80 ")) {
                        gainText = theRow.split(".80");
                    } else if (theRow.contains("$420.00 ")) {
                        gainText = theRow.split(".00");
                    } else if (theRow.contains("$423.87 ")) {
                        gainText = theRow.split(".87");
                    } else if (theRow.contains("$325.99 ")) {
                        gainText = theRow.split(".99");
                    } else {
                        gainText = theRow.split("001");
                    }
                    String gain = gainText[1].trim();
                    if (gain.equalsIgnoreCase("gfedcb")) {
                        representativeTrades.setGains("Yes");
                    } else representativeTrades.setGains("No");

                    representativeTradesList.add(representativeTrades);

                }
                System.out.println("----------------------------");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    private static Date validateDate(String date, String thisFormat) {
        String requiredFormat = "yyyy-MM-dd";
        if (date.contains(")")) {
            if (date.split("\\)")[1].trim().length() == thisFormat.length()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(thisFormat);
                    formatter.parse(date.split("\\)")[1].trim());
                    return new SimpleDateFormat(thisFormat).parse(date.split("\\)")[1].trim());
                } catch (ParseException ignore) {
                }
            }
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(thisFormat);
            formatter.parse(date.trim());
            return new SimpleDateFormat(thisFormat).parse(date.trim());
        } catch (ParseException ignore) {
            return null;
        }
    }
}



