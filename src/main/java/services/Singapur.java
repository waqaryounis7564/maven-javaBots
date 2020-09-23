package services;



import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.*;



public class Singapur {

    public static void scrape() throws IOException {
        String url = "https://eservices.mas.gov.sg/sprs/Telerik.Web.UI.WebResource.axd?type=rca&isc=true&guid=faf29a4a-5dee-4d20-a8a4-8bc0bb414461";
        Connection.Response resultImageResponse =  Jsoup.connect(url)
                .ignoreContentType(true).execute();

// output here
        FileOutputStream out = (new FileOutputStream(new File("img.jpg")));
        out.write(resultImageResponse.bodyAsBytes());  // resultImageResponse.body() is where the image's contents are.
        out.close();
    }
}
