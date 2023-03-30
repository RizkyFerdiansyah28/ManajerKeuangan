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

public class ReportHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "frenz";

    public ReportHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE \"Reports\" (\n" +
                "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,\n" +
                "\t\"user_id\"\tINTEGER,\n" +
                "\t\"category_id\"\tINTEGER,\n" +
                "\t\"balance\"\tREAL,\n" +
                "\t\"income\"\tREAL,\n" +
                "\t\"outcome\"\tREAL,\n" +
                "\t\"note\"\tTEXT,\n" +
                "\t\"date\"\tTEXT,\n" +
                "\tFOREIGN KEY(\"user_id\") REFERENCES Users(id),\n" +
                "\tFOREIGN KEY(\"category_id\") REFERENCES Categories(id)\n" +
                ");";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Reports");
        onCreate(sqLiteDatabase);
    }

    public ArrayList<HashMap<String, String>> getData(){
        // inisiasi objek list untuk menampung data
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        String query = "SELECT * FROM Reports"; // query data
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("user_id", cursor.getString(1));
                map.put("category_id", cursor.getString(2));
                map.put("balance", cursor.getString(3));
                map.put("income", cursor.getString(4));
                map.put("outcome", cursor.getString(5));
                map.put("date", cursor.getString(6));
                list.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void insert(HashMap<String, String> data, Activity activity){
        // buat query string
        String query = "INSERT INTO Reports (user_id,category_id,balance,income,outcome,date)" +
                "VALUES ("+ data.get("user_id") +", "+ data.get("category_id") +", "+ data.get("balance") +", "+ data.get("income") +", "+ data.get("outcome") +", "+ data.get("date") +")";

        // Toast.makeText(activity, query, Toast.LENGTH_SHORT).show();
        exeqQuery(query, activity);
    }

    public void update(int id, HashMap<String, String> data, Activity activity){

        // buat query string
        String query = "UPDATE moneys" +
                "SET user_id = "+ data.get("user_id") +", category_id = "+ data.get("category_id") +"', balance = "+ data.get("balance") +", income = "+ data.get("income") +", outcome = " + data.get("outcome") + ", date = " + data.get("date") + " WHERE id = "+id;

        exeqQuery(query, activity);
    }

    public void delete(int id, Activity activity){
        // buat query string
        String query = "DELETE FROM Reports WHERE id = " + id;
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
