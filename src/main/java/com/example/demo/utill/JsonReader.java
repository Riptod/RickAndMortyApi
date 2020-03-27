package com.example.demo.utill;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.example.demo.entity.Character;
import com.example.demo.exception.LoadDataException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

@Component
public class JsonReader {

    public Long numberOfPage(String json) {
        JSONParser parse = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) parse.parse(json.toString());
        } catch (ParseException e) {
            throw new LoadDataException("Can`t parse json to Object");
        }
        JSONObject object1 = (JSONObject) object.get("info");
        return (Long) object1.get("pages");
    }

    public String readJsonFromUrl(String serviceUrl) throws IOException {
        StringBuilder inline = new StringBuilder();
        URL url = new URL(serviceUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();
        if (responsecode != 200)
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        else {
            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                inline.append(sc.nextLine());
            }
            sc.close();
            return inline.toString();
        }
    }

    public List<Character> getArray(String json) {
        JSONParser parse = new JSONParser();
        JSONObject jobj = null;
        try {
            jobj = (JSONObject) parse.parse(json.toString());
        } catch (ParseException e) {
            throw new LoadDataException("Can`t parse json to Object");
        }
        JSONArray jsonarr_1 = (JSONArray) jobj.get("results");
        List<Character> characters = new ArrayList<>();
        for (int i = 0; i < jsonarr_1.size(); i++) {
            Character character = new Character();
            JSONObject jsonobj_1 = (JSONObject) jsonarr_1.get(i);
            character.setId((Long) jsonobj_1.get("id"));
            character.setName((String) jsonobj_1.get("name"));
            characters.add(character);
        }
        return characters;
    }
}
