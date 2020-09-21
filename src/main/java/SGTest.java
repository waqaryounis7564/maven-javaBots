//package com.x2iq.sbb.bots.service;
//
//import com.google.gson.Gson;
//import com.x2iq.sbb.bots.repo.BotsRepo;
//import com.x2iq.sbb.common.utils.ParameterUtils;
//import com.x2iq.sbb.common.RequestSetter;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Service
//public class SGBotService implements BotService {
//
//    private static final int toolId = 61;
//    private static int count = 0;
//    private BotsRepo botsRepo;
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    public SGBotService(BotsRepo botsRepo) {
//        this.botsRepo = botsRepo;
//    }
//
//    @Override
//    public boolean startBot() {
//        count = 0;
//        getSGBotStarted();
//        return botsRepo.setToolCompleted(true, count, toolId) > 0;
//    }
//
//    @Override
//    public String getName() {
//        return "SGBot";
//    }
//
//
//    private String downloadPage() throws IOException {
//        String url = "https://api.sgx.com/announcements/v1.1/?periodstart=20200725_160000&periodend=20200826_155959&cat=ANNC&sub=ANNC13&pagestart=0&pagesize=100";
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("GET");
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");
//        con.setRequestProperty("authorizationtoken", "842FLqEItEE94BX0k7pXT0zXn4l4RCVmUwuK9eXyQUk5FWySkcgvM7/ouIxtpYHPRrOHcTcVoBMOcI/Ynst/aw5AF3+efJCeR7B7OnqgJCeZZuHrzIamEuJNK0/zgZCq");
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//
//        }
//        in.close();
//        return response.toString();
//    }
//
//    private void getSGBotStarted() {
//        final int countryId = botsRepo.checkIsValidCountry(toolId);
//        String strSourceurl = null;
//        String strHeadline = null;
//        String strDate = null;
//        String strCompany = null;
//        String rnsId = null;
//        if (countryId < 0) return;
//
//        String response = null;
//        try {
//            response = downloadPage();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (response == null) return;
//        JSONObject obj = new JSONObject(response);
//        JSONArray jsonArray = obj.getJSONArray("data");
//        if (jsonArray == null)
//            return;
//
//        for (int i = 0; i < jsonArray.length(); i++) {
//            strHeadline = jsonArray.getJSONObject(i).getString("title");
//            strSourceurl = jsonArray.getJSONObject(i).getString("url");
//            strDate = jsonArray.getJSONObject(i).getString("submission_date");
//            rnsId = jsonArray.getJSONObject(i).getString("id");
//            strCompany = jsonArray.getJSONObject(i).getJSONArray("issuers").getJSONObject(0).getString("issuer_name");
//
//            try {
//                strDate = utils.ParameterUtils.getDateInStringFromDate(new SimpleDateFormat("yyyyMMdd").parse(strDate));
//            } catch (ParseException e) {
//                strDate = utils.ParameterUtils.getDateInStringFromDate(new Date());
//            }
//            if (!botsRepo.checkIsRnsAlreadyExist(rnsId, countryId)) {
//                botsRepo.insertAnnouncement(strHeadline, strDate, 14, 3, strSourceurl, countryId, strCompany, rnsId);
//                count++;
//            }
//
////      System.out.println(headline);
////      System.out.println(rnsId);
////      System.out.println(company);
////      System.out.println(srcUrl);
////      System.out.println(date);
////      System.out.println("----------");
//        }
//
//
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
