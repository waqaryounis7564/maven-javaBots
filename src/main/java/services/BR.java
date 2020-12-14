package services;


import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ParameterUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class BR {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public  void scrape() {
        try {
            String responseContent = getContent();
            JSONObject obj = new JSONObject(responseContent);
            String response = obj.getJSONObject("d").getString("dados");
            processReponse(response);
        } catch (JSONException ex) {
            logger.error(ex.getMessage());

        }
    }

    private  void processReponse(String response) {
        String[] arr = response.split("\\$&");
        String[] temporary = null;
        int chunk = 12;
        for (int i = 0; i < arr.length; i += chunk) {
            temporary = Arrays.copyOfRange(arr, i, i + chunk);
            if (Arrays.stream(temporary).allMatch(Objects::nonNull)) {
                processObject(temporary);

            }
        }


    }

    private void processObject(String[] arr) {
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

    private String getDate(String line) {
        String date = line.replaceAll(".*?\\<(.*)\\>.()", "").trim();
        String thisFormat="dd/MM/yyyy HH:mm";
        String requiredFormat="yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            Date parsed = simpleDateFormat.parse(date);
            date = simpleDateFormat.format(parsed);

        } catch (ParseException ex) {
            logger.error(ex.getMessage());
        }

        return ParameterUtils.getDateInYourFormat(date, thisFormat, requiredFormat);

    }

    private static String getUrl(String line) {
        Document parsed = Jsoup.parse(line);
        String key = parsed.select("#VisualizarDocumento")
                .attr("onclick")
                .replace("OpenPopUpVer('frmExibirArquivoIPEExterno.aspx?NumeroProtocoloEntrega=", "")
                .replace("')", "")
                .trim();
        if (key.isEmpty()) throw new RuntimeException("URL key not found for BRAZIL source");
        return MessageFormat.format("https://www.rad.cvm.gov.br/ENET/frmExibirArquivoIPEExterno.aspx?NumeroProtocoloEntrega={0}", key);
    }

    private String getContent() {
        String response = "";
        String dateStart = getDate().get("monthAgo");
        String dateEnd = getDate().get("today");
        dateStart = MessageFormat.format("''{0}''", dateStart);
        dateEnd = MessageFormat.format("''{0}''", dateEnd);
        String obj = "{ dataDe: " + dateStart + ", dataAte: " + dateEnd + " , empresa: '', setorAtividade: '-1', categoriaEmissor: '-1', situacaoEmissor: '-1', tipoDocumento: '-1', dataReferencia: '', categoria: '8', tipo: '99', especie: '-1', periodo: '2', horaIni: '', horaFim: '', palavraChave:'',ultimaDtRef:'false', tipoEmpresa:'0'}";

        try {
            URL url = new URL("https://www.rad.cvm.gov.br/ENET/frmConsultaExternaCVM.aspx/ListarDocumentos");
            HttpsURLConnection postConnection = (HttpsURLConnection) url.openConnection();
            SSLSocketFactory sslSocketFactory = createSslSocketFactory();
            postConnection.setConnectTimeout(5000);
            postConnection.setReadTimeout(10000);
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
            int statusCode = postConnection.getResponseCode();
            if (statusCode > 299) throw new RuntimeException("BRAZIL RESPONSE CODE :" + statusCode);

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
            logger.error(ex.fillInStackTrace().toString());
        }
        return response;
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


//    private void insertAnnouncement(String companyName, String url, String time) {
//        if (!botsRepo.checkIsurlAlreadyExist(url, countryId)) {
//
//            botsRepo.insertAnnouncement(null, time, 14, 3, url, 76, companyName, null);
//        }
//    }
}


