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
import com.example.bargest.Listeners.ProductsToBePaidListener;
import com.example.bargest.Listeners.TableListener;
import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Categories;
import com.example.bargest.Models.Products;
import com.example.bargest.Models.ProductsToBePaid;
import com.example.bargest.Models.Requests;
import com.example.bargest.Models.Tables;
import com.example.bargest.Models.views.ListRequests;
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
import java.util.List;
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
    private ProductsToBePaidListener productstobepaidListener;
    private NewRequestListner newRequestListner;
    private LoginListener tokenListener;
    private Database localDatabase = null;
    ArrayList<Bills> bills;
    ArrayList<Categories> categories;
    ArrayList<Products> products;
    ArrayList<Products> productsbycategory;
    ArrayList<ProductsToBePaid> productsaccountaux;
    ArrayList<ProductsToBePaid> productsToBePaid;
    ArrayList<Requests> requests;
    ArrayList<ListRequests> listrequests;
    ArrayList<Tables> tables;
    ArrayList<Products> newrequests;
    String url ="http://192.168.1.102/BarGestWeb/api/web/v1/";
    String token;

    //region LISTENERS SECTION
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
    public void setProductsToBePaidListener(ProductsToBePaidListener productsToBePaidListener){
        this.productstobepaidListener = productsToBePaidListener;
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
    //endregion

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
        tables = new ArrayList<>();
        categories = new ArrayList<>();
        products = new ArrayList<>();
        requests = new ArrayList<>();
        bills = new ArrayList<>();
    }

    public void getAllArrayList(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "productstobepaid/all?"+token, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("API", response.toString());
                productsToBePaid = parserJsonProducts.parserProductsToBePaid(response);
                addProductsToBePaidDB(productsToBePaid);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.e("API", error.toString());
            }
        });
        volleyQueue.add(jsonArrayRequest);

        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Request.Method.GET, url + "product/all?"+token, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("API", response.toString());
                products = parserJsonProducts.parserProducts(response);
                addProductsDB(products);;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.e("API", error.toString());
            }
        });
        volleyQueue.add(jsonArrayRequest1);

        JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, url + "table/all?" + token, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("API", response.toString());

                tables = parserJsonTables.parserJsonTables(response);
                addTablesDB(tables);
                if (tableListener != null)
                    tableListener.onRefreshListTables(tables);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.e("API", error.toString());
            }
        });
        volleyQueue.add(jsonArrayRequest2);

        JsonArrayRequest jsonArrayRequest3 = new JsonArrayRequest(Request.Method.GET, url + "account/all?" + token, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("API", response.toString());
                bills = parserJsonBills.parserJsonBilla(response);
                addBillsDB(bills);
                if (billListener != null)
                    billListener.onRefreshListTables(bills);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.e("API", error.toString());
            }
        });
        volleyQueue.add(jsonArrayRequest3);

        JsonArrayRequest jsonArrayRequest4 = new JsonArrayRequest(Request.Method.GET, url + "category/all?"+token, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("API", response.toString());
                categories = parserJsonCategories.parserJsonCategories(response);

                addCategoriesDB(categories);
                if (categoriesListener != null)
                    categoriesListener.onRefreshCategories(categories);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.e("API", error.toString());
            }
        });
        volleyQueue.add(jsonArrayRequest4);
    }

    //region REQUEST SECTION
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
    //endregion

    //region LOGIN SECTION
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
    //endregion

    //region TABLE SECTION
        //region API SECTION
        public ArrayList<Tables> getAPITableList(final Context context){
            if(isConnectionInternet(context)) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "table/all?" + token, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("API", response.toString());
                        //tableListener.onRefreshListTables(parserJsonTables.parserJsonTables(response));
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
                return null;
            }
            else{
                tables = localDatabase.getTables();
                toastNotIntenet(context);
                return tables;
            }
        }

        public ArrayList<Bills> getAPITableAccountsList(Context context,String table_id){
            if(isConnectionInternet(context)) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "table/" + table_id + "?" + token, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("API", response.toString());
                        bills = parserJsonBills.parserJsonBilla(response);
                        addBillsDB(bills);
                        if (billListener != null)
                            billListener.onRefreshListTables(bills);
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
                return null;
            }
            else {
                bills = getBillsDB();
                toastNotIntenet(context);
                return getBillsFromTable(Integer.parseInt(table_id));
            }
        }
        //endregion

        //region DataBase SECTION
            //region Tables
            public Tables getTable(int id) {
                for (Tables table : tables)
                    if (table.getId() == id)
                        return table;
                return null;
            }

            public ArrayList<Tables> getTablesDB() {
                tables = localDatabase.getTables();
                return tables;
            }

            public void addTableDB(Tables table) {
                localDatabase.addTable(table);
            }

            public void addTablesDB(ArrayList<Tables> tables) {
                localDatabase.deleteTables();
                for (Tables table : tables)
                    addTableDB(table);
            }

            public void removeTableDB(int id) {
                Tables table = getTable(id);

                if (table != null)
                    if (localDatabase.deleteTable(id))
                        tables.remove(table);
            }

            public void updateTableDB(Tables ntable) {
                Tables table = getTable(ntable.getId());

                if (table != null) {
                    if (localDatabase.editTable(ntable)) {
                        table.setId(ntable.getId());
                        table.setNumber(ntable.getNumber());
                        table.setStatus(ntable.getStatus());
                        table.setTotal(ntable.getTotal());
                    }
                }
            }
            //endregion
        //endregion
    //endregion

    //region REQUEST SECTION
        //region API SECTION
        public ArrayList<ListRequests> getAPIListRequests(Context context){
            if(isConnectionInternet(context)) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "request/currentactive?" + token, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("API", response.toString());
                        listrequests = parserJsonRequest.parserJsonListRequest(response);
                        addRequestsDB(listrequests);
                        if(listRequestsListener != null)
                            listRequestsListener.onRefreshListTables(listrequests);
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
                return null;
            }
            else{
                listrequests = getRequestsDB();
                toastNotIntenet(context);
                return listrequests;
            }
        }

        public ArrayList<ProductsToBePaid> getRequestInfo(Context context, int request_id){
            if(isConnectionInternet(context)) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "request/info/" + request_id + "?" + token, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("API", response.toString());
                        productsaccountaux = parserJsonProducts.parserProductsToBePaidAccount(response);
                        if (productstobepaidListener != null)
                            productstobepaidListener.onRefreshListProducts(productsaccountaux);
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
                return null;
            }
            else{
                toastNotIntenet(context);
                return getProductsByRequestId(request_id);
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

        public void editRequest(final Context context, int request_id,final ArrayList<ProductsToBePaid> products){
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

        public void updatestatusRequest(final Context context, int request_id){
        if(isConnectionInternet(context)) {
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url + "request/"+request_id+"/updatestatus?"+token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("API", response);
                    getAPIListRequests(context);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro ao atualizar o estado do pedido", Toast.LENGTH_LONG).show();
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
        //endregion

        //region Database SECTION
        public ListRequests getRequest(int id) {
            for (ListRequests request : listrequests)
                if (request.getId() == id)
                    return request;
            return null;
        }

        public ArrayList<ListRequests> getRequestsDB() {
            listrequests = localDatabase.getRequests();
            return listrequests;
        }

        public void addRequestDB(ListRequests request) {
            localDatabase.addRequests(request);
        }

        public void addRequestsDB(ArrayList<ListRequests> requests) {
            localDatabase.deleteRequests();
            for (ListRequests request : listrequests)
                addRequestDB(request);
        }

        public void removeRequestDB(int id) {
            ListRequests request = getRequest(id);

            if (request != null)
                if (localDatabase.deleteRequest(id))
                    listrequests.remove(request);
        }

        public void updateRequestDB(ListRequests nrequest) {
            ListRequests request = getRequest(nrequest.getId());

            if (request != null) {
                if (localDatabase.editRequest(nrequest)) {
                    request.setId(nrequest.getId());
                    request.setDateTime(nrequest.getDateTime());
                    request.setStatus(nrequest.getStatus());
                    request.setTable_number(nrequest.getTable_number());
                }
            }
        }
        //endregion
    //endregion

    //region CATEGORY SECTION
        //region API SECTION
        public ArrayList<Categories> getAllCategories(final Context context){
            if(isConnectionInternet(context)) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "category/all?"+token, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("API", response.toString());
                        categories = parserJsonCategories.parserJsonCategories(response);

                        addCategoriesDB(categories);
                        if (categoriesListener != null)
                            categoriesListener.onRefreshCategories(categories);

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
                return null;
            }
            else{
                categories = localDatabase.getCategories();
                toastNotIntenet(context);
                return categories;
            }
        }
        //endregion

        //region Database SECTION
        public Categories getCategory(int id) {
            for (Categories category : categories)
                if (category.getId() == id)
                    return category;
            return null;
        }

        public ArrayList<Categories> getCategoriesDB() {
            categories = localDatabase.getCategories();
            return categories;
        }

        public void addCategoryDB(Categories category) {
            localDatabase.addCategory(category);
        }

        public void addCategoriesDB(ArrayList<Categories> categories) {
            localDatabase.deleteCategories();
            for (Categories category : categories)
                addCategoryDB(category);
        }

        public void removeCategoryDB(int id) {
            Categories category = getCategory(id);

            if (category != null)
                if (localDatabase.deleteCategory(id))
                    categories.remove(category);
        }

        public void updateCategoryDB(Categories ncategory) {
            Categories category = getCategory(ncategory.getId());

            if (category != null) {
                if (localDatabase.editCategory(ncategory)) {
                    category.setId(ncategory.getId());
                    category.setName(ncategory.getName());
                }
            }
        }
        //endregion
    //endregion

    //region PRODUCT SECTION
        //region API SECTION
        public ArrayList<Products> getAllProducts(final Context context){
            if(isConnectionInternet(context)) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "product/all?"+token, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("API", response.toString());
                        products = parserJsonProducts.parserProducts(response);
                        addProductsDB(products);;
                        if (productsListener != null)
                            productsListener.onRefreshArrayProducts(products);
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
                return null;
            }
            else{
                products = localDatabase.getProducts();
                toastNotIntenet(context);
                return products;
            }
        }

        public ArrayList<Products> getProductsByCategory(final Context context, int categoryId){
            if(isConnectionInternet(context)) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "product/category/" + categoryId + "?"+token, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("API", response.toString());
                        productsbycategory = parserJsonProducts.parserProducts(response);
                        if (productsListener != null)
                            productsListener.onRefreshListProducts(productsbycategory);
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
                return null;
            }
            else{
                productsbycategory = getProductsbycategory(categoryId);
                toastNotIntenet(context);
                return productsbycategory;
            }
        }
        //endregion

        //region Database SECTION
        public Products getProduct(int id) {
            for (Products product : products)
                if (product.getId() == id)
                    return product;
            return null;
        }

        public ArrayList<Products> getProductsbycategory(int id) {
            products = localDatabase.getProducts();
            ArrayList<Products> productsbycat = new ArrayList<>();

            for(Products productcat : products){
                if(productcat.getCategory_id() == id){
                    productsbycat.add(productcat);
                }
            }
            return productsbycat;
        }

        public void addProductDB(Products product) {
            localDatabase.addProduct(product);
        }

        public void addProductsDB(ArrayList<Products> products) {
            localDatabase.deleteProducts();
            for (Products product : products)
                addProductDB(product);
        }

        public void removeProductDB(int id) {
            Products product = getProduct(id);

            if (product != null)
                if (localDatabase.deleteProduct(id))
                    products.remove(product);
        }

        public void updateProductDB(Products nproduct) {
            Products product = getProduct(nproduct.getId());

            if (product != null) {
                if (localDatabase.editProduct(nproduct)) {
                    product.setId(nproduct.getId());
                    product.setName(nproduct.getName());
                    product.setPrice(nproduct.getPrice());
                    product.setQuantity(nproduct.getQuantity());
                }
            }
        }
        //endregion
    // endregion

    //region PRODUCTOBEPAID SECTION
        //region API SECTION
        public ArrayList<ProductsToBePaid> getAllProductsToBePaid(final Context context){
            if(isConnectionInternet(context)) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "productstobepaid/all?"+token, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("API", response.toString());
                        productsToBePaid = parserJsonProducts.parserProductsToBePaid(response);
                        addProductsToBePaidDB(productsToBePaid);
                        if (productstobepaidListener != null)
                            productstobepaidListener.onRefreshArrayProducts(productsToBePaid);
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
                return null;
            }
            else{
                productsToBePaid = localDatabase.getProductsToBePaid();
                toastNotIntenet(context);
                return productsToBePaid;
            }
        }

        //endregion

        //region Database SECTION
        public ProductsToBePaid getProductToBePaid(int id) {
            for (ProductsToBePaid producttobepaid : productsToBePaid)
                if (producttobepaid.getId() == id)
                    return producttobepaid;
            return null;
        }

        public ArrayList<ProductsToBePaid> getProductsByRequestId(int idRequest) {
            productsToBePaid = localDatabase.getProductsToBePaid();
            ArrayList<ProductsToBePaid> productsToBePaidRequest = new ArrayList<>();

            for(ProductsToBePaid producttobepaid : productsToBePaid){
                if(producttobepaid.getRequest_id() == idRequest){
                    productsToBePaidRequest.add(producttobepaid);
                }
            }
            return productsToBePaidRequest;
        }

        public ArrayList<ProductsToBePaid> getProductsByAccountId(int idAccount) {
            productsToBePaid = localDatabase.getProductsToBePaid();
            ArrayList<ProductsToBePaid> productsToBePaidAccount = new ArrayList<>();

            for(ProductsToBePaid producttobepaid : productsToBePaid){
                if(producttobepaid.getAccount_id() == idAccount){
                    productsToBePaidAccount.add(producttobepaid);
                }
            }
            return productsToBePaidAccount;
        }

        public void addProductToBePaidDB(ProductsToBePaid producttobepaid) {
            localDatabase.addProductToBePaid(producttobepaid);
        }

        public void addProductsToBePaidDB(ArrayList<ProductsToBePaid> productstobepaid) {
            localDatabase.deleteProductsToBePaid();
            for (ProductsToBePaid producttobepaid : productstobepaid)
                addProductToBePaidDB(producttobepaid);
        }

        public void removeProductToBePaidDB(int id) {
            ProductsToBePaid producttobepaid = getProductToBePaid(id);

            if (producttobepaid != null)
                if (localDatabase.deleteProductToBePaid(id))
                    productsToBePaid.remove(producttobepaid);
        }

        public void updateProductToBePaidDB(ProductsToBePaid nproducttobepaid) {
            ProductsToBePaid producttobepaid = getProductToBePaid(nproducttobepaid.getId());

            if (producttobepaid != null) {
                if (localDatabase.editProductToBePaid(nproducttobepaid)) {
                    producttobepaid.setId(nproducttobepaid.getId());
                    producttobepaid.setName(nproducttobepaid.getName());
                    producttobepaid.setPrice(nproducttobepaid.getPrice());
                    producttobepaid.setQuantity(nproducttobepaid.getQuantity());
                }
            }
        }
        //endregion
    // endregion

    //region ACCOUNT SECTION
    public ArrayList<ProductsToBePaid> getAccountProducts(Context context, int accountId){
        if(isConnectionInternet(context)) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "account/info/" + accountId+"?"+token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.i("API", response.toString());
                    productstobepaidListener.onRefreshListProducts(parserJsonProducts.parserProductsToBePaidAccount(response));
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
            return null;
        }
        else{
            toastNotIntenet(context);
            return getProductsByAccountId(accountId);
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

        //region Bills
        public Bills getBill(int id) {
            for (Bills bill : bills)
                if (bill.getId() == id)
                    return bill;
            return null;
        }

        public ArrayList<Bills> getBillsDB() {
            bills = localDatabase.getBills();
            return bills;
        }

        public ArrayList<Bills> getBillsFromTable(int idTable) {
            bills = localDatabase.getBills();
            ArrayList<Bills> billsfromtable = new ArrayList<>();

            for(Bills bill : bills){
                if (bill.getIdMesa() == idTable)
                    billsfromtable.add(bill);
            }
            return billsfromtable;
        }

        public void addBillDB(Bills bill) {
            localDatabase.addBill(bill);
        }

        public void addBillsDB(ArrayList<Bills> bills) {
            localDatabase.deleteTables();
            for (Bills bill : bills)
                addBillDB(bill);
        }

        public void removeBillDB(int id) {
            Bills bill = getBill(id);

            if (bill != null)
                if (localDatabase.deleteBill(id))
                    tables.remove(bill);
        }

        public void updateBillDB(Bills nbill) {
            Bills bill = getBill(nbill.getId());

            if (bill != null) {
                if (localDatabase.editBill(nbill)) {
                    bill.setId(nbill.getId());
                    bill.setProductName(nbill.getProductName());
                    bill.setIdMesa(nbill.getIdMesa());
                    bill.setTotal(nbill.getTotal());
                }
            }
        }
        //endregion
    //endregion

    //region Internet SECTION
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
    //endregion
}
