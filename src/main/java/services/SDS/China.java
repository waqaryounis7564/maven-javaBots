package services.SDS;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class China {
    public static void crawl(){
        String content = getContent();
        System.out.println(content);
    }

    private static String getContent(){
        String response = "";
        String obj="q=62&q=000869&q=%E5%BC%A0%E8%A3%95%EF%BC%A1&q=28&q=0.0370&q=13.16&q=63&q=000877&q=%E5%A4%A9%E5%B1%B1%E8%82%A1%E4%BB%BD&q=2.51&q=64&q=000878&q=%E4%BA%91%E5%8D%97%E9%93%9C%E4%B8%9A&q=4.08&q=65&q=000883&q=%E6%B9%96%E5%8C%97%E8%83%BD%E6%BA%90&q=7.78&q=66&q=000887&q=%E4%B8%AD%E9%BC%8E%E8%82%A1%E4%BB%BD&q=8.14&q=67&q=000898&q=%E9%9E%8D%E9%92%A2%E8%82%A1%E4%BB%BD&q=36.78&q=68&q=000921&q=%E6%B5%B7%E4%BF%A1%E5%AE%B6%E7%94%B5&q=18.72&q=69&q=000930&q=%E4%B8%AD%E7%B2%AE%E7%A7%91%E6%8A%80&q=4.48&q=70&q=000937&q=%E5%86%80%E4%B8%AD%E8%83%BD%E6%BA%90&q=25.80&q=71&q=000959&q=%E9%A6%96%E9%92%A2%E8%82%A1%E4%BB%BD&q=6.34&q=72&q=000960&q=%E9%94%A1%E4%B8%9A%E8%82%A1%E4%BB%BD&q=10.80&q=73&q=000970&q=%E4%B8%AD%E7%A7%91%E4%B8%89%E7%8E%AF&q=17.41&q=74&q=000975&q=%E9%93%B6%E6%B3%B0%E9%BB%84%E9%87%91&q=33.99&q=75&q=000983&q=%E5%B1%B1%E8%A5%BF%E7%84%A6%E7%85%A4&q=8.20&q=76&q=000988&q=%E5%8D%8E%E5%B7%A5%E7%A7%91%E6%8A%80&q=3.22&q=77&q=000990&q=%E8%AF%9A%E5%BF%97%E8%82%A1%E4%BB%BD&q=11.35&q=78&q=000997&q=%E6%96%B0%E5%A4%A7%E9%99%86&q=4.54&q=79&q=000998&q=%E9%9A%86%E5%B9%B3%E9%AB%98%E7%A7%91&q=3.69&q=80&q=000999&q=%E5%8D%8E%E6%B6%A6%E4%B8%89%E4%B9%9D&q=1.56&q=81&q=001914&q=%E6%8B%9B%E5%95%86%E7%A7%AF%E4%BD%99&q=16.89&q=82&q=002001&q=%E6%96%B0%E5%92%8C%E6%88%90&q=2.72&q=83&q=002002&q=%E9%B8%BF%E8%BE%BE%E5%85%B4%E4%B8%9A&q=7.24&q=84&q=002004&q=%E5%8D%8E%E9%82%A6%E5%81%A5%E5%BA%B7&q=6.33&q=85&q=002010&q=%E4%BC%A0%E5%8C%96%E6%99%BA%E8%81%94&q=5.22&q=86&q=002013&q=%E4%B8%AD%E8%88%AA%E6%9C%BA%E7%94%B5&q=13.65&q=87&q=002019&q=%E4%BA%BF%E5%B8%86%E5%8C%BB%E8%8D%AF&q=2.97&q=88&q=002028&q=%E6%80%9D%E6%BA%90%E7%94%B5%E6%B0%94&q=2.43&q=89&q=002030&q=%E8%BE%BE%E5%AE%89%E5%9F%BA%E5%9B%A0&q=2.46&q=90&q=002038&q=%E5%8F%8C%E9%B9%AD%E8%8D%AF%E4%B8%9A&q=2.47&q=91&q=002048&q=%E5%AE%81%E6%B3%A2%E5%8D%8E%E7%BF%94&q=2.00&q=92&q=002051&q=%E4%B8%AD%E5%B7%A5%E5%9B%BD%E9%99%85&q=23.92&q=93&q=002056&q=%E6%A8%AA%E5%BA%97%E4%B8%9C%E7%A3%81&q=14&q=0.0380&q=1.00&q=94&q=10.48&q=95&q=002064&q=%E5%8D%8E%E5%B3%B0%E5%8C%96%E5%AD%A6&q=48.83&q=96&q=002074&q=%E5%9B%BD%E8%BD%A9%E9%AB%98%E7%A7%91&q=4.10&q=97&q=002075&q=%E6%B2%99%E9%92%A2%E8%82%A1%E4%BB%BD&q=7.06&q=98&q=002078&q=%E5%A4%AA%E9%98%B3%E7%BA%B8%E4%B8%9A&q=6.25&q=99&q=002080&q=%E4%B8%AD%E6%9D%90%E7%A7%91%E6%8A%80&q=11.72&q=100&q=002081&q=%E9%87%91%E8%9E%B3%E8%9E%82&q=13.20&q=101&q=002092&q=%E4%B8%AD%E6%B3%B0%E5%8C%96%E5%AD%A6&q=6.87&q=102&q=002093&q=%E5%9B%BD%E8%84%89%E7%A7%91%E6%8A%80&q=15.56&q=103&q=002100&q=%E5%A4%A9%E5%BA%B7%E7%94%9F%E7%89%A9&q=1.36&q=104&q=002110&q=%E4%B8%89%E9%92%A2%E9%97%BD%E5%85%89&q=4.91&q=105&q=002124&q=%E5%A4%A9%E9%82%A6%E8%82%A1%E4%BB%BD&q=3.67&q=106&q=002128&q=%E9%9C%B2%E5%A4%A9%E7%85%A4%E4%B8%9A&q=24.71&q=107&q=002129&q=%E4%B8%AD%E7%8E%AF%E8%82%A1%E4%BB%BD&q=100.00&q=108&q=002131&q=%E5%88%A9%E6%AC%A7%E8%82%A1%E4%BB%BD&q=27.03&q=109&q=002138&q=%E9%A1%BA%E7%BB%9C%E7%94%B5%E5%AD%90&q=2.58&q=110&q=002152&q=%E5%B9%BF%E7%94%B5%E8%BF%90%E9%80%9A&q=4.82&q=111&q=002155&q=%E6%B9%96%E5%8D%97%E9%BB%84%E9%87%91&q=52.12&q=112&q=002156&q=%E9%80%9A%E5%AF%8C%E5%BE%AE%E7%94%B5&q=2.66&q=113";
        String source="https://translate.googleapis.com/translate_a/t?anno=3&client=te_lib&format=html&v=1.0&key=AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw&logld=vTE_20201130_00&sl=auto&tl=en&tc=5&sr=1&tk=43789.425451&mode=1";
        try{
            URL url=new URL(source);
            HttpsURLConnection postConnection = (HttpsURLConnection) url.openConnection();
            postConnection.setConnectTimeout(10000);
            postConnection.setReadTimeout(10000);
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            postConnection.setRequestProperty("accept","*/*");
            postConnection.setRequestProperty("accept-language", "en-GB,en-US,en;q=0.9");
            postConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            postConnection.setRequestProperty("sec-fetch-dest", "empty");
            postConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            postConnection.setRequestProperty("sec-fetch-mode", "cors");
            postConnection.setRequestProperty("sec-fetch-site", "cross-site");
            postConnection.setRequestProperty("x-client-data", "CJK2yQEIpbbJAQjBtskBCKmdygEI+MfKAQikzcoBCNzVygEIqJzLAQjGnMsBCOOcywEIqJ3LAQ==");
            postConnection.setRequestProperty("referrer", "http://www.csf.com.cn/");
            postConnection.setRequestProperty("referrerPolicy", "strict-origin-when-cross-origin");
            postConnection.setDoOutput(true);
            OutputStream os = postConnection.getOutputStream();
            os.write(obj.getBytes());
            os.flush();
            os.close();
            int statusCode = postConnection.getResponseCode();
            if (statusCode > 299)
                throw new RuntimeException("China " + " -> : RESPONSE CODE :" + statusCode);

            try (InputStream inputStream = postConnection.getInputStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder line = new StringBuilder();
                String line1;
                while ((line1 = reader.readLine()) != null) {
                    line.append(line1);
                }
                response = line.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    return response;
    }
}
