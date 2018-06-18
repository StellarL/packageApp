package com.example.packageapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by 李馨 on 2018/6/9.
 */

public class AllOrdersAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Order> arrayList;
    private selfDialog sdlog;
    private String phone;


    public AllOrdersAdapter(Context context, ArrayList<Order> arrayList,String phone) {
        this.context = context;
        this.arrayList = arrayList;
        this.phone = phone;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        MyHolder myHolder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.layout_all_orders,null);
            myHolder = new MyHolder();
            myHolder.startPlace = view.findViewById(R.id.startPlace);
            myHolder.endPlace = view.findViewById(R.id.endPlace);
            myHolder.payment = view.findViewById(R.id.payment);
            myHolder.type = view.findViewById(R.id.type);
            myHolder.button = view.findViewById(R.id.receiveBtn);
            view.setTag(myHolder);
        }else{
            view = convertView;
            myHolder = (MyHolder) view.getTag();
        }

        final Order order = (Order)getItem(position);
        String startPlace = order.getStartPlace();
        String endPlace = order.getEndPlace();
        int payment = order.getPayment();
        String type=  "";
        switch (order.getType()){
            case 1:
                type = "小包裹";
                break;
            case 2:
                type = "中包裹";
                break;
            case 3:
                type = "大包裹";
                break;
        }
        myHolder.startPlace.setText(startPlace);
        myHolder.endPlace.setText(endPlace);
        myHolder.payment.setText(payment+"");
        myHolder.type.setText(type);
        //点击接单按钮
        myHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sdlog = new selfDialog(context);
                sdlog.setYesOnclickListener(new selfDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        DBUtil dbUtilOrder = new DBUtil(context,"Order");


                        DBUtil dbUtilUser = new DBUtil(context,"User3");
                        User user = dbUtilUser.queryByPhoneUser(phone);
                        //判斷是否接自己的单
                     if(order.getOrderId() == user.getId()){
                         Toast.makeText(context,"此订单为你的订单，不能接自己的单",Toast.LENGTH_SHORT).show();
                         sdlog.dismiss();
                     }else {

                        dbUtilOrder.updateStatusOrder(order.getId(),user.getId(),user.getRelName(),user.getPhone());
                        //todo 更新User数据库 score+2
                        dbUtilUser.updateReceScoreUser(user.getId());
                        dbUtilUser.queryAllUser();
                        Order order1 = dbUtilOrder.queryByIdOrder(order.getId());
                        Log.e("order1", "onYesClick: "+order1.toString());
                        Intent intent = new Intent(context,DetailActivity.class);
                        intent.putExtra("id",order.getId());
                        context.startActivity(intent);
                        sdlog.dismiss();
                    }}
                });
                sdlog.setNoOnclickListener(new selfDialog.onNoOnclickListener(){

                    @Override
                    public void onNoClick() {
                        sdlog.dismiss();
                    }
                });
                sdlog.show();





            }
        });

        return view;
    }

    class MyHolder{
        TextView startPlace,endPlace,type,payment;
        Button button;
    }
}
