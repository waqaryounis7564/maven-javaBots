package services;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;

public class Testing {
    public static void crawl() throws JSONException {

        JSONArray jsonArray = new JSONArray("{" + getJson());

    }

    private static JSONArray getJson() {
        JSONArray pets = new JSONArray();
        pets.put("cat");
        pets.put("dog");
        return pets;
    }


}
