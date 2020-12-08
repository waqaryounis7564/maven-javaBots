package services;

import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;

import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import static java.lang.Integer.parseInt;


public class BR {

    public static void scrape() throws Exception {
        JSONObject jsonObject=new JSONObject(getContent());
        String response = jsonObject.getString("dados");

    }
    private static void processReponse(String response){




    }
    private static void processObject(String [] arr){
        if (arr.length < 10) return;
        if (Integer.class.isInstance(parseInt(arr[0].substring(0, 5)))) {
            System.out.println("company name : "+arr[1]);
            System.out.println("delivery Date : "+ arr[6]);
            System.out.println("url :  https://www.rad.cvm.gov.br/ENET/frmExibirArquivoIPEExterno.aspx?NumeroProtocoloEntrega=" + arr[10]);
        } else if (arr[0].startsWith("&*")) {
            System.out.println("company name : "+ arr[1]);
            System.out.println("delivery Date : "+ arr[6]);
            System.out.println("url :  https://www.rad.cvm.gov.br/ENET/frmExibirArquivoIPEExterno.aspx?NumeroProtocoloEntrega=" + arr[10]);
        } else {
            System.out.println("company name : "+ arr[0]);
            System.out.println("delivery Date : "+ arr[5]);
            System.out.println("url :  https://www.rad.cvm.gov.br/ENET/frmExibirArquivoIPEExterno.aspx?NumeroProtocoloEntrega=" + arr[9]);

        }


}



    private static String  getContent() throws Exception {
        String obj = "{ dataDe: '01/12/2020', dataAte: '08/12/2020' , empresa: '', setorAtividade: '-1', categoriaEmissor: '-1', situacaoEmissor: '-1', tipoDocumento: '-1', dataReferencia: '', categoria: '8', tipo: '99', especie: '-1', periodo: '2', horaIni: '', horaFim: '', palavraChave:'',ultimaDtRef:'false', tipoEmpresa:'0'}";
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


