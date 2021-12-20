//package com.x2iq.sds.bots.european.services;
//
//import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.DomElement;
//import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
//import com.x2iq.sds.bots.european.model.SDSData;
//import com.x2iq.sds.bots.european.repo.SdsRepo;
//import com.x2iq.sds.bots.european.services.interfaces.SdsService;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.stream.IntStream;
//
//import static com.x2iq.sds.utils.ParamUtils.getDateInYourFormat;
//
//@Service
//public class DEService2Web implements SdsService, EuWebService {
//
//  private static String START_DATE;
//  private static String END_DATE ;
//  private final int countryId = 276;
//  private final SdsRepo sdsRepo;
//  private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//  @Autowired
//  public DEService2Web(SdsRepo sdsRepo) {
//    this.sdsRepo = sdsRepo;
//  }
//
//
//
//  private static String  getCalender(int year) {
//    Calendar calendar = Calendar.getInstance();
//    calendar.set(year,00,01);
//    return new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());
//  }
//  private static String  getCalenderEnd(int year) {
//    Calendar calendar = Calendar.getInstance();
//    calendar.set(year,01,01);
//    return new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());
//  }
//
//
//  @Override
//  @Scheduled(fixedDelay = 999999999999999999L)
//  public boolean start() {
//    int year=2021;
//    START_DATE=getCalender(year);
//    END_DATE=getCalenderEnd(year);
//    init();
//
//
//    return false;
//  }
//
//
//
//  private void init() {
//    logger.info("Schedule of DEWeb starts");
//    try (WebClient webClient = new WebClient()) {
//      webClient.getOptions().setCssEnabled(false);
//      webClient.getOptions().setJavaScriptEnabled(true);
//      webClient.getOptions().setThrowExceptionOnScriptError(false);
//      webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//      DefaultCredentialsProvider creds = new DefaultCredentialsProvider();
//      creds.addCredentials("waqar.younas7564.gmail.com", "iqrjxq");
//      webClient.setCredentialsProvider(creds);
//
//      openTheMainPage(webClient);
//    } catch (IOException e) {
//      logger.error(e.getMessage());
//    }
//    logger.error("Schedule of DEWeb ends");
//  }
//
//  private void openTheMainPage(WebClient webClient) throws IOException {
//    String url = "https://www.bundesanzeiger.de/pub/en/start";
//    HtmlPage htmlPage = webClient.getPage(url);
//    HtmlAnchor htmlAnchor = htmlPage.getAnchors()
//        .stream()
//        .filter(
//            a -> a.asText().equals("Extended search")
//        ).findFirst().orElse(null);
//
//    if (htmlAnchor == null) {
//      logger.info("Attention : Anchor not found");
//      return;
//    }
//    htmlPage = htmlAnchor.click();
//
//    DomElement element = htmlPage.getTabbableElements().stream()
//        .filter(a -> a.getAttribute("value").equals("72"))
//        .findFirst().orElse(null);
//    if (element == null)
//      return;
//    htmlPage = element.click();
//    htmlPage = htmlPage.getElementByName("panelCategories:groupCategories:apply-button").click();
//    htmlPage.getElementByName("start_date").setAttribute("value", START_DATE);
//    htmlPage.getElementByName("end_date").setAttribute("value", END_DATE);
//    htmlPage = htmlPage.getElementsByName("search-button").get(0).click();
//    htmlAnchor = htmlPage.getAnchors()
//        .stream()
//        .filter(
//            a -> a.getAttribute("href").contains("sort~date~desc")
//        ).findFirst().orElse(null);
//
//    if (htmlAnchor != null) {
//      htmlPage = htmlAnchor.click();
//    }
//    while (htmlPage != null) {
//      htmlPage.getAnchors().forEach(a -> {
//        if (a.asText().trim().equals("Mitteilung von Netto-Leerverkaufspositionen"))
//          performClickOnAnchor(a);
//      });
//      HtmlAnchor nextAnchor = htmlPage.getAnchors()
//          .stream()
//          .filter(
//              a -> a.getAttribute("title").trim().equals("To next page")
//          ).findFirst()
//          .orElse(null);
//      if (nextAnchor == null) {
//        logger.info("Attention: Next page Anchor not found");
//        return;
//      }
//      if (nextAnchor.getAttribute("href") == null) {
//        logger.info("End of DE Web Bot for SDS");
//        return;
//      }
//      htmlPage = nextAnchor.click();
//    }
//  }
//
//  private void performClickOnAnchor(HtmlAnchor htmlAnchor) {
//    HtmlPage htmlPage;
//    try {
//      htmlPage = htmlAnchor.click();
//    } catch (IOException e) {
//      return;
//    }
//    Document document = Jsoup.parse(htmlPage.asXml());
//    Element element = document.getElementById("begin_pub");
//    if(element==null)return;
//    consumeTheElement(element, document, htmlAnchor.getHrefAttribute());
//  }
//
//  private void consumeTheElement(Element element, Document document, String htmlAnchor) {
//    String pDate = getDateInYourFormat(document.getElementsByClass("date").text(), "MM/dd/yyyy", "yyyy-MM-dd");
//    String resultText = document.getElementsByClass("result_container").first().text();
//    if (resultText.toLowerCase().contains("Berichtigt am".toLowerCase()) || resultText.toLowerCase().contains("Amended on".toLowerCase())) {
//      logger.info("transaction ignored, you got it " + document.getElementsByClass("result_container").first().text());
//      return;
//    }
//    Elements rows = element.getElementsByTag("tr");
//    if (rows.size() < 2) {
//      logger.error("Unparseable text" + rows.text());
//      return;
//    }
//    Element row = element.getElementsByTag("tr").get(1);
//
//    String manager = row.getElementsByTag("h3").first().text().trim();
//
//    String issuer = row.getElementsByTag("b").first().text().trim();
//
//    String isin = row.getElementsByTag("b").get(1).text().replace("ISIN: ", "").trim();
//
//    String fetchedDate = row.getElementsByTag("b").get(2).text();
//    String date = getDateInYourFormat(fetchedDate, "dd.MM.yyyy", "yyyy-MM-dd");
//
//    if (date == null) {
//      logger.error("date not parsed" + fetchedDate);
//      return;
//    }
//
//    if (row.getElementsByTag("b").size() < 3) {
//      logger.error("Not compiled" + element.toString());
//      return;
//    }
//    String position = "0.0";
//    if (row.getElementsByTag("b").size() > 3) {
//      position = row.getElementsByTag("b").get(3).text();
//    }
//
//    BigDecimal positionInBD = new BigDecimal("0.0");
//    try {
//      positionInBD = new BigDecimal(position.replace(',', '.').replace("%", "").trim());
//    } catch (NumberFormatException ignore) {
//      logger.error("Number not parsed " + position);
//    }
//    BigDecimal finalPositionInBD = positionInBD;
//    SDSData sdsData = new SDSData() {{
//      setIssuerName(issuer);
//      setPositionHolder(manager);
//      setIsinCode(isin);
//      setPositionDate(date);
//      setNetShortPosition(finalPositionInBD);
//      setCountryCode("DE");
//      setCountryId(countryId);
//      setStatus("active");
//      setSourceURL(htmlAnchor);
//      setPublicationDate(pDate);
//      setTruePublicationDate(pDate);
//    }};
//    logger.info(sdsData.toString());
//    sdsRepo.inserMappedTable(sdsData);
//    sleepForLessThenSecond();
//  }
//
//  private void sleepForLessThenSecond() {
//    try {
//      Thread.sleep((int) (Math.random() * 1000));
//    } catch (InterruptedException ignore) {
//    }
//  }
//
//}
