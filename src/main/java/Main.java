import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.http.HttpHost;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.collections.Lists;
import utils.ParameterUtils;

class Member implements Comparator<Member> {

   String name;
  Member(String nm){
  this.name=nm;
  }


  public Member() {
  }

  @Override
  public int compare(Member o1, Member o2) {
    return o1.name.compareTo(o2.name);
  }
}

public class Main {


//  private static String crawl() {
//    String res = null;
//    ArrayList<String> userAgents = new ArrayList<>();
//    Random r = new Random();
//    userAgents.add(
//        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1.1 Safari/605.1.15");
//    userAgents.add("Windows NT 10.0; Win64; x64; rv:77.0) Gecko/20100101 Firefox/77.0");
//    userAgents.add(
//        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
//    String userAgent = userAgents.get(r.nextInt(userAgents.size()));
//    HttpHost proxy = new HttpHost("gate2.proxyfuel.com", 2000);
//    try {
//      String host = Executor.newInstance()
//          .auth(proxy, "waqar.younas7564.gmail.com", "iqrjxq")
//          .execute(Request.Get("http://api.ipify.org").viaProxy(proxy))
//          .returnContent().asString();
//    } catch (IOException e) {
//      System.out.println(e.getMessage());
//    }
//    Request request = Request.Get(
//        "https://www.nseindia.com/api/corporates-daily-buyback?index=equities&from_date=22-01-2022&to_date=22-04-2022")
//        .addHeader("cache-control", "max-age=0")
//        .addHeader("sec-ch-ua-mobile", "?0")
//        .addHeader("upgrade-insecure-requests", "1").
//            addHeader("accept",
//                "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
//        .
//            addHeader("upgrade-insecure-requests", "1").
//            addHeader("sec-fetch-site", "none").
//            addHeader("sec-fetch-mode", "navigate").
//            addHeader("sec-fetch-user", "?1").
//            addHeader("accept-language", "en-GB,en-US;q=0.9,en;q=0.8").userAgent(userAgent).
//            addHeader("Referer",
//                "https://www.nseindia.com/companies-listing/corporate-filings-daily-buy-back").
//            addHeader("Cookie",
//                "AKA_A2=A; nsit=KI846EME3yqF8_LOjLr71_ak; nseappid=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhcGkubnNlIiwiYXVkIjoiYXBpLm5zZSIsImlhdCI6MTY1MDYwMTg5NCwiZXhwIjoxNjUwNjA1NDk0fQ.BQNFhrmu-RAq8t--M2c8rBS9votmgN3mZTtRWLVZvYU; ak_bmsc=DB34C3406400F99A03096719B80A4881~000000000000000000000000000000~YAAQpmbRF9s/4D+AAQAAb0SKTw94H3RU/TDDo7faSnOI8iKvzO4el4cbdSlIDfVaaM1TyeqRP6Qe3JLQ4TjZY+zg1ZErV6WibUHW4q3bUpg5C6frTODmuBxRXVB19uruL1ZDWz7NfONKE+sTYVA+ZEHsRPsH9n1uHU2IflQHcYyHb7+a/7mIaFiRivOvSOKCFcnrFLmVRxtSeJWtwqfTQw2TV6hQ+lxBQWAwaKzkyG9IBUwBscEXjnjmB0hoW+VBEYsz30LTvZWjUcMZmcB73fpSf9Pvo3n5nJR5XyZfWWGTBRZxYt0dZJk06Qaqxg3ux+JNaMNIZFiz5qDQRRDhpC6KZbS78xqixize4MaqkkCATaE91OKSGs+V4msvR2ZYsk8ETqP2Msozr75KtE/lgTOZgmCwedOYq/d704tuLsTi4vmwsrflKuOOfYqf5Z1D9uOSFb5Nh1tTQ60qMmkpvbHVbrKrIapUi2IquYXqIWEY9Q4YctKFKBN6iIw=; bm_sv=BBF60ED9F72750E1C2759FC545244CF4~gIkGfpdQwRmeHJC766dk8R4qmPoIArJQUghQMfQzfVUnDfwRX2B/pFXiW39cWOa5Ada7bA3YN+JNkGeVWWgHPTsJ3vvN1CgK/hVqIo8U9SMDP0XQxeDlAAXScgOdleJdehbvV/hknRGzO+xibXdT1FBK3vqneUzLmzFdm2qkeXw=")
//        .
//            viaProxy(proxy);
//    try {
//      res = Executor.newInstance()
//          .auth(proxy, "waqar.younas7564.gmail.com", "iqrjxq")
//          .execute(request)
//          .returnContent().asString();
//    } catch (IOException e) {
//      System.out.println(e.getMessage());
//    }
//    return res;
//  }

  public static void main(String[] args) throws Exception {

//    Collections.sort();
    Member member=new Member("waqar");
    Member member2=new Member("bilal");
    List<Member> bilal = Lists.newArrayList(member, member2);
//    Collections.sort(bilal);
    Iterator<Member> iterator = bilal.iterator();
    while (iterator.hasNext()){
      System.out.println(iterator.next().name);
    }

//    Thread thread = new Thread(new Thd1());
//    thread.run();
//    System.out.println("End");
//    Thread.sleep(6000);

//    ArrayList<Runnable> thds = new ArrayList<>();
//    thds.add(new Thd4());
//    thds.add(new Thd3());
//    thds.add(new Thd2());
//    thds.add(new Thd1());
//    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
//    scheduledExecutorService.scheduleWithFixedDelay(() -> {
//      thds.forEach(Runnable::run);
//    }, 15, 10, SECONDS);

//    Runtime.getRuntime().availableProcessors();
//    System.out.println(Thread.currentThread().getName());
//
//    ExecutorService executorService = Executors.newFixedThreadPool(4);
//executorService.submit(()->{
//  thds.forEach(thd->{
//    thd.run();
//  });
//});
//executorService.shutdown();
//    thds.forEach(thd->{
//      new Thread(thd).start();
//    });
  }

// int[] arr={1,2,3,4};
// int b=13;
//    System.out.println("before " + Arrays.toString(arr));
// arrFn(arr);
//    System.out.println("After " + Arrays.toString(arr));

//    Singelton s1=Singelton.getInstance();
//
//    while (){
//      s1
//    }
//    Singelton s2=Singelton.getInstance();

//    s1.setName("waqar");
//    s1.setAge(31);
//    System.out.println("s1--> " +s1.getName()+s1.getAge());
//
////    s1.setName("Bilal");
////    s1.setAge(25);
//    System.out.println("S2---> "+s1.getName()+s1.getAge());
//    System.out.println("s1--> " +s1.getName()+s1.getAge());

//    System.out.println(getFormattedDate("2015-07-20T22:22:56Z",2));
//    try (final WebClient webClient = new WebClient()) {
//      System.setProperty("jsse.enableSNIExtension", "false");
//      webClient.getOptions().setUseInsecureSSL(true);
//      webClient.getOptions().setPrintContentOnFailingStatusCode(false);
//      webClient.getOptions().setCssEnabled(false);
//      webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//      webClient.getOptions().setThrowExceptionOnScriptError(false);
//      HtmlPage page =webClient.getPage("https://mops.twse.com.tw/mops/web/ajax_t35sc09");
//      String contentAsString = page.getWebResponse().getContentAsString();
//      System.out.println(contentAsString);
//
//    }

//
//    Request request = Request.Get("https://mops.twse.com.tw/mops/web/ajax_t35sc09");
//    HttpResponse httpResponse = request.execute().returnResponse();
//    System.out.println(httpResponse.getStatusLine());
//    request.setHeader("Connection","keep-alive");
//    request.setHeader("Sec-Ch-Ua","^^");
////    HttpResponse httpResponse = request.execute().returnResponse();
//    System.out.println(httpResponse.getStatusLine());
//    if(httpResponse.getEntity()!=null)
//
//    {
//      String html = EntityUtils.toString(httpResponse.getEntity());
//      System.out.println(html);
//    }

//    String url = "https://api.news.eu.nasdaq.com/news/query.action?displayLanguage=en&timeZone=Europe/Tallinn&dateMask=yyyy-MM-dd+HH:mm:ss+Z&limit=26&countResults=false&start=0&freeText=purchase+of+own+shares&market=NASDAQ+OMX,+Tallinn&market=NASDAQ+OMX,+Riga&market=NASDAQ+OMX,+Vilnius&_queryId=exchanges&callback=jQuery224017731228046817837_1641198440364&_=1641198440370";
//    Document document = Jsoup.connect(url).ignoreContentType(true).get();
//    String body = document.body().toString();
//    String data = body.replace("<body>", "").replace("</body>", "")
//        .replace(" jQuery224017731228046817837_1641198440364(", "").replace(")", "");
//
//    JSONObject jsonResponse = new JSONObject(data);
//    JSONObject results = jsonResponse.getJSONObject("results");
//    JSONArray item = results.getJSONArray("item");
//    for (int i = 0; i < item.length(); i++) {
//      String annsHeading = "";
//      String companyName = "";
//      String link = "";
//      String releaseDate = "";
//
//      annsHeading = item.getJSONObject(i).getString("headline");
//      companyName = item.getJSONObject(i).getString("company");
//      link = item.getJSONObject(i).getString("messageUrl");
//      releaseDate = item.getJSONObject(i).getString("releaseTime");
//      System.out.println(annsHeading);
//      System.out.println(companyName);
//      System.out.println(link);
//      System.out.println(releaseDate);
//      System.out.println("*********************");
//
//    }
//
////    try {
////      Pattern p = Pattern.compile("\\s\\d+\\s");
////      String headLine = "(Historisk) Point72 Asset Management, L.P. has reduced his short position to 242000 shares issued by FLSmidth & Co. A/S";
////      Matcher m = p.matcher(headLine);
////      if (headLine.contains("his short position to")) {
////        if (m.find()) {
////          int shares = Integer.parseInt(m.group(0).trim());
////          System.out.println(shares);
////        }
////      }
////
////    } catch (NumberFormatException | IndexOutOfBoundsException ignore) {
////      return;
////    }
//
////    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
////    Date startDate = formatter.parse("2010-12-20");
////    Date endDate = formatter.parse("2010-12-26");
////
////    Calendar start = Calendar.getInstance();
////    start.setTime(startDate);
////    Calendar end = Calendar.getInstance();
////    end.setTime(endDate);
////
////    for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
////Date simpleDateFormat=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(date.toString());
////      String dateInYourFormat = ParameterUtils
////          .getDateInYourFormat(simpleDateFormat.toString(), "EEE MMM dd HH:mm:ss zzz yyyy",
////              "yyyy-MM-dd");
////      System.out.println(dateInYourFormat);
////    }
//    // Do your job here with `date`.
//
////          String companyName = row.select("td.oddnew-M kjName ").text();
////            String link = row.getElementsByTag("a").attr("href");
////           String headLine = row.text();
////            System.out.println(headLine);
////            String dateTime = row.select("td.oddnew-L kjTime").first().text();
//    // dateTime = dateTime.replace("-", "");
//
////
////    String srcUrl = "https://ft-api.prod.oam.finanstilsynet.dk/external/v0.1/trigger/dfsa-search-announcement";
////    final String USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Mobile Safari/537.36";
////    String refrerer = "https://oam.finanstilsynet.dk/";
////    for (int skip = 0; skip <= 100; skip += 25) {
////
////      String jsonBody = "{\"SortField\":\"RegistrationDate\",\"Ascending\":false,\"Skip\":" + skip
////          + ",\"Take\":25,\"Status\":[\"Published\"],\"InformationTypes\":[\"DFSAInformationType/0528038b-b549-4636-8eb1-adee0126e728\"],\"IncludeHistoric\":true}";
////
////      Connection.Response response = Jsoup.connect(srcUrl)
////          .userAgent(USER_AGENT)
////          .header("Content-Type", "application/json")
////          .followRedirects(true)
////          .ignoreHttpErrors(true)
////          .ignoreContentType(true)
////          .header("Accept", "application/json")
////          .header("Accept-Language", "en-US,en;q=0.9")
////          .header("User-Agent", USER_AGENT)
////          .header("referer", refrerer)
////          .requestBody(jsonBody)
////          .method(Connection.Method.POST)
////          .execute();
////      JSONObject data = new JSONObject(response.body());
////      JSONArray jsonArray = data.getJSONArray("data");
////      for (int i = 0; i < jsonArray.length(); i++) {
////        String annId = jsonArray.getJSONObject(i).getString("Id");
////        getAnnData(annId);
////
////      }
////      System.out.println("----------------------------------------------");
////    }
////
////  }
////
////
////  private static void getAnnData(String id) throws IOException {
////    String url = "https://ft-api.prod.oam.finanstilsynet.dk/api/v0.1/widget/f02103d6-84cb-47e6-9d1e-ad990033c55f/data";
////    final String USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Mobile Safari/537.36";
////    String refrerer = "https://oam.finanstilsynet.dk/";
////    String jsonBody =
////        "{\"Pagination\":{\"PageIndex\":1,\"PageSize\":10},\"queryParameters\":{\"Id\":" + "\"" + id
////            + "\""
////            + "},\"container\":{\"ContainerId\":\"38a12880-ef76-4eda-a86c-ad99003556ea\",\"ContainerType\":\"WidgetContainer\"},\"sortedFields\":[]}";
////
////    Connection.Response response = Jsoup.connect(url)
////        .header("accept", "*/*")
////        .header("accept-language", "en-GB,en-US;q=0.9,en;q=0.8")
////        .header("authorization",
////            "CfDJ8CQYRJTrwkZGslJ84u0JqX1IlrhlFmyWSdQoBIHjsBnc6IgccbowLj626U3zxg-rSZfLVmVPYW2hcjhFzviSt5iKr7VHZC43nURIeX91vyAhhq3_WjrE1OfYHcZJKcw4tC5i9sJlau4nciqIwtGUfQW1OlQItzEIax-i5dvPCeIYPEuwNUm3EBB1Fbo2FaAGPGyMbm8DIdd-ZsbRp-f0bDaukM_KlnBIW-QXxzm9Hr-7wTtCpVlpvwaKWEJ7m26px1z2AGfIJt6PtqKQ9DcnHEuktEEcAmp6n3t1I-aEM6WhFTA3bU5gtJRbjStzpLPBeg")
////        .header("content-type", "application/json;charset=UTF-8")
////        .header("sec-ch-ua",
////            "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"96\", \"Google Chrome\";v=\"96\"")
////        .header("sec-ch-ua",
////            "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"96\", \"Google Chrome\";v=\"96\"")
////        .header("sec-fetch-dest", "empty")
////        .header("sec-fetch-mode", "cors")
////        .header("sec-ch-ua-platform", "\"Windows\"")
////        .header("sec-fetch-site", "same-site")
////        .header("sec-ch-ua-mobile", "?0")
////        .header("workersiteid", "cc2aa132-ec77-4a8c-95af-abb101459cbc")
////        .header("referer", refrerer)
////        .header("Referrer-Policy", "strict-origin-when-cross-origin")
////        .followRedirects(true)
////        .ignoreHttpErrors(true)
////        .ignoreContentType(true)
////        .requestBody(jsonBody)
////        .method(Connection.Method.POST)
////        .execute();
////
////    JSONObject data = new JSONObject(response.body());
////    JSONObject jsonObj = data.getJSONArray("data").getJSONObject(0);
////    String isinCode = jsonObj.getString("ISINCode");
////    String positionHolder = jsonObj.getString("AnnouncedCompanyName");
////    String issuerName = jsonObj.getString("IssuerName");
////    double netShortPosition = jsonObj.getDouble("ShortsellingPercentageShareCapital");
////    String truePublicationDate = jsonObj.getString("PublishedDate");
////    String netShortPositionDate = jsonObj.getString("ShortsellingPositionDate");
////    String isHistoric = String.valueOf(jsonObj.get("IsHistoric"));
////    String headLine = (jsonObj.getString("Headline"));
////    Pattern p = Pattern.compile("[1-9]\\d*");
////    Matcher m = p.matcher(headLine);
////    if(headLine.contains("his short position to")){
////    if(m.find()) {
////      System.out.println(m.group());
////    }
////    }
////
//////    try {
//////      System.out.println(
//////          isinCode + " " + positionHolder + " " + issuerName + " " + netShortPosition + " "
//////              + getFormattedDate(truePublicationDate) + "  " + getFormattedDate(
//////              netShortPositionDate) + "::::::::" + isHistoric);
//////    } catch (ParseException e) {
//////      e.printStackTrace();
//////    }
////
////    System.out.println("******************************");

}

//  public static String getFormattedDate(String date) throws ParseException {
//    Date simpleDateFormat = null;
//    Calendar calendar = Calendar.getInstance();
//    String result = null;
//    try {
//      simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'").parse(date);
//      calendar.setTime(simpleDateFormat);
//      calendar.add(Calendar.HOUR_OF_DAY, 1);
//      simpleDateFormat = calendar.getTime();
//      result =  ParameterUtils
//          .getDateInYourFormat(simpleDateFormat.toString(), "EEE MMM dd HH:mm:ss zzz yyyy", "yyyy-MM-dd");
//
//    } catch (ParseException e) {
//      simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS.SSS'Z'").parse(date);
//      calendar.setTime(simpleDateFormat);
//      calendar.add(Calendar.HOUR, 1);
//      simpleDateFormat = calendar.getTime();
//      result =  ParameterUtils
//          .getDateInYourFormat(simpleDateFormat.toString(), "EEE MMM dd HH:mm:ss zzz yyyy", "yyyy-MM-dd");
//    }
//
//    return result;
//  }
//
//  public static String getRowCreationDate(String date) throws ParseException {
//    Date simpleDateFormat = null;
//    Calendar calendar = Calendar.getInstance();
//    String result = null;
//    try {
//      simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'").parse(date);
//      calendar.setTime(simpleDateFormat);
//      calendar.add(Calendar.HOUR_OF_DAY, 1);
//      simpleDateFormat = calendar.getTime();
//      result =  ParameterUtils
//          .getDateInYourFormat(simpleDateFormat.toString(), "EEE MMM dd HH:mm:ss zzz yyyy", "yyyy-MM-dd HH:mm:ss");
//
//    } catch (ParseException e) {
//      simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS.SSS'Z'").parse(date);
//      calendar.setTime(simpleDateFormat);
//      calendar.add(Calendar.HOUR, 1);
//      simpleDateFormat = calendar.getTime();
//      result =  ParameterUtils
//          .getDateInYourFormat(simpleDateFormat.toString(), "EEE MMM dd HH:mm:ss zzz yyyy", "yyyy-MM-dd HH:mm:ss");
//    }
//
//    return result;
//  }


