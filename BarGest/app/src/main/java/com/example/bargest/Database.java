package com.example.bargest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Categories;
import com.example.bargest.Models.Products;
import com.example.bargest.Models.ProductsToBePaid;
import com.example.bargest.Models.Requests;
import com.example.bargest.Models.Tables;
import com.example.bargest.Models.views.ListRequests;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static final String NAME = "bargest.db";
    private static final int VERSION= 1;
    private final SQLiteDatabase db;

    //Tables
    private static final String TABLE_TABLE = "tables";
    private static final String COLUMN_TABLE_ID = "id";
    private static final String COLUMN_TABLE_NUMBER = "number";
    private static final String COLUMN_TABLE_STATUS = "status";
    private static final String COLUMN_TABLE_TOTAL = "total";

    //Accounts
    private static final String TABLE_ACCOUNT = "accounts";
    private static final String COLUMN_ACCOUNT_ID = "id";
    private static final String COLUMN_ACCOUNT_NAME = "name";
    private static final String COLUMN_ACCOUNT_TOTAL = "total";
    private static final String COLUMN_ACCOUNT_TABLE_ID = "table_id";
    //Requests
    private static final String TABLE_REQUEST = "requests";
    private static final String COLUMN_REQUEST_ID = "id";
    private static final String COLUMN_REQUEST_DATETIME = "dateTime";
    private static final String COLUMN_REQUEST_STATUS = "status";
    private static final String COLUMN_REQUEST_TABLE_ID = "table_id";
    //Requests_Products
    private static final String TABLE_REQUESTPRODUCT = "requests_product";
    private static final String COLUMN_REQUESTPRODUCT_REQUEST_ID = "request_id";
    private static final String COLUMN_REQUESTPRODUCT_PRODUCT_ID = "product_id";
    private static final String COLUMN_REQUESTPRODUCT_QUANTITY = "quantity";
    //Products
    private static final String TABLE_PRODUCT = "products";
    private static final String COLUMN_PRODUCT_ID = "id";
    private static final String COLUMN_PRODUCT_NAME = "name";
    private static final String COLUMN_PRODUCT_PRICE = "price";
    private static final String COLUMN_PRODUCT_QUANTITY = "quantity";
    private static final String COLUMN_PRODUCT_CATEGORY_ID = "category_id";
    //ProductsToBePaid
    private static final String TABLE_PRODUCTTOBEPAID = "productstobepaid";
    private static final String COLUMN_PRODUCTTOBEPAID_ID = "id";
    private static final String COLUMN_PRODUCTTOBEPAID_NAME = "name";
    private static final String COLUMN_PRODUCTTOBEPAID_PRICE = "price";
    private static final String COLUMN_PRODUCTTOBEPAID_QUANTITY = "quantity";
    private static final String COLUMN_PRODUCTTOBEPAID_ACCOUNT_ID = "account_id";
    private static final String COLUMN_PRODUCTTOBEPAID_REQUEST_ID = "request_id";
    //Categories
    private static final String TABLE_CATEGORY = "categories";
    private static final String COLUMN_CATEGORY_ID = "id";
    private static final String COLUMN_CATEGORY_NAME = "name";

    public Database(@Nullable Context context) {
        super(context, NAME, null, VERSION);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_TABLE + "( "+
                        COLUMN_TABLE_ID + " INTEGER PRIMARY KEY, "+
                        COLUMN_TABLE_NUMBER + " INTEGER NOT NULL, "+
                        COLUMN_TABLE_STATUS + " INTEGER NOT NULL, "+
                        COLUMN_TABLE_TOTAL + " INTEGER NOT NULL "+
                        " )";
        String CREATE_ACCOUNT=
                "CREATE TABLE IF NOT EXISTS "+ TABLE_ACCOUNT + "( "+
                        COLUMN_ACCOUNT_ID + " INTEGER PRIMARY KEY, "+
                        COLUMN_ACCOUNT_NAME + " TEXT NOT NULL, "+
                        COLUMN_ACCOUNT_TOTAL + " DECIMAL(10, 2) NOT NULL, "+
                        COLUMN_ACCOUNT_TABLE_ID + " INTEGER NOT NULL "+
                " )";

        String CREATE_REQUEST =
                "CREATE TABLE IF NOT EXISTS "+ TABLE_REQUEST + "(" +
                        COLUMN_REQUEST_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_REQUEST_DATETIME + " TEXT NOT NULL, " +
                        COLUMN_REQUEST_STATUS + " INTEGER NOT NULL, " +
                        COLUMN_REQUEST_TABLE_ID + " INTEGER NOT NULL " +
                        " ) ";

        String CREATE_PRODUCT_REQUEST =
                "CREATE TABLE IF NOT EXISTS "+ TABLE_REQUESTPRODUCT + "(" +
                        COLUMN_REQUESTPRODUCT_REQUEST_ID + " INTEGER NOT NULL, " +
                        COLUMN_REQUESTPRODUCT_PRODUCT_ID + " INTEGER NOT NULL, " +
                        COLUMN_REQUESTPRODUCT_QUANTITY + " INTEGER NOT NULL "+
                        " ) ";

        String CREATE_PRODUCTTOBEPAID =
                "CREATE TABLE IF NOT EXISTS "+ TABLE_PRODUCTTOBEPAID + "(" +
                        COLUMN_PRODUCTTOBEPAID_ID + " INTEGER NOT NULL, " +
                        COLUMN_PRODUCTTOBEPAID_NAME + " INTEGER NOT NULL, " +
                        COLUMN_PRODUCTTOBEPAID_PRICE + " INTEGER NOT NULL, "+
                        COLUMN_PRODUCTTOBEPAID_QUANTITY + " INTEGER NOT NULL, "+
                        COLUMN_PRODUCTTOBEPAID_ACCOUNT_ID + " INTEGER NOT NULL, "+
                        COLUMN_PRODUCTTOBEPAID_REQUEST_ID + " INTEGER NOT NULL "+
                        " ) ";

        String CREATE_PRODUCT =
                "CREATE TABLE IF NOT EXISTS "+ TABLE_PRODUCT + " ( " +
                        COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY," +
                        COLUMN_PRODUCT_NAME + " TEXT NOT NULL," +
                        COLUMN_PRODUCT_PRICE + " decimal(10,2) NOT NULL, " +
                        COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL, " +
                        COLUMN_PRODUCT_CATEGORY_ID +" INTEGER NOT NULL" +
                ")";
        String CREATE_CATEGORY =
                "CREATE TABLE IF NOT EXISTS "+ TABLE_CATEGORY + " ( " +
                        COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY," +
                        COLUMN_CATEGORY_NAME + " TEXT NOT NULL" +
                        ")";

        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_ACCOUNT);
        db.execSQL(CREATE_REQUEST);
        db.execSQL(CREATE_PRODUCT_REQUEST);
        db.execSQL(CREATE_PRODUCTTOBEPAID);
        db.execSQL(CREATE_PRODUCT);
        db.execSQL(CREATE_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_REQUEST);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_REQUESTPRODUCT);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PRODUCTTOBEPAID);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CATEGORY);
        onCreate(db);
    }

    //region Tables

    /**
     * SELECT
     * @return
     */
    public ArrayList<Tables> getTables(){
        ArrayList<Tables> tables = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_TABLE, new String[]{COLUMN_TABLE_ID, COLUMN_TABLE_NUMBER, COLUMN_TABLE_STATUS, COLUMN_TABLE_TOTAL}, null, null, null,null, null);

        if(cursor.moveToFirst()){
            do{
                Tables table = new Tables(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getDouble(3));
                tables.add(table);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return tables;
    }

    /**
     * INSERT
     * @param table
     * @return
     */
    public void addTable(Tables table){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TABLE_ID, table.getId());
        values.put(COLUMN_TABLE_NUMBER, table.getNumber());
        values.put(COLUMN_TABLE_STATUS, table.getStatus());
        values.put(COLUMN_TABLE_TOTAL, table.getTotal());

        this.db.insert(TABLE_TABLE, null, values);
    }

    /**
     * UPDATE
     * @param table
     * @return
     */
    public boolean editTable(Tables table){

        ContentValues values = new ContentValues();
        values.put(COLUMN_TABLE_ID, table.getId());
        values.put(COLUMN_TABLE_NUMBER, table.getNumber());
        values.put(COLUMN_TABLE_STATUS, table.getStatus());
        values.put(COLUMN_TABLE_TOTAL, table.getStatus());

        int nRows = this.db.update(TABLE_TABLE, values, "id = ?", new String[]{table.getId()+""});

        return (nRows>0);
    }

    /**
     * DELETE
     * @param idMesa
     * @return
     */
    public boolean deleteTable(int idMesa){
        int nRows = this.db.delete(TABLE_TABLE,"id = ?", new String[]{idMesa+""});
        return (nRows>0);
    }

    public boolean deleteTables() {
        return db.delete(TABLE_TABLE, null, null) > 0;
    }
    //endregion

    //region Bills

    /**
     * SELECT
     * @return
     */
    public ArrayList<Bills> getBills(){
        ArrayList<Bills> bills = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_ACCOUNT, new String[]{COLUMN_ACCOUNT_ID, COLUMN_ACCOUNT_NAME, COLUMN_ACCOUNT_TOTAL, COLUMN_ACCOUNT_TABLE_ID}, null, null, null,null, null);

        if(cursor.moveToFirst()){
            do{
                Bills bill = new Bills(cursor.getString(1), cursor.getFloat(2), cursor.getInt(0), cursor.getInt(3));
                bills.add(bill);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return bills;
    }

    /**
     * INSERT
     * @param bill
     * @return
     */
    public void addBill(Bills bill){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACCOUNT_ID, bill.getId());
        values.put(COLUMN_ACCOUNT_NAME, bill.getProductName());
        values.put(COLUMN_ACCOUNT_TOTAL, bill.getTotal());
        values.put(COLUMN_ACCOUNT_TABLE_ID, bill.getIdMesa());

        this.db.insert(TABLE_ACCOUNT, null, values);
    }

    /**
     * UPDATE
     * @param bill
     * @return
     */
    public boolean editBill(Bills bill){

        ContentValues values = new ContentValues();
        values.put(COLUMN_ACCOUNT_ID, bill.getId());
        values.put(COLUMN_ACCOUNT_NAME, bill.getProductName());
        values.put(COLUMN_ACCOUNT_TOTAL, bill.getTotal());
        values.put(COLUMN_ACCOUNT_TABLE_ID, bill.getIdMesa());

        int nRows = this.db.update(TABLE_ACCOUNT, values, "id = ?", new String[]{bill.getId()+""});

        return (nRows>0);
    }

    /**
     * DELETE
     * @param idBill
     * @return
     */
    public boolean deleteBill(int idBill){
        int nRows = this.db.delete(TABLE_ACCOUNT,"id = ?", new String[]{idBill+""});
        return (nRows>0);
    }

    public boolean deleteBills() {
        return db.delete(TABLE_ACCOUNT, null, null) > 0;
    }
    //endregion

    //region Requests

    /**
     * SELECT
     * @return
     */
    public ArrayList<ListRequests> getRequests(){
        ArrayList<ListRequests> requests = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_REQUEST, new String[]{COLUMN_REQUEST_ID, COLUMN_REQUEST_STATUS, COLUMN_REQUEST_TABLE_ID, COLUMN_REQUEST_DATETIME}, null, null, null,null, null);

        if(cursor.moveToFirst()){
            do{
                ListRequests request = new ListRequests(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),  cursor.getString(3));
                requests.add(request);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return requests;
    }

    /**
     * INSERT
     * @param request
     * @return
     */
    public void addRequests(ListRequests request){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACCOUNT_ID, request.getId());
        values.put(COLUMN_REQUEST_STATUS, request.getStatus());
        values.put(COLUMN_REQUEST_TABLE_ID, request.getTable_number());
        values.put(COLUMN_REQUEST_DATETIME, request.getDateTime());

        this.db.insert(TABLE_REQUEST, null, values);
    }

    /**
     * UPDATE
     * @param request
     * @return
     */
    public boolean editRequest(ListRequests request){

        ContentValues values = new ContentValues();
        values.put(COLUMN_ACCOUNT_ID, request.getId());
        values.put(COLUMN_REQUEST_STATUS, request.getStatus());
        values.put(COLUMN_REQUEST_TABLE_ID, request.getTable_number());
        values.put(COLUMN_REQUEST_DATETIME, request.getDateTime());

        int nRows = this.db.update(TABLE_REQUEST, values, "id = ?", new String[]{request.getId()+""});

        return (nRows>0);
    }

    /**
     * DELETE
     * @param idRequest
     * @return
     */
    public boolean deleteRequest(int idRequest){
        int nRows = this.db.delete(TABLE_REQUEST,"id = ?", new String[]{idRequest+""});
        return (nRows>0);
    }

    public boolean deleteRequests() {
        return db.delete(TABLE_REQUEST, null, null) > 0;
    }
    //endregion

    //region Category

    /**
     * SELECT
     * @return
     */
    public ArrayList<Categories> getCategories(){
        ArrayList<Categories> categories = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_CATEGORY, new String[]{COLUMN_CATEGORY_ID, COLUMN_CATEGORY_NAME}, null, null, null,null, null);

        if(cursor.moveToFirst()){
            do{
                Categories category = new Categories(cursor.getInt(0), cursor.getString(1));
                categories.add(category);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return categories;
    }

    /**
     * INSERT
     * @param category
     * @return
     */
    public void addCategory(Categories category){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_ID, category.getId());
        values.put(COLUMN_CATEGORY_NAME, category.getName());

        this.db.insert(TABLE_CATEGORY, null, values);
    }

    /**
     * UPDATE
     * @param category
     * @return
     */
    public boolean editCategory(Categories category){

        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_ID, category.getId());
        values.put(COLUMN_CATEGORY_NAME, category.getName());

        int nRows = this.db.update(TABLE_CATEGORY, values, "id = ?", new String[]{category.getId()+""});

        return (nRows>0);
    }

    /**
     * DELETE
     * @param idCategoria
     * @return
     */
    public boolean deleteCategory(int idCategoria){
        int nRows = this.db.delete(TABLE_CATEGORY,"id = ?", new String[]{idCategoria+""});
        return (nRows>0);
    }

    public boolean deleteCategories() {
        return db.delete(TABLE_CATEGORY, null, null) > 0;
    }
    //endregion

    //region Products

    /**
     * SELECT
     * @return
     */
    public ArrayList<Products> getProducts(){
        ArrayList<Products> products = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_PRODUCT, new String[]{COLUMN_PRODUCT_ID, COLUMN_PRODUCT_NAME, COLUMN_PRODUCT_PRICE, COLUMN_PRODUCT_QUANTITY, COLUMN_PRODUCT_CATEGORY_ID}, null, null, null,null, null);

        if(cursor.moveToFirst()){
            do{
                Products product = new Products(cursor.getInt(0), cursor.getString(1), cursor.getFloat(2), cursor.getInt(3), cursor.getInt(4));
                products.add(product);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return products;
    }

    /**
     * INSERT
     * @param product
     * @return
     */
    public void addProduct(Products product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID, product.getId());
        values.put(COLUMN_PRODUCT_NAME, product.getName());
        values.put(COLUMN_PRODUCT_PRICE, product.getPrice());
        values.put(COLUMN_PRODUCT_QUANTITY, product.getQuantity());
        values.put(COLUMN_PRODUCT_CATEGORY_ID, product.getCategory_id());

        this.db.insert(TABLE_PRODUCT, null, values);
    }

    /**
     * UPDATE
     * @param product
     * @return
     */
    public boolean editProduct(Products product){

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID, product.getId());
        values.put(COLUMN_PRODUCT_NAME, product.getName());
        values.put(COLUMN_PRODUCT_PRICE, product.getPrice());
        values.put(COLUMN_PRODUCT_QUANTITY, product.getQuantity());
        values.put(COLUMN_PRODUCT_CATEGORY_ID, product.getCategory_id());

        int nRows = this.db.update(TABLE_PRODUCT, values, "id = ?", new String[]{product.getId()+""});

        return (nRows>0);
    }

    /**
     * DELETE
     * @param idProduct
     * @return
     */
    public boolean deleteProduct(int idProduct){
        int nRows = this.db.delete(TABLE_PRODUCT,"id = ?", new String[]{idProduct+""});
        return (nRows>0);
    }

    public boolean deleteProducts() {
        return db.delete(TABLE_PRODUCT, null, null) > 0;
    }
    //endregion

    //region ProductsToBePaid

    /**
     * SELECT
     * @return
     */
    public ArrayList<ProductsToBePaid> getProductsToBePaid(){
        ArrayList<ProductsToBePaid> productstobepaid = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_PRODUCTTOBEPAID, new String[]{COLUMN_PRODUCTTOBEPAID_ID, COLUMN_PRODUCTTOBEPAID_NAME, COLUMN_PRODUCTTOBEPAID_PRICE, COLUMN_PRODUCTTOBEPAID_QUANTITY, COLUMN_PRODUCTTOBEPAID_ACCOUNT_ID, COLUMN_PRODUCTTOBEPAID_REQUEST_ID}, null, null, null,null, null);

        if(cursor.moveToFirst()){
            do{
                ProductsToBePaid producttobepaid = new ProductsToBePaid(cursor.getInt(0), cursor.getString(1), cursor.getFloat(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), 0);
                productstobepaid.add(producttobepaid);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return productstobepaid;
    }

    /**
     * INSERT
     * @param producttobepaid
     * @return
     */
    public void addProductToBePaid(ProductsToBePaid producttobepaid){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTTOBEPAID_ID, producttobepaid.getId());
        values.put(COLUMN_PRODUCTTOBEPAID_NAME, producttobepaid.getName());
        values.put(COLUMN_PRODUCTTOBEPAID_PRICE, producttobepaid.getPrice());
        values.put(COLUMN_PRODUCTTOBEPAID_QUANTITY, producttobepaid.getQuantity());
        values.put(COLUMN_PRODUCTTOBEPAID_ACCOUNT_ID, producttobepaid.getAccount_id());
        values.put(COLUMN_PRODUCTTOBEPAID_REQUEST_ID, producttobepaid.getRequest_id());

        this.db.insert(TABLE_PRODUCTTOBEPAID, null, values);
    }

    /**
     * UPDATE
     * @param producttobepaid
     * @return
     */
    public boolean editProductToBePaid(ProductsToBePaid producttobepaid){

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTTOBEPAID_ID, producttobepaid.getId());
        values.put(COLUMN_PRODUCTTOBEPAID_NAME, producttobepaid.getName());
        values.put(COLUMN_PRODUCTTOBEPAID_PRICE, producttobepaid.getPrice());
        values.put(COLUMN_PRODUCTTOBEPAID_QUANTITY, producttobepaid.getQuantity());
        values.put(COLUMN_PRODUCTTOBEPAID_ACCOUNT_ID, producttobepaid.getAccount_id());
        values.put(COLUMN_PRODUCTTOBEPAID_REQUEST_ID, producttobepaid.getRequest_id());

        int nRows = this.db.update(TABLE_PRODUCTTOBEPAID, values, "id = ?", new String[]{producttobepaid.getId()+""});

        return (nRows>0);
    }

    /**
     * DELETE
     * @param idProducttobepaid
     * @return
     */
    public boolean deleteProductToBePaid(int idProducttobepaid){
        int nRows = this.db.delete(TABLE_PRODUCTTOBEPAID,"id = ?", new String[]{idProducttobepaid+""});
        return (nRows>0);
    }

    public boolean deleteProductsToBePaid() {
        return db.delete(TABLE_PRODUCTTOBEPAID, null, null) > 0;
    }
    //endregion

}
