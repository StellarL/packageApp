package com.example.packageapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * OrderDBHelper:
 * 创建order表
 */

public class OrderDBHelper extends SQLiteOpenHelper {

    public OrderDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists order1");
        String create_sql = "create table order1(" +
                "_id integer primary key autoincrement, " +
                "order_id integer," + //存User _id
                "order_name text," + // User relname
                "order_phone text," + //User phone
                "receive_id integer," +
                "receive_name text," +
                "receive_phone text," +
                "start_place text," +
                "end_place text ," +
                "payment integer," +
                "type integer," +
                "status integer," +
                "finish text," +
                "info text," +
                "score integer)";
        Log.e("DBHelper", "onCreate: " + create_sql);
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
