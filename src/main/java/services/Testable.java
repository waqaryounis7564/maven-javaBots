package services;

import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.StringJoiner;

public class Testable {

    public static void scrape() throws Exception {

        try {
            String obj = "{ dataDe: '01/11/2020', dataAte: '01/12/2020' , empresa: '', setorAtividade: '-1', categoriaEmissor: '-1', situacaoEmissor: '-1', tipoDocumento: '-1', dataReferencia: '', categoria: '8', tipo: '99', especie: '-1', periodo: '2', horaIni: '', horaFim: '', palavraChave:'',ultimaDtRef:'false', tipoEmpresa:'0'}";
            URL url = new URL("https://www.rad.cvm.gov.br/ENET/frmConsultaExternaCVM.aspx/ListarDocumentos");
            HttpsURLConnection postConnection = (HttpsURLConnection) url.openConnection();
            SSLSocketFactory sslSocketFactory = createSslSocketFactory();
            postConnection.setConnectTimeout(50);
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
            if (postConnection.getResponseCode() > 299) {
                try (InputStream inputStream = postConnection.getErrorStream()) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder line = new StringBuilder();
                    String line1;
                    while ((line1 = reader.readLine()) != null) {
                        line.append(line1);
                    }
                    System.out.println(line.toString());
                }

            } else {
                try (InputStream inputStream = postConnection.getInputStream()) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder line = new StringBuilder();
                    String line1;
                    while ((line1 = reader.readLine()) != null) {
                        line.append(line1);
                    }
                    System.out.println(line.toString());

                }
            }
        } catch (IOException ex) {
            System.out.println(ex.fillInStackTrace());
        }
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




