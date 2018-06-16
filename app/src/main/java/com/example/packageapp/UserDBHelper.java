package com.example.packageapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * UserDBHelper
 * 创建User表
 */

public class UserDBHelper extends SQLiteOpenHelper{


    public UserDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists user1");
        String create_sql = "create table user1(" +
                "_id integer primary key autoincrement, " +
                "phone text," +
                "password text," +
                "relname text," +
                "id_card text," +
                "id_img text," +
                "score integer)";
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
