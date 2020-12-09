package com.example.bargest.Utils;

import com.example.bargest.Models.Products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class parserJsonProducts {
    public static ArrayList<Products> parserProducts(JSONArray json){
        ArrayList<Products> requests = new ArrayList<>();
        try {
            for(int i=0; i < json.length(); i++){
                JSONObject product = (JSONObject) json.get(i);
                int id = product.getInt("id");
                float price =(float) product.getDouble("price");
                String name = product.getString("name");
                requests.add(new Products(id,name,price,0));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return requests;
    }
    public static ArrayList<Products> parserAccountProducts(JSONArray json){
        ArrayList<Products> requests = new ArrayList<>();
        try {
            for(int i=0; i < json.length(); i++){
                JSONObject product = (JSONObject) json.get(i);
                int id = product.getInt("id");
                float price =(float) product.getDouble("price");
                String name = product.getString("name");
                int quantity = product.getInt("quantity");
                requests.add(new Products(id,name,price,quantity));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return requests;
    }


}
