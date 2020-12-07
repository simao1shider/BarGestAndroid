package com.example.bargest;

import android.content.Context;

import android.util.Log;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bargest.Listeners.BillListener;
import com.example.bargest.Listeners.CategoriesListener;
import com.example.bargest.Listeners.ListRequestsListener;
import com.example.bargest.Listeners.TableListener;
import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Requests;
import com.example.bargest.Utils.parserJsonBills;
import com.example.bargest.Utils.parserJsonCategories;
import com.example.bargest.Utils.parserJsonRequest;
import com.example.bargest.Utils.parserJsonTables;

import org.json.JSONArray;

import java.util.ArrayList;

public class SingletonBarGest {

    private static SingletonBarGest INSTANCE = null;
    private static RequestQueue volleyQueue = null;
    //Listeners
    private TableListener tableListener;
    private BillListener billListener;
    private ListRequestsListener listRequestsListener;
    private CategoriesListener categoriesListener;
    ArrayList<Bills> bills;
    String url ="http://192.168.1.180/BarGestWeb/api/web/v1/";

    public void setTableListener(TableListener tableListener){
        this.tableListener=tableListener;
    }
    public void setBillsListener(BillListener billsListener){
        this.billListener=billsListener;
    }
    public void setCategoriesListener(CategoriesListener categoriesListener){
        this.categoriesListener=categoriesListener;
    }


    public void setListRequestListener(ListRequestsListener listRequestsListener){
        this.listRequestsListener=listRequestsListener;
    }

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
    //---------------TABLES---------------
    public void getAPITableList(Context context){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest (Request.Method.GET, url+"table", null, new Response.Listener<JSONArray>() {
              @Override
              public void onResponse(JSONArray response) {
                  Log.i("API", response.toString());
                  tableListener.onRefreshListTables(parserJsonTables.parserJsonTables(response));
              }
              }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("API",error.toString());
                    }
                });
        Log.i("API","teste");
        volleyQueue.add(jsonArrayRequest);
    }
    public void getAPITableAccountsList(Context context,String table_id){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest (Request.Method.GET, url+"table/accounts/"+table_id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("API", response.toString());
                billListener.onRefreshListTables(parserJsonBills.parserJsonBilla(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.e("API",error.toString());
            }
        });
        Log.i("API","teste");
        volleyQueue.add(jsonArrayRequest);
    }
    //---------------REQUESTS---------------
    public void getAPIListRequests(Context context){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest (Request.Method.GET, url+"request/current", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("API", response.toString());
                listRequestsListener.onRefreshListTables(parserJsonRequest.parserJsonListRequest(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.e("API",error.toString());
            }
        });
        Log.i("API","teste");
        volleyQueue.add(jsonArrayRequest);
    }

    public void deleteRequest(final Context context, int requestId){
        StringRequest stringRequest = new StringRequest (Request.Method.DELETE, url+"request/delete/"+requestId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context,response,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.e("API",error.toString());
                Toast.makeText(context,"Erro ao eliminar",Toast.LENGTH_LONG).show();
                getAPIListRequests(context);
            }
        });
        Log.i("API","teste");
        volleyQueue.add(stringRequest);
    }

    //---------------CATEGORY---------------

    public void getAllCategories(final Context context){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest (Request.Method.GET, url+"category/all", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("API", response.toString());
                categoriesListener.onRefreshCategories(parserJsonCategories.parserJsonCategories(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.e("API",error.toString());
            }
        });
        Log.i("API","teste");
        volleyQueue.add(jsonArrayRequest);
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



}
