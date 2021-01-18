package com.example.bargest.Utils;

import com.example.bargest.Models.Products;
import com.example.bargest.Models.ProductsToBePaid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class parserJsonProducts {
    public static ArrayList<Products> parserProducts(JSONArray json){
        ArrayList<Products> products = new ArrayList<>();
        try {
            for(int i=0; i < json.length(); i++){
                JSONObject product = (JSONObject) json.get(i);
                int id = product.getInt("id");
                float price =(float) product.getDouble("price");
                String name = product.getString("name");
                int category_id = product.getInt("category_id");
                products.add(new Products(id,name,price,0,category_id));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return products;
    }
    public static ArrayList<Products> parserAccountProducts(JSONArray json){
        ArrayList<Products> products = new ArrayList<>();
        try {
            for(int i=0; i < json.length(); i++){
                JSONObject product = (JSONObject) json.get(i);
                int id = product.getInt("id");
                float price =(float) product.getDouble("price");
                String name = product.getString("name");
                int quantity = product.getInt("quantity");
                int category_id = product.getInt("category_id");
                products.add(new Products(id,name,price,quantity, category_id));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return products;
    }

    public static ArrayList<ProductsToBePaid> parserProductsToBePaid(JSONArray json){
        ArrayList<ProductsToBePaid> productstobepaid = new ArrayList<>();
        try {
            for(int i=0; i < json.length(); i++){
                JSONObject product = (JSONObject) json.get(i);
                int id = product.getInt("product_id");
                float price =(float) product.getDouble("product_price");
                String name = product.getString("product_name");
                int quantity = product.getInt("quantity");
                int request_id = product.getInt("request_id");
                int account_id = product.getInt("account_id");
                productstobepaid.add(new ProductsToBePaid(id,name,price,quantity, account_id, request_id, 0));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return productstobepaid;
    }

    public static ArrayList<ProductsToBePaid> parserProductsToBePaidAccount(JSONArray json){
        ArrayList<ProductsToBePaid> productstobepaid = new ArrayList<>();
        try {
            for(int i=0; i < json.length(); i++){
                JSONObject product = (JSONObject) json.get(i);
                int id = product.getInt("id");
                float price =(float) product.getDouble("price");
                String name = product.getString("name");
                int quantity = product.getInt("quantity");
                int category_id = product.getInt("category_id");
                productstobepaid.add(new ProductsToBePaid(id,name,price,quantity, 0, 0, category_id));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return productstobepaid;
    }


}
