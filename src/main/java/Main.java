
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;


public class Main {

  public static void main(String[] args) throws Exception {

    String srcUrl = "https://ft-api.prod.oam.finanstilsynet.dk/external/v0.1/trigger/dfsa-search-announcement";
    final String USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Mobile Safari/537.36";
    String refrerer = "https://oam.finanstilsynet.dk/";
    for (int skip = 0; skip <= 100; skip += 25) {

      String jsonBody = "{\"SortField\":\"RegistrationDate\",\"Ascending\":false,\"Skip\":" + skip
          + ",\"Take\":25,\"Status\":[\"Published\"],\"InformationTypes\":[\"DFSAInformationType/0528038b-b549-4636-8eb1-adee0126e728\"],\"IncludeHistoric\":true}";

      Connection.Response response = Jsoup.connect(srcUrl)
          .userAgent(USER_AGENT)
          .header("Content-Type", "application/json")
          .followRedirects(true)
          .ignoreHttpErrors(true)
          .ignoreContentType(true)
          .header("Accept", "application/json")
          .header("Accept-Language", "en-US,en;q=0.9")
          .header("User-Agent", USER_AGENT)
          .header("referer", refrerer)
          .requestBody(jsonBody)
          .method(Connection.Method.POST)
          .execute();
      JSONObject data = new JSONObject(response.body());
      JSONArray jsonArray = data.getJSONArray("data");
      for (int i = 0; i < jsonArray.length(); i++) {
        String annId = jsonArray.getJSONObject(i).getString("Id");
        getAnnData(annId);

      }
      System.out.println("----------------------------------------------");
    }

  }


  private static void getAnnData(String id) throws IOException {
    String url = "https://ft-api.prod.oam.finanstilsynet.dk/api/v0.1/widget/f02103d6-84cb-47e6-9d1e-ad990033c55f/data";
    final String USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Mobile Safari/537.36";
    String refrerer = "https://oam.finanstilsynet.dk/";
    String jsonBody =
        "{\"Pagination\":{\"PageIndex\":1,\"PageSize\":10},\"queryParameters\":{\"Id\":" + "\"" + id
            + "\""
            + "},\"container\":{\"ContainerId\":\"38a12880-ef76-4eda-a86c-ad99003556ea\",\"ContainerType\":\"WidgetContainer\"},\"sortedFields\":[]}";

    Connection.Response response = Jsoup.connect(url)
        .header("accept", "*/*")
        .header("accept-language", "en-GB,en-US;q=0.9,en;q=0.8")
        .header("authorization",
            "CfDJ8CQYRJTrwkZGslJ84u0JqX1IlrhlFmyWSdQoBIHjsBnc6IgccbowLj626U3zxg-rSZfLVmVPYW2hcjhFzviSt5iKr7VHZC43nURIeX91vyAhhq3_WjrE1OfYHcZJKcw4tC5i9sJlau4nciqIwtGUfQW1OlQItzEIax-i5dvPCeIYPEuwNUm3EBB1Fbo2FaAGPGyMbm8DIdd-ZsbRp-f0bDaukM_KlnBIW-QXxzm9Hr-7wTtCpVlpvwaKWEJ7m26px1z2AGfIJt6PtqKQ9DcnHEuktEEcAmp6n3t1I-aEM6WhFTA3bU5gtJRbjStzpLPBeg")
        .header("content-type", "application/json;charset=UTF-8")
        .header("sec-ch-ua",
            "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"96\", \"Google Chrome\";v=\"96\"")
        .header("sec-ch-ua",
            "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"96\", \"Google Chrome\";v=\"96\"")
        .header("sec-fetch-dest", "empty")
        .header("sec-fetch-mode", "cors")
        .header("sec-ch-ua-platform", "\"Windows\"")
        .header("sec-fetch-site", "same-site")
        .header("sec-ch-ua-mobile", "?0")
        .header("workersiteid", "cc2aa132-ec77-4a8c-95af-abb101459cbc")
        .header("referer", refrerer)
        .header("Referrer-Policy", "strict-origin-when-cross-origin")
        .followRedirects(true)
        .ignoreHttpErrors(true)
        .ignoreContentType(true)
        .requestBody(jsonBody)
        .method(Connection.Method.POST)
        .execute();

    JSONObject data = new JSONObject(response.body());
    JSONObject jsonObj = data.getJSONArray("data").getJSONObject(0);
    String isinCode = jsonObj.getString("ISINCode");
    String positionHolder = jsonObj.getString("AnnouncedCompanyName");
    String issuerName = jsonObj.getString("IssuerName");
    double netShortPosition = jsonObj.getDouble("ShortsellingPercentageShareCapital");
    String truePublicationDate = jsonObj.getString("PublishedDate");
    String netShortPositionDate = jsonObj.getString("ShortsellingPositionDate");
    try {
      System.out.println(
          isinCode + " " + positionHolder + " " + issuerName + " " + netShortPosition + " "
              + getFormattedDate(truePublicationDate) + "  " + getFormattedDate(
              netShortPositionDate));
    } catch (ParseException e) {
      e.printStackTrace();
    }

    System.out.println("******************************");


  }

  public static String getFormattedDate(String date) throws ParseException {
    Date simpleDateFormat = null;
    try {
      simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'").parse(date);

    } catch (ParseException e) {
      simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS.SSS'Z'").parse(date);
    }
    ZonedDateTime zdt = ZonedDateTime.ofInstant(simpleDateFormat.toInstant(), ZoneId
        .of("GMT+7"));

    LocalDate dateTime = zdt.toLocalDate();
    return dateTime.toString();
  }

}