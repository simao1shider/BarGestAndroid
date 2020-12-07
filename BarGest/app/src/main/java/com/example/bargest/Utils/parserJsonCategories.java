package com.example.bargest.Utils;

import com.example.bargest.Models.Categories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class parserJsonCategories {

    public static ArrayList<Categories> parserJsonCategories(JSONArray json){
        ArrayList<Categories> listCategories = new ArrayList<>();

        try {
            for(int i=0; i < json.length(); i++){
                JSONObject category = (JSONObject) json.get(i);
                int id = category.getInt("id");
                String name  = category.getString("name");
                listCategories.add(new Categories(id, name));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return listCategories;
    }
}
