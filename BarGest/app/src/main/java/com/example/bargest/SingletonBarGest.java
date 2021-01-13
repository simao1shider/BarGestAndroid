package com.example.bargest;

import android.content.Context;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
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
import com.example.bargest.Listeners.LoginListener;
import com.example.bargest.Listeners.NewRequestListner;
import com.example.bargest.Listeners.ProductsListener;
import com.example.bargest.Listeners.TableListener;
import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Categories;
import com.example.bargest.Models.Products;
import com.example.bargest.Models.Requests;
import com.example.bargest.Models.Tables;
import com.example.bargest.Utils.parserJsonBills;
import com.example.bargest.Utils.parserJsonCategories;
import com.example.bargest.Utils.parserJsonProducts;
import com.example.bargest.Utils.parserJsonRequest;
import com.example.bargest.Utils.parserJsonTables;
import com.example.bargest.Views.Fragments.TablesFragment;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingletonBarGest {

    private static SingletonBarGest INSTANCE = null;
    private static RequestQueue volleyQueue = null;
    //Listeners
    private TableListener tableListener;
    private BillListener billListener;
    private ListRequestsListener listRequestsListener;
    private CategoriesListener categoriesListener;
    private ProductsListener productsListener;
    private NewRequestListner newRequestListner;
    private LoginListener tokenListener;
    private Database localDatabase = null;
    ArrayList<Bills> bills;
    ArrayList<Categories> categories;
    ArrayList<Products> products;
    ArrayList<Requests> requests;
    ArrayList<Tables> tables;
    //Rever este array
    ArrayList<Products> newrequests;
    String url ="http://10.200.20.34/BarGestWeb/api/web/v1/";
    String token;

    public void setTableListener(TableListener tableListener){
        this.tableListener=tableListener;
    }
    public void setBillsListener(BillListener billsListener){
        this.billListener=billsListener;
    }
    public void setCategoriesListener(CategoriesListener categoriesListener){
        this.categoriesListener=categoriesListener;
    }
    public void setProductListener(ProductsListener productListener){
        this.productsListener = productListener;
    }
    public void setNewrequestsListener(NewRequestListner newRequestListner){
        this.newRequestListner = newRequestListner;
    }
    public void setListRequestListener(ListRequestsListener listRequestsListener){
        this.listRequestsListener=listRequestsListener;
    }
    public void setLoginListener(LoginListener token){
        this.tokenListener=token;
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
        localDatabase = new Database(context);
        tables = new ArrayList<Tables>();
        categories = new ArrayList<Categories>();
        products = new ArrayList<Products>();
        requests = new ArrayList<Requests>();
        bills = new ArrayList<Bills>();
        localDatabase = new Database(context);
    }

    public void startNewRequest(){
        newrequests = new ArrayList<>();
    }

    public void addNewRequest(Products product){
        int index=0;
        for (Products oldproduct: newrequests) {
            if(oldproduct.getId()==product.getId()){
                product.setQuantity(oldproduct.getQuantity()+1);
                newrequests.set(index,product);
                newRequestListner.onRefreshListProducts(newrequests);
                return;
            }
            index+=1;
        }
        product.setQuantity(1);
        newrequests.add(product);
        newRequestListner.onRefreshListProducts(newrequests);
    }


    public void loginUserAPI(final String username, final String password, final Context context) {
        if (!isConnectionInternet(context)) {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(Request.Method.GET, url + "employee/login", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context,"Token:"+response,Toast.LENGTH_LONG).show();
                    tokenListener.onRefreshToken(response);
                    SharedPreferences.Editor editor = context.getSharedPreferences("Pref",Context.MODE_PRIVATE).edit();
                    editor.putString("token",response);
                    editor.apply();
                    token = "access-token="+ response.replace("\"", "");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String credentials = username+":"+password;
                    String auth = "Basic "
                            + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", auth);
                    return headers;
                }
            };
            volleyQueue.add(request);
        }
    }
    //---------------TABLES---------------
    public void getAPITableList(final Context context){
        if(isConnectionInternet(context)) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "table/tables?" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.i("API", response.toString());
                    tableListener.onRefreshListTables(parserJsonTables.parserJsonTables(response));
                    tables = parserJsonTables.parserJsonTables(response);
                    addTablesDB(tables);
                    if (tableListener != null)
                        tableListener.onRefreshListTables(tables);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error:"+error.getMessage(), Toast.LENGTH_LONG).show();
                    // TODO: Handle error
                    Log.e("API", error.toString());
                }
            });
            Log.i("API", "teste");
            volleyQueue.add(jsonArrayRequest);
        }
        else{
            tables = localDatabase.getTables();
            if (tableListener != null)
                tableListener.onRefreshListTables(tables);
            toastNotIntenet(context);
        }
    }
    public void getAPITableAccountsList(Context context,String table_id){
        if(isConnectionInternet(context)) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "table/accounts/" + table_id + "?" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.i("API", response.toString());
                    billListener.onRefreshListTables(parserJsonBills.parserJsonBilla(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Log.e("API", error.toString());
                }
            });
            Log.i("API", "teste");
            volleyQueue.add(jsonArrayRequest);
        }
        else {
            toastNotIntenet(context);
        }
    }

    public void addTablesDB(ArrayList<Tables> tablesList) {
        localDatabase.deleteTables();
        for (Tables table : tablesList)
            addTableDB(table);
    }

    public void addTableDB(Tables table) {
        localDatabase.addTable(table);
    }
    //---------------REQUESTS---------------
    public void getAPIListRequests(Context context){
        if(isConnectionInternet(context)) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "request/current?" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.i("API", response.toString());
                    listRequestsListener.onRefreshListTables(parserJsonRequest.parserJsonListRequest(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Log.e("API", error.toString());
                }
            });
            Log.i("API", "teste");
            volleyQueue.add(jsonArrayRequest);
        }
        else{
            toastNotIntenet(context);
        }
    }

    public void getRequestInfo(Context context, int request_id){
        if(isConnectionInternet(context)) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "request/info/" + request_id + "?" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.i("API", response.toString());
                    productsListener.onRefreshListProducts(parserJsonProducts.parserAccountProducts(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Log.e("API", error.toString());
                }
            });
            Log.i("API", "teste");
            volleyQueue.add(jsonArrayRequest);
        }
        else{
            toastNotIntenet(context);
        }
    }

    public void deleteRequest(final Context context, int requestId){
        if(isConnectionInternet(context)) {
            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url + "request/delete/" + requestId+"?"+token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Log.e("API", error.toString());
                    Toast.makeText(context, "Erro ao eliminar", Toast.LENGTH_LONG).show();
                    getAPIListRequests(context);
                }
            });
            Log.i("API", "teste");
            volleyQueue.add(stringRequest);
        }
        else{
            toastNotIntenet(context);
        }
    }

    public void createRequestAccount(final Context context, int accountId, final ArrayList<Products> products, final FragmentManager fragment){
        if(isConnectionInternet(context)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "request/create/account/" + accountId+"?"+token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("API", response);
                    Toast.makeText(context, "Pedido criado com successo", Toast.LENGTH_LONG).show();
                    fragment.popBackStack();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Log.e("API", error.toString());
                    Toast.makeText(context, "Erro ao inserir dados", Toast.LENGTH_LONG).show();
                    getAPIListRequests(context);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    String sproducts = new Gson().toJson(products);
                    params.put("products", sproducts);
                    return params;
                }
            };
            Log.i("API", "teste");
            volleyQueue.add(stringRequest);
        }
        else{
            toastNotIntenet(context);
        }
    }
    public void createRequestTable(final Context context, int tableId,final String accountName ,final ArrayList<Products> products, final FragmentManager fragment){
        if(isConnectionInternet(context)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "request/create/table/" + tableId+"?"+token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("API", response);
                    Toast.makeText(context, "Pedido criado com successo", Toast.LENGTH_LONG).show();
                    fragment.popBackStack();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Log.e("API", error.toString());
                    Toast.makeText(context, "Erro ao inserir dados", Toast.LENGTH_LONG).show();
                    getAPIListRequests(context);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    String sproducts = new Gson().toJson(products);
                    params.put("products", sproducts);
                    params.put("account_name", accountName);
                    return params;
                }
            };
            Log.i("API", "teste");
            volleyQueue.add(stringRequest);
        }
        else{
            toastNotIntenet(context);
        }
    }

    public void editRequest(final Context context, int request_id,final ArrayList<Products> products){
        if(isConnectionInternet(context)) {
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url + "request/edit/" + request_id+"?"+token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("API", response);
                    Toast.makeText(context, "Editado com sucesso", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Log.e("API", error.toString());
                    Toast.makeText(context, "Erro ao inserir dados", Toast.LENGTH_LONG).show();
                    getAPIListRequests(context);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    String sproducts = new Gson().toJson(products);
                    params.put("products", sproducts);
                    return params;
                }
            };
            Log.i("API", "teste");
            volleyQueue.add(stringRequest);
        }
        else{
            toastNotIntenet(context);
        }
    }
    //---------------CATEGORY---------------

    public void getAllCategories(final Context context){
        if(isConnectionInternet(context)) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "category/all?"+token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.i("API", response.toString());
                    categoriesListener.onRefreshCategories(parserJsonCategories.parserJsonCategories(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Log.e("API", error.toString());
                }
            });
            Log.i("API", "teste");
            volleyQueue.add(jsonArrayRequest);
        }
        else{
            toastNotIntenet(context);
        }
    }

    //---------------Products---------------
    public void getAllProducs(){
        //if(isConnectionInternet(context)) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "product/all?"+token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.i("API", response.toString());
                    productsListener.onRefreshArrayProducts(parserJsonProducts.parserProducts(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Log.e("API", error.toString());
                }
            });
            Log.i("API", "teste");
            volleyQueue.add(jsonArrayRequest);
        //}
        //else{
        //    toastNotIntenet(context);
        //}
    }

    public void getProductsByCategory(final Context context,int categoryId){
        if(isConnectionInternet(context)) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "product/category/" + categoryId + "?"+token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.i("API", response.toString());
                    productsListener.onRefreshListProducts(parserJsonProducts.parserProducts(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Log.e("API", error.toString());
                }
            });
            Log.i("API", "teste");
            volleyQueue.add(jsonArrayRequest);
        }
        else{
            toastNotIntenet(context);
        }
    }

    //---------------ACCOUNTS---------------
    public void getAccountProducts(Context context,int accountId){
        if(isConnectionInternet(context)) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "account/info/" + accountId+"?"+token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.i("API", response.toString());
                    productsListener.onRefreshListProducts(parserJsonProducts.parserAccountProducts(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Log.e("API", error.toString());
                }
            });
            Log.i("API", "teste");
            volleyQueue.add(jsonArrayRequest);
        }
        else{
            toastNotIntenet(context);
        }
    }

    public void pay(final Context context, int account_id, final int nif, final FragmentManager fragmentManager){
        if(isConnectionInternet(context)) {
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url + "account/pay/" + account_id+"?"+token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("API", response);
                    Toast.makeText(context, "Pagamento efetuado com sucesso!", Toast.LENGTH_LONG).show();
                    fragmentManager.beginTransaction().replace(R.id.container, new TablesFragment()).commit();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Log.e("API", error.toString());
                    Toast.makeText(context, "Erro no pagamento!", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nif", String.valueOf(nif));
                    return params;
                }
            };
            Log.i("API", "teste");
            volleyQueue.add(stringRequest);
        }
        else{
            toastNotIntenet(context);
        }
    }

    public void paywithsplit(final Context context, int account_id, final int nif, final ArrayList<Products> topay, final FragmentManager fragmentManager){
        if(isConnectionInternet(context)) {
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url + "account/splitpay/" + account_id+"?"+token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("API", response);
                    //Toast.makeText(context,"Pagamento efetuado com sucesso!",Toast.LENGTH_LONG).show();
                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                    fragmentManager.beginTransaction().replace(R.id.container, new TablesFragment()).commit();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Log.e("API", error.toString());
                    Toast.makeText(context, "Erro no pagamento!", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nif", String.valueOf(nif));
                    String productstopay = new Gson().toJson(topay);
                    params.put("products", productstopay);
                    return params;
                }
            };
            Log.i("API", "teste");
            volleyQueue.add(stringRequest);
        }
        else {
            toastNotIntenet(context);
        }
    }

    private static boolean isConnectionInternet(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    private void toastNotIntenet(Context context){
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_nointernet,null);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

}
