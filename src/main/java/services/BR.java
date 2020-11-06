//package services;
//
//
//import com.gargoylesoftware.htmlunit.HttpMethod;
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.WebRequest;
//import com.gargoylesoftware.htmlunit.html.DomElement;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
//import com.gargoylesoftware.htmlunit.util.NameValuePair;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.ssl.SSLContextBuilder;
//import org.apache.http.ssl.SSLContexts;
//import utils.RequestSetter;
//
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSocketFactory;
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.security.KeyManagementException;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.CertificateException;
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.zip.GZIPInputStream;
//
//import static utils.ParameterUtils.getSocketFactory;
//
//public class BR {
////    final WebClient webClient = getDomNodeOrDie().getPage().getEnclosingWindow().getWebClient();
////  webClient.getPage(webClient.getOptions().getHomePage());
//
//    public static void scrapeDate() throws IOException {
//        try (WebClient webClient = new WebClient()) {
//            String url="https://www.rad.cvm.gov.br/ENET/frmConsultaExternaCVM.aspx";
//            webClient.getOptions().setCssEnabled(false);
//            webClient.getOptions().setJavaScriptEnabled(true);
//            webClient.getOptions(). setUseInsecureSSL(true);
//            webClient.getOptions().setThrowExceptionOnScriptError(false);
//
//            final HtmlPage page = webClient.getPage(url);
//            DomElement submitBtn=page.querySelector("#btnConsulta");
//            HtmlPage pg2=submitBtn.click();
//            webClient.waitForBackgroundJavaScript(10000);
//            String res=pg2.asXml();
//            System.out.println(res);
//
//        }
//    }
//
//    public static void scrape() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
//
//        SSLContextBuilder SSLBuilder = SSLContexts.custom();
//        File file = new File("C:\\Program Files (x86)\\Java\\jre1.8.0_261\\lib\\security\\keystore.cer");
//        SSLBuilder = SSLBuilder.loadTrustMaterial(file,
//                "changeit".toCharArray());
//        SSLContext sslcontext = SSLBuilder.build();
//        SSLConnectionSocketFactory sslConSocFactory = new SSLConnectionSocketFactory(sslcontext, new NoopHostnameVerifier());
//        HttpClientBuilder clientbuilder = HttpClients.custom();
//        clientbuilder.setSSLSocketFactory(sslConSocFactory);
//        CloseableHttpClient httpclient = clientbuilder.build();
//        HttpPost httpPost = new HttpPost("https://www.rad.cvm.gov.br/ENET/frmConsultaExternaCVM.aspx/ListarDocumentos");
//        HttpResponse httpresponse = httpclient.execute(httpPost);
//        System.out.println(httpresponse.getStatusLine());
//    }
//
//    public static void scraping() throws IOException {
//
//        String cookie = "  _ga=GA1.3.1352294763.1601901539; _gid=GA1.3.1673770607.1602480500; rxVisitor=16025006608559H4ET8ATDGTCJBBDVBIVB2GBKCG4EV0V; ASP.NET_SessionId=ujoeagmggb1nto55wlxhpl55; BIGipServerpool_www.rad.cvm.gov.br_443=1174939658.47873.0000; TS01871345=016e3b076f5ae2dedc2645da6ed4670aaf36243ec0d1616a1149a9bb5444a5257f1b47964f36a47347387ec2d9c122e3753711c804048153c1609c0996d5d932f6b4d7e626; dtCookie=v_4_srv_1_sn_2M09H77LCU1F2FGR661PRNGU1VTRH64T_perc_100000_ol_0_mul_1_app-3Ad94293f33bf4a8d1_1; TS01f82b11=016e3b076f50d9d56eb3fe7092605b6259be6fc945d1616a1149a9bb5444a5257f1b47964ff27b9fbb166b6d21a2d89f9af45c46f7c0d6cf4423abe9be918259e3725dbb02; dtSa=-; dtLatC=2; rxvt=1602504720592|1602502504176; dtPC=1$502920138_431h3vQMMBHMMCMBRMELEUFTFWAKPRJJFHKAVO-0e1";
//        String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
//        final String acceptEncoding = "gzip, deflate, br";
//        final String acceptLanguage = "en-US,en;q=0.9,ur;q=0.8,de;q=0.7,el;q=0.6";
//        String url = "https://www.rad.cvm.gov.br/ENET/frmConsultaExternaCVM.aspx/ListarDocumentos";
//        Map<String, Object> params = new LinkedHashMap<>();
//        params.put("dataDe", "01/10/2020");
//        params.put("dataAte", "14/10/2020");
//        params.put("empresa", "");
//        params.put("setorAtividade", "-1");
//        params.put("categoriaEmissor", "-1");
//        params.put("situacaoEmissor", "-1");
//        params.put("tipoDocumento", "-1");
//        params.put("dataReferencia", "");
//        params.put("categoria", "8");
//        params.put("tipo", "99");
//        params.put("especie", "-1");
//        params.put("periodo", "2");
//        params.put("horaIni", "");
//        params.put("horaFim", "");
//        params.put("palavraChave", "");
//        params.put("ultimaDtRef", "false");
//        params.put("tipoEmpresa", "0");
//        StringBuilder postData = new StringBuilder();
//        String postData1="{ dataDe: '', dataAte: '' , empresa: '', setorAtividade: '-1', categoriaEmissor: '-1', situacaoEmissor: '-1', tipoDocumento: '-2', dataReferencia: '', categoria: '-1', tipo: '-1', especie: '-1', periodo: '0', horaIni: '', horaFim: '', palavraChave:'',ultimaDtRef:'false', tipoEmpresa:'0'}";
////        for (Map.Entry<String, Object> param : params.entrySet()) {
////            if (postData.length() != 0) postData.append("&");
////            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
////            postData.append("=");
////            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
////        }
//        byte[] postDataBytes = postData1.toString().getBytes("UTF-8");
//
//
//        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(url).openConnection();
//        SSLSocketFactory sslSocketFactory = getSocketFactory();
//        if (sslSocketFactory != null)
//            httpsURLConnection.setSSLSocketFactory(sslSocketFactory);
//        httpsURLConnection.setRequestMethod("POST");
//        RequestSetter requestSetter = new RequestSetter();
//        requestSetter.setAccept(accept);
//        requestSetter.setHost("www.rad.cvm.gov.br");
//        requestSetter.setOrigin("https://www.rad.cvm.gov.br");
//        requestSetter.setAcceptEncoding(acceptEncoding);
//        requestSetter.setAcceptLanguage(acceptLanguage);
//        requestSetter.setReferer("https://www.rad.cvm.gov.br/ENET/frmConsultaExternaCVM.aspx");
//        requestSetter.setContentLength(String.valueOf(postData.length()));
//        httpsURLConnection.setDoOutput(true);
//        httpsURLConnection = requestSetter.getHttpsUrlConnection(httpsURLConnection);
//        httpsURLConnection.addRequestProperty("cookie", cookie);
//
//
//        try (OutputStream outputStream = httpsURLConnection.getOutputStream()) {
//            outputStream.write(postDataBytes);
//            getResponseFromHttpConnection(httpsURLConnection);
//        }
//
//
//    }
//
//    private static String getResponseFromHttpConnection(HttpURLConnection httpURLConnection) throws IOException {
//        try (InputStream inputStream = httpURLConnection.getInputStream()) {
//            Reader reader;
//            if ("gzip".equals(httpURLConnection.getContentEncoding())) {
//                reader = new InputStreamReader(new GZIPInputStream(inputStream));
//            } else {
//                reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
//            }
//            try (BufferedReader br = new BufferedReader(reader)) {
//                StringBuilder sb = new StringBuilder();
//                String line;
//                while ((line = br.readLine()) != null) {
//                    sb.append(line).append("\n");
//                    System.out.println(line);
//                }
//                return sb.toString();
//            }
//        }
//    }
//}