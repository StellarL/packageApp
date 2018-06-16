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

public class DBUtil {

    private SQLiteDatabase sqLiteDatabase;

    public DBUtil(Context context, String dbname) {
        sqLiteDatabase = context.openOrCreateDatabase(dbname,Context.MODE_PRIVATE,null);
    }


    public void initUser(){
        String insert_sql = "insert into user1(_id,phone,password,relname,id_card,id_img,score)" +
                " values(null,?,?,null,null,null,0)";
        sqLiteDatabase.execSQL(insert_sql,new String[]{"13666666666","123456"});
    }

    public void insertInitOrder(){
//        insertOrder(2,"李馨","13555555555","D5","A6",5,1,"谢谢");
//        insertOrder(2,"lxini","13666666666","D5","D6",5,1,"谢谢");
//        insertOrder(2,"dengyan","13555555555","D5","A7",5,1,"谢谢");
//        insertOrder(1,"李欣","13666666666","C3","A7",4,1,"谢谢");
//        updateStatusOrder(5,2,"dengyan","13555555555");
//        insertOrder(1,"dengyan","13555555555","A3","D5",4,1,"谢谢");
//        updateStatusOrder(6,2,"lixin","13666666666");
//        insertOrder(2,"哈哈哈哈","13555555555","A7","A8",3,2,"谢谢");
//        updateStatusOrder(1,1,"aa","13666666666");
//        updateStatusOrder(3,1,"aa","13666666666");
        queryAllOrder();
        Log.e("insertInitOrder", "insertInitOrder: insertSuccess" );
    }


    /**
     * 注册用户
     * @param userPhone
     * @param password
     */
    public void insertUser(String userPhone,String password){
        String insert_sql = "insert into user1(_id,phone,password,relname,id_card,id_img,score)" +
                " values(null,?,?,null,null,null,0)";
        sqLiteDatabase.execSQL(insert_sql,new String[]{userPhone,password});
        Log.e("insertUser", "insertUser: success" );
    }

    /**
     * 检查手机号是否重复
     * @param userphone 用户输入的手机号
     * @return true重复 false不重复
     */
    public boolean queryPhoneUser(String userphone){
        String sql = "select phone from user1";
        Cursor c = sqLiteDatabase.rawQuery(sql,null);
        while (c.moveToNext()){
            String phone = c.getString(c.getColumnIndex("phone"));
            if(phone.equals(userphone)){
                return true;
            }
        }
        return false;
    }

    /**
     * 检查登录密码是否正确
     * @param userphone 用户输入的手机号
     * @param password 用户输入的密码
     * @return true正确 false 不正确
     */
    public boolean pswRightUser(String userphone,String password){
        String sql = "select password from user1 where phone=?";
        Cursor c = sqLiteDatabase.rawQuery(sql,new String[]{userphone});
        String relPass = "";
        while (c.moveToNext()){
            relPass = c.getString(c.getColumnIndex("password"));
        }
        if(relPass.equals(password))
            return true;
        else
            return false;
    }

    /**
     * 查询所有用户
     */
    public void queryAllUser(){
        String sql = "select * from user1";
        Cursor c = sqLiteDatabase.rawQuery(sql,null);
        while (c.moveToNext()){
            //_id
            int id = c.getInt(c.getColumnIndex("_id"));
            String phone = c.getString(c.getColumnIndex("phone"));
            String password = c.getString(c.getColumnIndex("password"));
            String relname = c.getString(c.getColumnIndex("relname"));
            String id_card = c.getString(c.getColumnIndex("id_card"));
            String id_img = c.getString(c.getColumnIndex("id_img"));
            int score = c.getInt(c.getColumnIndex("score"));
            User user = new User(id,phone,password,relname,id_card,id_img,score);
            Log.e("queryAllUser", "queryAllUser: " + user.toString() );
        }
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
    public void insertOrder(int orderId,String orderName,String orderPhone,String startPlace,String endPlace,int payment,int type,String info) {
        String sql = "insert into order1(_id,order_id,order_name,order_phone,receive_id,receive_name,receive_phone,start_place,end_place,payment,type,status,finish,info,score)" +
                "values ( null, ? , ? ,?,null,null,null,?,?,?,?,0,?,?,0)";
        sqLiteDatabase.execSQL(sql,new Object[]{orderId,orderName,orderPhone,startPlace,endPlace,payment,type,"未完成",info});
        Log.e("insertOrder", "insertOrder: success" );
    }

    //查询所有订单
    public void queryAllOrder(){
        String strSelect = "select * from order1";
        Cursor c = sqLiteDatabase.rawQuery(strSelect,null);
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
            Log.e("queryAllOrder", "queryAllOrder: "+order.toString() );
        }
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
     * 用户点击接单 更新状态 同时写入接单人id name phone
     * @param id 订单id
     * @param receId 接单人id
     * @param name 接单人name
     * @param phone 接单人phone
     */
    public void updateStatusOrder(int id,int receId,String name,String phone) {
        String sql = "update order1 set receive_id=?,receive_name=?,receive_phone=?,status=1 where _id=?";
        sqLiteDatabase.execSQL(sql,new String[]{String.valueOf(receId),name,phone,String.valueOf(id)});
        Log.e("updateStete", "updateStete: success" );
    }

    /**
     * 用户接单 更新User score+2
     * @param id
     */
    public void updateReceScoreUser(int id){
        //先获取用户的score
        String query_sql = "select score from user1 where _id=?";
        Cursor c = sqLiteDatabase.rawQuery(query_sql,new String[]{String.valueOf(id)});
        int score = 0;
        while (c.moveToNext()){
            score = c.getInt(c.getColumnIndex("score"));
        }
        //再set score = score+2
        String update_sql = "update user1 set score=? where _id=?";
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
        String query_sql = "select score from user1 where _id=?";
        Cursor c = sqLiteDatabase.rawQuery(query_sql,new String[]{String.valueOf(id)});
        int score = 0;
        while (c.moveToNext()){
            score = c.getInt(c.getColumnIndex("score"));
        }
        //再在score上加上当前订单的score
        String sql = "update user1 set score=? where _id=?";
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
