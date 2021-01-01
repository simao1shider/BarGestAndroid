package com.example.bargest.Utils;

import com.example.bargest.Models.Tables;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class parserJsonTables {
    public static ArrayList<Tables> parserJsonTables(JSONArray json){
        ArrayList<Tables> listTables = new ArrayList<>();

        try {
            for(int i=0; i < json.length(); i++){
                JSONObject table = (JSONObject) json.get(i);
                int id = table.getInt("id");
                int number = table.getInt("number");
                int status = table.getInt("status");
                double total = table.getDouble("total");
                listTables.add(new Tables(id, number, status,total));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return listTables;
    }
}
