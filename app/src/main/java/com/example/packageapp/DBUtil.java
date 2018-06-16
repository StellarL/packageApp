package com.example.packageapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 李馨 on 2018/6/13.
 */

public class DBUtil extends SQLiteOpenHelper{

    private SQLiteDatabase sqLiteDatabase;
    private String table;

    public DBUtil(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, String table) {
        super(context, name, factory, version);
        sqLiteDatabase = context.openOrCreateDatabase(name,Context.MODE_PRIVATE,null);
        this.table = table;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_sql = "";
        switch (table){
            case "user":
                create_sql = "create table "+ table +
                        "(_id integer primary key autoincrement, " +
                        "phone text" +
                        "password text," +
                        "relname text," +
                        "id_card text," +
                        "id_img text" +
                        "score integer)";
            case "order1":
                create_sql = "create table order1(" +
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
        }
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 下单事件
     * @param orderId 下单人id
     * @param orderName 下单人relname
     * @param orderPhone 下单人phone
     * @param startPlace  起点
     * @param endPlace 终点
     * @param payment 金额
     * @param type 类型
     * @param info 信息
     */
    public void insertOrder(int orderId,String orderName,String orderPhone,String startPlace,String endPlace,String payment,String type,String info) {
        String sql = "insert into order1(_id,order_id,order_name,order_phone,receive_id,receive_name,receive_phone,start_place,end_place,payment,type,status,finish,info,score)" +
                "values ( null, ? , ? ,?,null,null,null,?,?,?,?,0,?,?,0)";
        sqLiteDatabase.execSQL(sql,new Object[]{orderId,orderName,orderPhone,startPlace,endPlace,payment,type,"未完成",info});
    }

    //查询未接单的所有订单
    public ArrayList<Order> queryAllState0Order(){
        ArrayList<Order> arrayList ;
        String strSelect = "select * from order1 where status = 0";
        Cursor c = sqLiteDatabase.rawQuery(strSelect,null);
        arrayList = new ArrayList<Order>();
        while (c.moveToNext()) {
            //订单id
            int _id = c.getInt(c.getColumnIndex("_id"));
            //下单人id  即 用户名 电话号码
            int order_id = c.getInt(c.getColumnIndex("order_id"));
            //下单人name 即 收货人name
            String order_name = c.getString(c.getColumnIndex("order_name"));
            //下单人phone
            String order_phone = c.getString(c.getColumnIndex("order_phone"));
            //接单人id
            int receive_id = c.getInt(c.getColumnIndex("receive_id"));
            //接单人name
            String receive_name = c.getString(c.getColumnIndex("receive_name"));
            //接单人phone
            String receive_phone = c.getString(c.getColumnIndex("receive_phone"));
            //起点
            String start_place = c.getString(c.getColumnIndex("start_place"));
            //终点
            String end_place = c.getString(c.getColumnIndex("end_place"));
            //金额
            int payment = c.getInt(c.getColumnIndex("payment"));
            //包裹类型
            int type = c.getInt(c.getColumnIndex("type"));
            //是否接单
            int status = c.getInt(c.getColumnIndex("status"));
            //是否完成
            String finish = c.getString(c.getColumnIndex("finish"));
            //取货信息
            String info = c.getString(c.getColumnIndex("info"));
            //评分
            int score = c.getInt(c.getColumnIndex("score"));
            Order order = new Order(_id,order_id,order_name,order_phone,receive_id,receive_name,receive_phone,start_place,end_place,payment,type,status,finish,info,score);
            arrayList.add(order);
        }
        return arrayList;
    }

    //根据订单id 查询 Order
    public Order queryByIdOrder(int id) {
        String sql = "select * from order1 where _id = ? ";
        Cursor c = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(id)});

        Order order = new Order();
        while (c.moveToNext()) {
            //订单id
            int _id = c.getInt(c.getColumnIndex("_id"));
            //下单人id  即 用户名 电话号码
            int order_id = c.getInt(c.getColumnIndex("order_id"));
            //下单人name 即 收货人name
            String order_name = c.getString(c.getColumnIndex("order_name"));
            //下单人phone
            String order_phone = c.getString(c.getColumnIndex("order_phone"));
            //接单人id
            int receive_id = c.getInt(c.getColumnIndex("receive_id"));
            //接单人name
            String receive_name = c.getString(c.getColumnIndex("receive_name"));
            //接单人phone
            String receive_phone = c.getString(c.getColumnIndex("receive_phone"));
            //起点
            String start_place = c.getString(c.getColumnIndex("start_place"));
            //终点
            String end_place = c.getString(c.getColumnIndex("end_place"));
            //金额
            int payment = c.getInt(c.getColumnIndex("payment"));
            //包裹类型
            int type = c.getInt(c.getColumnIndex("type"));
            //是否接单
            int status = c.getInt(c.getColumnIndex("status"));
            //是否完成
            String finish = c.getString(c.getColumnIndex("finish"));
            //取货信息
            String info = c.getString(c.getColumnIndex("info"));
            //评分
            int score = c.getInt(c.getColumnIndex("score"));
            order = new Order(_id,order_id,order_name,order_phone,receive_id,receive_name,receive_phone,start_place,end_place,payment,type,status,finish,info,score);
        }
        return order;
    }

    /**
     * 用户点击接单 更新数据库 state = 1 receiveID receiveName receivePhone
     */
    public void updateStatusOrder(int id,String username,String name,String phone) {
        String sql = "update order1 set receive_id=?,receive_name=?,receive_phone=?,state=1 where _id=?";
        sqLiteDatabase.execSQL(sql,new String[]{username,name,phone,String.valueOf(id)});
        Log.e("updateStete", "updateStete: success" );
    }

    /**
     * 用户接单 更新User score+2
     * @param id
     */
    public void updateReceScoreUser(int id){
        //先获取用户的score
        String query_sql = "select score from user where _id=?";
        Cursor c = sqLiteDatabase.rawQuery(query_sql,new String[]{String.valueOf(id)});
        int score = 0;
        while (c.moveToNext()){
            score = c.getInt(c.getColumnIndex("score"));
        }
        //再set score = score+2
        String update_sql = "update user set score=? where _id=?";
        sqLiteDatabase.execSQL(update_sql,new Object[]{score+2,id});
        Log.e("updateReceScoreUser", "updateReceScoreUser: success" );
    }

    /**
     * 评价更新Order表中score
     * @param id 订单id
     * @param score 当前订单的评分
     */
    public void updateScoreOrder(int id,int score){
        String sql = "update order1 set score=? where _id =?";
        sqLiteDatabase.execSQL(sql,new Object[]{id,score});
        Log.e("updateScoreOrder", "updateScoreOrder: success" );
    }

    /**
     * 评价更新User表中的Score
     * @param id User表对应的id
     * @param newscore 当前订单的评分
     */
    public void updateScoreUser(int id,int newscore){
        //先获取当前user 对应的score
        String query_sql = "select score from user where _id=?";
        Cursor c = sqLiteDatabase.rawQuery(query_sql,new String[]{String.valueOf(id)});
        int score = 0;
        while (c.moveToNext()){
            score = c.getInt(c.getColumnIndex("score"));
        }
        //再在score上加上当前订单的score
        String sql = "update user set score=? where _id=?";
        sqLiteDatabase.execSQL(sql,new Object[]{score+newscore,id});
        Log.e("updateScoreUser", "updateScoreUser: success" );
    }


    /**
     * 查询我的下单
     * @param phone 用户的phone
     * @return 我的下单的ArrayList
     */
    public ArrayList<Order> selectMyOrder(String phone){
        ArrayList<Order> arrayList = new ArrayList<>();
        String sql = "select * from order1 where order_phone = ?";
        Cursor c = sqLiteDatabase.rawQuery(sql, new String[]{phone});

        while (c.moveToNext()) {
            //订单id
            int _id = c.getInt(c.getColumnIndex("_id"));
            //下单人id  即 用户名 电话号码
            int order_id = c.getInt(c.getColumnIndex("order_id"));
            //下单人name 即 收货人name
            String order_name = c.getString(c.getColumnIndex("order_name"));
            //下单人phone
            String order_phone = c.getString(c.getColumnIndex("order_phone"));
            //接单人id
            int receive_id = c.getInt(c.getColumnIndex("receive_id"));
            //接单人name
            String receive_name = c.getString(c.getColumnIndex("receive_name"));
            //接单人phone
            String receive_phone = c.getString(c.getColumnIndex("receive_phone"));
            //起点
            String start_place = c.getString(c.getColumnIndex("start_place"));
            //终点
            String end_place = c.getString(c.getColumnIndex("end_place"));
            //金额
            int payment = c.getInt(c.getColumnIndex("payment"));
            //包裹类型
            int type = c.getInt(c.getColumnIndex("type"));
            //是否接单
            int status = c.getInt(c.getColumnIndex("status"));
            //是否完成
            String finish = c.getString(c.getColumnIndex("finish"));
            //取货信息
            String info = c.getString(c.getColumnIndex("info"));
            //评分
            int score = c.getInt(c.getColumnIndex("score"));
            Order order = new Order(_id,order_id,order_name,order_phone,receive_id,receive_name,receive_phone,start_place,end_place,payment,type,status,finish,info,score);
            arrayList.add(order);
        }
        return arrayList;
    }

    /**
     * 查询我的接单
     * @param phone 用户手机号
     * @return
     */
    public ArrayList<Order> selectMyReceiveOrder(String phone) {
        ArrayList<Order> arrayList = new ArrayList<>();
        String sql = "select * from order1 where receive_phone = ?";
        Cursor c = sqLiteDatabase.rawQuery(sql, new String[]{phone});

        while (c.moveToNext()) {
            //订单id
            int _id = c.getInt(c.getColumnIndex("_id"));
            //下单人id  即 用户名 电话号码
            int order_id = c.getInt(c.getColumnIndex("order_id"));
            //下单人name 即 收货人name
            String order_name = c.getString(c.getColumnIndex("order_name"));
            //下单人phone
            String order_phone = c.getString(c.getColumnIndex("order_phone"));
            //接单人id
            int receive_id = c.getInt(c.getColumnIndex("receive_id"));
            //接单人name
            String receive_name = c.getString(c.getColumnIndex("receive_name"));
            //接单人phone
            String receive_phone = c.getString(c.getColumnIndex("receive_phone"));
            //起点
            String start_place = c.getString(c.getColumnIndex("start_place"));
            //终点
            String end_place = c.getString(c.getColumnIndex("end_place"));
            //金额
            int payment = c.getInt(c.getColumnIndex("payment"));
            //包裹类型
            int type = c.getInt(c.getColumnIndex("type"));
            //是否接单
            int status = c.getInt(c.getColumnIndex("status"));
            //是否完成
            String finish = c.getString(c.getColumnIndex("finish"));
            //取货信息
            String info = c.getString(c.getColumnIndex("info"));
            //评分
            int score = c.getInt(c.getColumnIndex("score"));
            Order order = new Order(_id,order_id,order_name,order_phone,receive_id,receive_name,receive_phone,start_place,end_place,payment,type,status,finish,info,score);
            arrayList.add(order);
        }
        return arrayList;
    }

    /**
     * 更新订单已完成
     * @param id 订单id
     */
    public void updateFinish(int id){
        String sql = "update order1 set finish =? where _id =?";
        sqLiteDatabase.execSQL(sql,new String[]{"已完成",String.valueOf(id)});
        Log.e("updateFinish", "updateFinish: success" );
    }


}
