package com.example.bargest.Utils;

import com.example.bargest.Models.Bills;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class parserJsonBills {
    public static ArrayList<Bills> parserJsonBilla(JSONArray json){
        ArrayList<Bills> listbills = new ArrayList<>();

        try {
            for(int i=0; i < json.length(); i++){
                JSONObject bill = (JSONObject) json.get(i);
                int id = bill.getInt("id");
                String  name = bill.getString("name");
                double total = bill.getDouble("total");
                int idMesa = bill.getInt("table_id");
                listbills.add(new Bills(name, (float)total, id, idMesa));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return listbills;
    }
}
