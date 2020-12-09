package services;

import jdk.nashorn.internal.parser.JSONParser;
import org.eclipse.jetty.util.IO;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sun.util.resources.cldr.aa.CurrencyNames_aa;

import javax.net.ssl.*;
import java.io.*;

import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Integer.parseInt;


public class BR {

    public static void scrape() throws Exception {
        String responseContent = getContent();
        JSONObject obj = new JSONObject(responseContent);
        String response = obj.getJSONObject("d").getString("dados");
        processReponse(response);
    }

    private static void processReponse(String response) {
        String[] arr = response.split("\\$&");
        String[] temporary = null;
        int chunk = 12;
        for (int i = 0; i < arr.length; i += chunk) {
            temporary = Arrays.copyOfRange(arr, i, i + chunk);
//            temporary = arr.slice(i, i + chunk);
            // console.log(temporary)
            if (Arrays.stream(temporary).allMatch(el -> el != null)) {
                processObject(temporary);
                System.out.println("**********************************************************************");
            }
        }


    }

    private static void processObject(String[] arr) {
        if (arr.length < 10) return;
        if ((arr[0].substring(0, 5)).matches("^([\\s\\d]+)$")) {
            System.out.println("company name : " + arr[1]);
            System.out.println("delivery Date : " + getDate(arr[6]));
            System.out.println("url : " + getUrl(arr[10]));
        } else if (arr[0].startsWith("&*")) {
            System.out.println("company name : " + arr[1]);
            System.out.println("delivery Date : " + getDate(arr[6]));
            System.out.println("url : " + getUrl(arr[10]));
        } else {
            System.out.println("company name : " + arr[0]);
            System.out.println("delivery Date : " + getDate(arr[5]));
            System.out.println("url :  " + getUrl(arr[9]));

        }


    }

    private static String getDate(String line) {
        String date = line.replaceAll(".*?\\<(.*)\\>.()", "").trim();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            Date parsed = simpleDateFormat.parse(date);
            date = simpleDateFormat.format(parsed);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date;
    }

    private static String getUrl(String line) {
        Document parsed = Jsoup.parse(line);
        String key = parsed.select("#VisualizarDocumento")
                .attr("onclick")
                .replace("OpenPopUpVer('frmExibirArquivoIPEExterno.aspx?NumeroProtocoloEntrega=", "")
                .replace("')", "")
                .trim();
        return MessageFormat.format("https://www.rad.cvm.gov.br/ENET/frmExibirArquivoIPEExterno.aspx?NumeroProtocoloEntrega={0}", key);
    }

    private static String getContent() throws Exception {
        String dateStart = getDate().get("monthAgo");
        String dateEnd = getDate().get("today");
        dateStart=MessageFormat.format("''{0}''",dateStart);
        dateEnd=MessageFormat.format("''{0}''",dateEnd);
        String obj = "{ dataDe: "+ dateStart+", dataAte: "+ dateEnd+" , empresa: '', setorAtividade: '-1', categoriaEmissor: '-1', situacaoEmissor: '-1', tipoDocumento: '-1', dataReferencia: '', categoria: '8', tipo: '99', especie: '-1', periodo: '2', horaIni: '', horaFim: '', palavraChave:'',ultimaDtRef:'false', tipoEmpresa:'0'}";
        URL url = new URL("https://www.rad.cvm.gov.br/ENET/frmConsultaExternaCVM.aspx/ListarDocumentos");
        HttpsURLConnection postConnection = (HttpsURLConnection) url.openConnection();
        SSLSocketFactory sslSocketFactory = createSslSocketFactory();
        postConnection.setSSLSocketFactory(sslSocketFactory);
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
        postConnection.setRequestProperty("accept-language", "en-US,en;q=0.9");
        postConnection.setRequestProperty("content-type", "application/json; charset=UTF-8");
        postConnection.setRequestProperty("sec-fetch-dest", "empty");
        postConnection.setRequestProperty("sec-fetch-mode", "cors");
        postConnection.setRequestProperty("sec-fetch-site", "same-origin");
        postConnection.setRequestProperty("x-dtpc", "28$18501402_282h15vAPCOUANCAARHEKQMOMMVJPAKBRQOUKCR-0e23");
        postConnection.setRequestProperty("x-requested-with", "XMLHttpRequest");
        postConnection.setRequestProperty("referrer", "https://www.rad.cvm.gov.br/ENET/frmConsultaExternaCVM.aspx");
        postConnection.setRequestProperty("referrerPolicy", "strict-origin-when-cross-origin");
        postConnection.setRequestProperty("mode", "cors");
        postConnection.setRequestProperty("credentials", "include");

        postConnection.setDoOutput(true);
        OutputStream os = postConnection.getOutputStream();
        os.write(obj.getBytes());
        os.flush();
        os.close();


        try (InputStream inputStream = postConnection.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder line = new StringBuilder();
            String line1;
            while ((line1 = reader.readLine()) != null) {
                line.append(line1);
            }
            return line.toString();
        }

    }

    private static Map<String, String> getDate() {
        HashMap<String, String> date = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date result = cal.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String monthAgo = simpleDateFormat.format(result);
        String today = simpleDateFormat.format(new Date());
        date.put("monthAgo", monthAgo);
        date.put("today", today);

        return date;
    }

    private static SSLSocketFactory createSslSocketFactory() throws Exception {
        TrustManager[] byPassTrustManagers = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        }};
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, byPassTrustManagers, new SecureRandom());
        return sslContext.getSocketFactory();
    }
}


