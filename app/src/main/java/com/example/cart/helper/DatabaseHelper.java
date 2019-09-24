package com.example.cart.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String database="cart.db";
    public static final String table_name="cart";
    public static final String id="id";
    public static final String name="name";
    public static final String price="price";
    public static final String quantity="quantity";
    public static final String total="total";

    public DatabaseHelper(@Nullable Context context) {
        super(context, database, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
db.execSQL("CREATE TABLE " + table_name + " (" + id +" INTEGER PRIMARY KEY, " + name + " varchar(50), " + price + " varchar(50), " + quantity + " varchar(50)," + total + " varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXISTS "+table_name);
onCreate(db);
    }

    public void insertData(String id,String name,String price,String quantity,String total){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO " + table_name + "(id,name,price,quantity,total)VALUES(?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,id);
        statement.bindString(2,name);
        statement.bindString(3,price);
        statement.bindString(4,quantity);
        statement.bindString(5,total);
        statement.executeInsert();
        statement.close();
        database.close();
    }

    public void updateData(String id,String quantity,String total){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE " + table_name + " SET quantity=?,total=? WHERE id=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,quantity);
        statement.bindString(2,total);
        statement.bindString(3,id);
        statement.executeInsert();
        statement.close();
        database.close();
    }

    public void deleteData(String id){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM " + table_name + " WHERE id=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,id);
        statement.execute();
        statement.close();
        database.close();
    }

    public void clearData(){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM " + table_name;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.execute();
        statement.close();
        database.close();
    }

    public Cursor getCartData(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+table_name,null);
        return cursor;
    }

    public int getProductQuantity(String id){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT quantity FROM " + table_name + " WHERE id="+id,null);
        int qty=0;
        if (cursor.moveToFirst()){
            qty=cursor.getInt(0);
            cursor.close();
        }
        return qty;
    }

    public Cursor checkProduct(String id){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+table_name+" WHERE id="+id,null);
        return cursor;
    }

    public Cursor cartQty(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + table_name,null);
        return cursor;
    }

    public int getTotal(){
        SQLiteDatabase  database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT sum(total) FROM "+table_name,null);
        int total=0;
        if (cursor.moveToFirst()){
            total=cursor.getInt(0);
            cursor.close();
        }
        return total;
    }




}
