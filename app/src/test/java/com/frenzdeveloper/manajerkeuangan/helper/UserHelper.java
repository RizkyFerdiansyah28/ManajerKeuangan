package com.frenzdeveloper.manajerkeuangan.helper;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class UserHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "frenz";

    public UserHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE \"Users\" (\n" +
                "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,\n" +
                "\t\"name\"\tTEXT DEFAULT 'John doe',\n" +
                "\t\"email\"\tTEXT,\n" +
                "\t\"password\"\tTEXT UNIQUE\n"+
                ");";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(sqLiteDatabase);
    }

    public ArrayList<HashMap<String, String>> getData(){
        // inisiasi objek list untuk menampung data
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        String query = "SELECT * FROM Users"; // query data
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("name", cursor.getString(1));
                map.put("email", cursor.getString(2));
                map.put("password", cursor.getString(3));
                list.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void insert(HashMap<String, String> data, Activity activity){


        SQLiteDatabase database = this.getWritableDatabase();

        // buat query string
        String query = "INSERT INTO Users (name,email,password)" +
                "VALUES ('"+ data.get("name") +"', '"+ data.get("email") +"', '"+ data.get("password") +"')";

        // Toast.makeText(activity, query, Toast.LENGTH_SHORT).show();
        exeqQuery(query, activity);
    }

    public void update(int id, HashMap<String, String> data, Activity activity){
        SQLiteDatabase database = this.getWritableDatabase();

        // buat query string
        String query = "UPDATE moneys" +
                "SET name = '"+ data.get("name") +"', email = '"+ data.get("email") +"', password = '"+ data.get("password") +"' WHERE id = "+id;

        exeqQuery(query, activity);
    }

    public void delete(int id, Activity activity){
        SQLiteDatabase database = this.getWritableDatabase();

        // buat query string
        String query = "DELETE FROM moneys WHERE id = " + id;
        exeqQuery(query, activity);
    }

    void exeqQuery(String query, Activity activity){
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            database.execSQL(query);
        }catch (Exception e){
            Toast.makeText(activity, e.getMessage()+"", Toast.LENGTH_SHORT).show();
        }
    }
}
