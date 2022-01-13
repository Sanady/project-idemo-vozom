package com.example.projekatidemovozom.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class CityModel {
    private String city;
    public CityModel() { }

    public CityModel(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public static CityModel parseJSONObject(JSONObject object) {
        CityModel city = new CityModel();
        try {
            if(object.has("city")) {
                city.setCity(object.getString("city"));
            }
        } catch (Exception e) {

        }
        return city;
    }

    public static LinkedList<CityModel> parseJSONArray(JSONArray array) {
        LinkedList<CityModel> list = new LinkedList<>();
        try {
            for(int i = 0; i < array.length(); i++) {
                CityModel city = parseJSONObject(array.getJSONObject(i));
                list.add(city);
            }
        } catch (Exception e) {

        }
        return list;
    }
}
