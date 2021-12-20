//package services.SDS;
//
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class PtService {
//    private static List<String> urls;
//
//    public static void scrape() {
//        String url = "https://web3.cmvm.pt/english/sdi/emitentes/shortselling/index.cfm";
//        processHomePage(crawl(url));
//    }
//
//    public static String crawl(String url) {
//        Connection.Response response = null;
//        try {
//            response = Jsoup.connect(url).method(Connection.Method.GET).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return response.body();
//    }
//
//    public static void processHomePage(String response) {
//        Document document = Jsoup.parse(response);
//        getAllUrls(document);
//        urls.forEach(src -> {
//            processIssuerPage(crawl(src));
//        });
//
//    }
//
//    public static void getAllUrls(Document document) {
//        urls = new ArrayList<>();
//        Elements elements = document.getElementsByClass("bloco10 first");
//        Elements linkElements = elements.get(0).getElementsByTag("ul").get(0).getElementsByTag("li");
//        String urlPrefix = "https://web3.cmvm.pt/english/sdi/emitentes/shortselling/";
//
//        for (Element element : linkElements) {
//            String href = element.getElementsByTag("a").attr("href");
//            href = urlPrefix + href;
//            urls.add(href);
//        }
//    }
//
//    public static void processIssuerPage(String response) {
//        Document document = Jsoup.parse(response);
//        Elements table = document.select("#tableContentGestaoActivos>tbody>tr:last-child");
//        if (table.size() == 0) return;
//        boolean tableIsNotEmpty = !table.text().isEmpty();
//        if (tableIsNotEmpty) {
//            HashMap<String, String> data = new HashMap<>();
//            String issuerName = document.select("#divsContainer > div > section > p:nth-child(2)").text();
//            String isin = document.select("#divsContainer > div > section > p:nth-child(3)").text().replace("ISIN:", "").trim();
//            data.put(issuerName, isin);
//            getIssuerPageTableSpData(table, issuerName, isin);
//        }
//        getIssuerPageLinks(document);
//    }
//
//    public static void getIssuerPageTableSpData(Elements table, String issuer, String isin) {
//        Elements data = table.select("td");
//        String positionHolder = data.get(0).text().trim();
//        BigDecimal netShortPosition = new BigDecimal(data.get(1).text().trim());
//        String publicationDate = data.get(2).text().trim();
//        String positionDate = data.get(3).text().trim();
//
//        positionDate = ParamUtils.getDateInYourFormat(positionDate, "dd/MM/yyyy", "yyyy-MM-dd");
//        publicationDate = ParameterUtils.getDateInYourFormat(publicationDate, "dd/MM/yyyy", "yyyy-MM-dd");
//
//        SDSData sdsData = new SDSData();
//        sdsData.setPositionHolder(positionHolder);
//        sdsData.setIssuerName(issuerName);
//        sdsData.setPositionDate(positionDate);
//        sdsData.setPublicationDate(publicationDate);
//        sdsData.setNetShortPosition(netShortPosition);
//        sdsData.setCountryCode("PT");
//        sdsData.setIsinCode(isin);
//        sdsData.setCountryId(countryId);
//        sdsData.setSourceURL(theUrl);
//        sdsData.setHistoric(false);
//        sdsData.setStatus("active");
//
//        sdsRepo.inserMappedTable(sdsData);
//
//    }
//
//    public static void getIssuerPageLinks(Document document) {
//        ArrayList<String> urls = new ArrayList<>();
//        String prefix = "https://web3.cmvm.pt/english/sdi/emitentes/shortselling/";
//        Elements links = document.select("section.WTabela>ul>li>a");
//        for (Element link : links) {
//            String href = link.attr("href");
//            if (href.contains("hist_posicoes_agregadas")) continue;
//            urls.add(prefix+href);
//        }
//        urls.forEach(src -> crawlIssuerPageLinks(crawl(src)));
//    }
//
//    public static void crawlIssuerPageLinks(String response) {
//        Document document = Jsoup.parse(response);
//        String issuerName = document.select("#divsContainer > div > section > p:nth-child(2)").text();
//        String isin = document.select("#divsContainer > div > section > p:nth-child(3)").text().replace("ISIN:", "").trim();
//        Elements data = document.select("#tableContentGestaoActivos>tbody>tr");
//        data.stream().skip(2).forEach(li->{
//            Elements td = li.select("td");
//            String positionHolder = td.get(0).text().trim();
//            BigDecimal netShortPosition = new BigDecimal(td.get(1).text().trim());
//            String publicationDate = td.get(2).text().trim();
//            String positionDate = td.get(3).text().trim();
//
//            positionDate = ParamUtils.getDateInYourFormat(positionDate, "dd/MM/yyyy", "yyyy-MM-dd");
//            publicationDate = ParameterUtils.getDateInYourFormat(publicationDate, "dd/MM/yyyy", "yyyy-MM-dd");
//
//            SDSData sdsDataHistoric = new SDSData();
//            sdsDataHistoric.setPositionHolder(positionHolder);
//            sdsDataHistoric.setIsinCode(isin);
//            sdsDataHistoric.setIssuerName(issuerName);
//            sdsDataHistoric.setPositionDate(positionDate);
//            sdsDataHistoric.setPublicationDate(publicationDate);
//            sdsDataHistoric.setNetShortPosition(netShortPosition);
//            sdsDataHistoric.setCountryId(countryId);
//            sdsDataHistoric.setCountryCode("PT");
//            sdsDataHistoric.setSourceURL(secondInternalUrl);
//            sdsDataHistoric.setHistoric(true);
//            sdsDataHistoric.setStatus("historic");
//            sdsRepo.inserMappedTable(sdsDataHistoric);
//
//        });
//
//
//    }
//
//    public static void getShortPositions() {
//    }
//
//
//}
