package com.example.bargest;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Categories;
import com.example.bargest.Models.Requests;
import com.example.bargest.Models.Tables;
import com.example.bargest.Utils.parserJsonTables;

import org.json.JSONArray;

import java.util.ArrayList;

public class SingletonBarGest {

    private static SingletonBarGest INSTANCE = null;
    private static RequestQueue volleyQueue = null;
    public ArrayList<Tables> arrayListTables;

    ArrayList<Bills> bills;

    public static synchronized SingletonBarGest getInstance(Context context) {
        if(INSTANCE == null)
        {
            volleyQueue= Volley.newRequestQueue(context);
            INSTANCE = new SingletonBarGest(context);
        }


        return INSTANCE;
    }

    private SingletonBarGest(Context context) {
    }

    public ArrayList<Tables> genereteFakeTableList(Context context){
        arrayListTables = new ArrayList<>();
        String url = "http://192.168.1.204/BarGestWeb/api/web/v1/table/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
              @Override
              public void onResponse(JSONArray response) {
                  Log.i("-->API", response.toString());
                  arrayListTables = parserJsonTables.parserJsonTables(response);
              }
              }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });

        volleyQueue.add(jsonArrayRequest);
        return arrayListTables;
    }
    public ArrayList<Requests> genereteFakeRequestList(){
        ArrayList<Requests> arrayList = new ArrayList<>();

        arrayList.add(new Requests(1,0));
        arrayList.add(new Requests(2,1));
        arrayList.add(new Requests(3,2));
        arrayList.add(new Requests(4,1));
        arrayList.add(new Requests(5,0));
        arrayList.add(new Requests(6,2));
        arrayList.add(new Requests(7,2));
        arrayList.add(new Requests(8,0));
        arrayList.add(new Requests(9,0));
        arrayList.add(new Requests(10,0));


        return arrayList;
    }
    public ArrayList<Bills> generateFakeDetailsBills(){
        bills = new ArrayList<>();

        bills.add(new Bills("Asd",(float) 3.5,2));
        bills.add(new Bills("dd",(float) 2.5,2));
        bills.add(new Bills("oo",(float) 1.2,4));
        bills.add(new Bills("Asd",(float) 10,2));
        bills.add(new Bills("asdf",(float) 3.5,7));
        bills.add(new Bills("fdfff",(float) 3.5,8));
        bills.add(new Bills("vv",(float) 3.5,1));
        bills.add(new Bills("ser",(float) 3.5,2));
        bills.add(new Bills("dffv",(float) 3.5,6));
        bills.add(new Bills("ssa",(float) 3.5,5));
        bills.add(new Bills("ssd",(float) 3.5,9));
        bills.add(new Bills("ddd",(float) 3.5,3));
        bills.add(new Bills("Assasd",(float) 3.5,2));

        return bills;

    }
    public float getTotalBills(){
        float total = 0;
        for (Bills bill:bills) {
            total+=bill.getPrice()*bill.getQuantidade();
        }

        return total;
    }

    public ArrayList<Categories> genereteFakeCategoriesList(){
        ArrayList<Categories> categories = new ArrayList<>();
        categories.add(new Categories("GINS"));
        categories.add(new Categories("Vinhos"));
        categories.add(new Categories("Sandes"));
        categories.add(new Categories("Pratos"));
        categories.add(new Categories("Sumos"));
        categories.add(new Categories("Entradas"));


        return categories;
    }

}
