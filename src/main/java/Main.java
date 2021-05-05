import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import services.common.CssDataNullException;
import services.sbb.Finland;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

public class Main {


    public static void main(String[] args) throws Exception {
        Finland finland=new Finland();
        finland.crawl();

        }
    }





