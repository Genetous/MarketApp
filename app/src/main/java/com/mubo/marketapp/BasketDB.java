package com.mubo.marketapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mubo.marketapp.productList.productDataBasket;

import java.util.ArrayList;
import java.util.List;

public class BasketDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "basket.db";
    String table="basket";
    Context cx;
    public BasketDB(@Nullable Context context) {
        super(context,  DATABASE_NAME, null, DATABASE_VERSION);
        cx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                table+
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "category VARCHAR," +
                "subCategory VARCHAR," +
                "productName VARCHAR," +
                "product_price VARCHAR," +
                "product_description VARCHAR," +
                "product_image VARCHAR," +
                "product_id VARCHAR," +
                "count INTEGER);");
    }
    public void productEkle(productDataBasket p){
        String don="ok";
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("category", p.getCategory());
            values.put("subCategory", p.getSubCategory());
            values.put("productName", p.getProduct_name());
            values.put("product_price", p.getProduct_price());
            values.put("product_description", p.getProduct_description());
            values.put("product_image", p.getProduct_image());
            values.put("product_id", p.getId());
            values.put("count", p.getCount());
            db.insert(table, null, values);
            db.close();

        }catch (Exception ex){
            don="hata";
        }
    }
    public List<productDataBasket> sepetGetir(){
        List<productDataBasket> basket=new ArrayList<>();
        String order="ID DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        String[] veri_columns={"ID","category","subCategory","productName","product_price",
                        "product_description","product_image","product_id","count"};
        Cursor cursor = db.query(table, veri_columns,null , null, null, null, order);
        while (cursor.moveToNext()) {
            productDataBasket pdb=new productDataBasket();
            pdb.setID(cursor.getInt(0));
            pdb.setCategory(cursor.getString(1));
            pdb.setSubCategory(cursor.getString(2));
            pdb.setProduct_name(cursor.getString(3));
            pdb.setProduct_price(cursor.getString(4));
            pdb.setProduct_description(cursor.getString(5));
            pdb.setProduct_image(cursor.getString(6));
            pdb.setId(cursor.getString(7));
            pdb.setCount(cursor.getInt(8));
            basket.add(pdb);
        }
        return basket;
    }
    public long delete(String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table,"ID=?",new String[]{ID});
    }
    public long updateCount(String id,int count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("count",count);
        return db.update(table, contentValues,"ID=?",new String[] {id});
    }
    public int contains(String product_id){
        int ID=-1;
        SQLiteDatabase db = this.getWritableDatabase();
        String operation="product_id=?";
        String[] where={product_id};
        Cursor cursor = db.query(table, new String[]{"ID"},operation , where, null, null, null);
        while (cursor.moveToNext()) {
            ID=cursor.getInt(0);
            break;
        }
        return ID;
    }
    public productDataBasket urunGetir(String product_id){
        String operation="product_id=?";
        String[] where={product_id};
        productDataBasket pdb=new productDataBasket();
        String order="ID DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        String[] veri_columns={"ID","category","subCategory","productName","product_price",
                "product_description","product_image","product_id","count"};
        Cursor cursor = db.query(table, veri_columns,operation , where, null, null, order);
        while (cursor.moveToNext()) {
            pdb.setID(cursor.getInt(0));
            pdb.setCategory(cursor.getString(1));
            pdb.setSubCategory(cursor.getString(2));
            pdb.setProduct_name(cursor.getString(3));
            pdb.setProduct_price(cursor.getString(4));
            pdb.setProduct_description(cursor.getString(5));
            pdb.setProduct_image(cursor.getString(6));
            pdb.setId(cursor.getString(7));
            pdb.setCount(cursor.getInt(8));
            break;
        }
        return pdb;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
