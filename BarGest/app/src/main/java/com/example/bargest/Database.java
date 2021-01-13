package com.example.bargest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.bargest.Models.Tables;

public class Database extends SQLiteOpenHelper {

    private static final String NAME = "bargest.db";
    private static final int VERSION= 1;
    private final SQLiteDatabase db;

    //Tables
    private static final String TABLE_TABLE = "tables";
    private static final String COLUMN_TABLE_ID = "id";
    private static final String COLUMN_TABLE_NUMBER = "number";
    private static final String COLUMN_TABLE_STATUS = "status";

    //Accounts
    private static final String TABLE_ACCOUNT = "accounts";
    private static final String COLUMN_ACCOUNT_ID = "id";
    private static final String COLUMN_ACCOUNT_NAME = "name";
    private static final String COLUMN_ACCOUNT_STATUS = "status";
    private static final String COLUMN_ACCOUNT_TOTAL = "total";
    private static final String COLUMN_ACCOUNT_TABLE_ID = "table_id";
    //Requests
    private static final String TABLE_REQUEST = "requests";
    private static final String COLUMN_REQUEST_ID = "id";
    private static final String COLUMN_REQUEST_DATETIME = "dateTime";
    private static final String COLUMN_REQUEST_STATUS = "status";
    private static final String COLUMN_REQUEST_ACCOUNT_ID = "account_id";
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
    private static final String COLUMN_PRODUCT_CATEGORY_ID = "category_id";
    //Categories
    private static final String TABLE_CATEGORY = "products";
    private static final String COLUMN_CATEGORY_ID = "id";
    private static final String COLUMN_CATEGORY_NAME = "name";

    public Database(@Nullable Context context) {
        super(context, NAME, null, VERSION);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE " + TABLE_TABLE + "( "+
                        COLUMN_TABLE_NUMBER + " INTEGER PRIMARY KEY, "+
                        COLUMN_TABLE_STATUS + " INTEGER NOT NULL "+
                        " )";
        String CREATE_ACCOUNT=
                "CREATE TABLE "+ TABLE_ACCOUNT + "( "+
                        COLUMN_ACCOUNT_ID + " INTEGER PRIMARY KEY, "+
                        COLUMN_ACCOUNT_NAME + " TEXT NOT NULL, "+
                        COLUMN_ACCOUNT_STATUS + " INTEGER NOT NULL, "+
                        COLUMN_ACCOUNT_TOTAL + " DECIMAL(10, 2) NOT NULL, "+
                        COLUMN_ACCOUNT_TABLE_ID + " INTEGER NOT NULL "+
                " )";

        String CREATE_REQUEST =
                "CREATE TABLE "+ TABLE_REQUEST + "(" +
                        COLUMN_REQUEST_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_REQUEST_DATETIME + " TEXT NOT NULL, " +
                        COLUMN_REQUEST_STATUS + " INTEGER NOT NULL, " +
                        COLUMN_REQUEST_ACCOUNT_ID + " INTEGER NOT NULL, " +
                        COLUMN_REQUEST_TABLE_ID + " INTEGER NOT NULL " +
                        " ) ";

        String CREATE_PRODUCT_REQUEST =
                "CREATE TABLE "+ TABLE_REQUESTPRODUCT + "(" +
                        COLUMN_REQUESTPRODUCT_REQUEST_ID + " INTEGER NOT NULL, " +
                        COLUMN_REQUESTPRODUCT_PRODUCT_ID + " INTEGER NOT NULL, " +
                        COLUMN_REQUESTPRODUCT_QUANTITY + " INTEGER NOT NULL "+
                        " ) ";

        String CREATE_PRODUCT =
                "CREATE TABLE "+ TABLE_PRODUCT + " ( " +
                        COLUMN_PRODUCT_ID + " INTEGER NOT NULL," +
                        COLUMN_PRODUCT_NAME + " TEXT NOT NULL," +
                        COLUMN_PRODUCT_PRICE + " decimal(10,2) NOT NULL, " +
                        COLUMN_PRODUCT_CATEGORY_ID +" INTEGER NOT NULL" +
                ")";
        String CREATE_CATEGORY =
                "CREATE TABLE "+ TABLE_CATEGORY + " ( " +
                        COLUMN_CATEGORY_NAME + " TEXT NOT NULL," +
                        COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY " +
                        ")";

        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_ACCOUNT);
        db.execSQL(CREATE_REQUEST);
        db.execSQL(CREATE_PRODUCT_REQUEST);
        db.execSQL(CREATE_PRODUCT);
        db.execSQL(CREATE_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_REQUEST);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_REQUESTPRODUCT);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CATEGORY);
        onCreate(db);
    }


    //Tables
    public Tables addMesaBD(Tables table){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TABLE_ID, table.getId());
        values.put(COLUMN_TABLE_NUMBER, table.getNumber());
        values.put(COLUMN_TABLE_STATUS, table.getStatus());

        long id = this.db.insert(TABLE_TABLE, null, values);
        if(id > -1){
            table.setId( (int) id );
            return table;
        }
        return null;
    }

    //UPDATE
    public boolean editMesaBD(Tables table){

        ContentValues values = new ContentValues();
        values.put(COLUMN_TABLE_ID, table.getId());
        values.put(COLUMN_TABLE_NUMBER, table.getNumber());
        values.put(COLUMN_TABLE_STATUS, table.getStatus());

        return this.db.update(TABLE_TABLE, values, "id = ?", new String[]{table.getId()+""}) > 0;
    }
    //DELETE
    public boolean deleteTableBD(int idMesa){
        return db.delete(TABLE_TABLE, "id =?", new String[]{idMesa+""}) > 0;
    }
}
