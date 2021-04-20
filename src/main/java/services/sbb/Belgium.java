package services.sbb;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import services.common.CssDataNullException;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Belgium {
    public static void crawl() throws CssDataNullException {
        JSONArray jsonArray = new JSONArray(get());
        JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
        String data = jsonObject.get("data").toString();

        Elements tr = Jsoup.parse(data).select("tr");
        if (tr.isEmpty()) throw new CssDataNullException();
        for (Element element : tr) {
            if (element.child(2).text().equalsIgnoreCase("TITLE"))
                continue;
            String date = element.child(0).text().split(" CET")[0];
            String ticker = element.child(1).text();
            String headline = element.child(2).text();
            String headlineLink = element.child(2).getElementsByTag("a").attr("data-node-nid");
            String headlineUrl = "https://live.euronext.com/ajax/node/company-press-release/" + headlineLink;
            System.out.println(MessageFormat.format("{0},{1},{2},{3},{4}", date, ticker, headline, headlineLink, headlineUrl));
        }

    }
public static void backup() throws IOException {
        String url="https://live.euronext.com/node/202397";
    Document document = Jsoup.connect(url).get();
    System.out.println(document.body().select("#content > section > div.container"));
}
    private static String get() {
        int page = 3910;
        String obj = "view_name=company_press_releases_view&view_display_id=page_3&view_args=&view_path=%2Flistview%2Fcompany-press-releases&view_base_path=listview%2Fcompany-press-releases&view_dom_id=1cb7f301d8b3c2e35c83b9482379a169528626b0e9ff7ce9d184d17842b3669f&pager_element=0&field_company_pr_pub_datetime_start=1970-01-01+00%3A00%3A00&field_company_pr_pub_datetime_end=now&page=" + page + "&_drupal_ajax=1&ajax_page_state%5Btheme%5D=euronext_live&ajax_page_state%5Btheme_token%5D=&ajax_page_state%5Blibraries%5D=asset_injector%2Fcss%2Fam___hide__subscribe_regulated_news%2Casset_injector%2Fcss%2Fam__fixytd_fplextoslo_438%2Casset_injector%2Fcss%2Fam_cookie_compliance%2Casset_injector%2Fcss%2Fam_currency_size_in_pds%2Casset_injector%2Fcss%2Fam_fix_logos_height_on_instrument_pages%2Casset_injector%2Fcss%2Fam_oslo_financial_events_hide_second_see_all_button_fplextoslo_3%2Casset_injector%2Fcss%2Fcss_adjustements_to_be_reintegrated_in_the_theme_validated_%2Casset_injector%2Fcss%2Ffix_menu_on_ie%2Casset_injector%2Fcss%2Fin_page_quote_search%2Casset_injector%2Fcss%2Flimit_promo_004_height_except_on_home_page%2Casset_injector%2Fcss%2Fnot_logged_in_menu_nolink_items_colors%2Casset_injector%2Fcss%2Fsecondary_navigation_temporary_before_theming%2Casset_injector%2Fcss%2Ftemp_css_adjustements_to_be_reintegrated_in_the_theme%2Casset_injector%2Fcss%2Ftemporary_fix_disable_links_cor_reports_html%2Casset_injector%2Fcss%2Ftextual_paragraph%2Casset_injector%2Fcss%2Fview_filters%2Casset_injector%2Fcss%2Fweb_team_sc_intraday_off_canvas_download_feature_removal%2Casset_injector%2Fcss%2Fweb_team_sc_specific_product_directory_indices_no_currency%2Casset_injector%2Fcss%2Fwebteam_sc_temporay_removal_of_pop_out_at_instrument%2Casset_injector%2Fcss%2Fwebteam_specific_elio_fixed_header_body_min_height%2Casset_injector%2Fcss%2Fwebteam_specific_elio_underlying_accordion%2Casset_injector%2Fjs%2Fwebteam_specific_elio_underlying_accordion%2Cawl_company_press_releases%2Fawl-company-pr-off-canvas%2Cawl_company_press_releases%2Fawl-company-press-releases%2Cawl_mobile_search_menu%2Fawl-mobile-search-menu%2Cawl_search_box%2Fawl_search_box%2Cawl_tophat%2Fawl-tophat-lc%2Cbetter_exposed_filters%2Fgeneral%2Cbetter_exposed_filters%2Fselect_all_none%2Cbootstrap_barrio%2Fbreadcrumb%2Cbootstrap_barrio%2Fform%2Cbootstrap_barrio%2Fglobal-styling%2Cbootstrap_sass%2Fglobal-styling%2Ccore%2Fdrupal.autocomplete%2Ccore%2Fdrupal.collapse%2Ccore%2Fhtml5shiv%2Ccpr_issuer_view%2Fcpr-issuer-view-rn-full%2Cdatalayer%2Fbehaviors%2Ceu_cookie_compliance%2Feu_cookie_compliance_default%2Ceuronext_live%2Fglobal-styling%2Cextlink%2Fdrupal.extlink%2Cresponsive_menu%2Fresponsive_menu.config%2Csystem%2Fbase%2Cviews%2Fviews.ajax%2Cviews%2Fviews.module";
        String response = "";
        try {
            URL url = new URL("https://live.euronext.com/en/views/ajax?_wrapper_format=drupal_ajax");
            HttpsURLConnection postConnection = (HttpsURLConnection) url.openConnection();
            postConnection.setConnectTimeout(10000);
            postConnection.setReadTimeout(10000);
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            postConnection.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
            postConnection.setRequestProperty("accept-language", "en-US,en;q=0.9");
            postConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            postConnection.setRequestProperty("sec-ch-ua", "\"Google Chrome\";v=\"89\", \"Chromium\";v=\"89\", \";Not A Brand\";v=\"99\"");
            postConnection.setRequestProperty("sec-ch-ua-mobile", "?0");
            postConnection.setRequestProperty("sec-fetch-mode", "cors");
            postConnection.setRequestProperty("sec-fetch-dest", "empty");
            postConnection.setRequestProperty("sec-fetch-site", "same-origin");
            postConnection.setRequestProperty("upgrade-insecure-requests", "1");
            postConnection.setRequestProperty("x-requested-with", "XMLHttpRequest");
            postConnection.setRequestProperty("referrer", "https://live.euronext.com/en/products/equities/company-news");
            postConnection.setRequestProperty("referrerPolicy", "strict-origin-when-cross-origin");
            postConnection.setRequestProperty("mode", "cors");

            postConnection.setDoOutput(true);
            OutputStream os = postConnection.getOutputStream();
            os.write(obj.getBytes());
            os.flush();
            os.close();
            int statusCode = postConnection.getResponseCode();
            if (statusCode > 299)
                throw new RuntimeException(" -> : RESPONSE CODE :" + statusCode);

            try (InputStream inputStream = postConnection.getInputStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder line = new StringBuilder();
                String line1;
                while ((line1 = reader.readLine()) != null) {
                    line.append(line1);
                }
                response = line.toString();
            }
        } catch (Exception ex) {
//            logger.error(ex.fillInStackTrace().toString());
        }
        return response;
    }

}


