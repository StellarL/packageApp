package com.example.packageapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 李馨 on 2018/6/13.
 */

public class MyOrderAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Order> arrayList;

    public MyOrderAdapter(Context context, ArrayList<Order> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
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
        View view;
        MyHolder myHolder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.layout_my_receive,null);
            myHolder = new MyHolder();
            myHolder.startPlace = view.findViewById(R.id.startPlace);
            myHolder.endPlace = view.findViewById(R.id.endPlace);
            myHolder.type = view.findViewById(R.id.type);
            myHolder.payment = view.findViewById(R.id.payment);
            myHolder.finish = view.findViewById(R.id.status);
            view.setTag(myHolder);
        }else{
            view = convertView;
            myHolder = (MyHolder)view.getTag();
        }

        Order order = (Order)getItem(position);
        String startPlaceText = order.getStartPlace();
        String endplaceText = order.getEndPlace();
        String typeText = "";
        switch (order.getType()){
            case 1:
                typeText = "小包裹";
                break;
            case 2:
                typeText = "中包裹";
                break;
            case 3:
                typeText = "大包裹";
                break;
        }
        String paymentText = order.getPayment()+"";
        String finishText = "";
        int statusText = order.getStatus();
        switch (statusText){
            case 0:
                //未接单
                finishText = "未接单";
                break;
            case 1:
                //已接单
                finishText = order.getFinish();
                break;
        }
        myHolder.startPlace.setText(startPlaceText);
        myHolder.endPlace.setText(endplaceText);
        myHolder.type.setText(typeText);
        myHolder.payment.setText(paymentText);
        myHolder.finish.setText(finishText);

        return view;
    }

    class MyHolder{
        TextView startPlace,endPlace,type,payment,finish;
    }
}
