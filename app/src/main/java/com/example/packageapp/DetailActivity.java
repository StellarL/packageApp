package com.example.packageapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private TextView tv_ordername,tv_orderphone,tv_startPlace,tv_endPlace,tv_type,tv_payment,tv_info;
    private Button btnComfirm;
    private DBUtil dbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
        int id = getIntent().getIntExtra("id",0);
        dbUtil = new DBUtil(DetailActivity.this,"Order");
        final Order order = dbUtil.queryByIdOrder(id);
        tv_ordername.setText(order.getOrderName());
        tv_orderphone.setText(order.getOrderPhone());
        tv_startPlace.setText(order.getStartPlace());
        tv_endPlace.setText(order.getEndPlace());
        String type = "";
        switch (order.getType()) {
            case 1:
                type = "小包裹";
                break;
            case 2:
                type = "中包裹";
                break;
            case 3:
                type = "大包裹";
        }
        tv_type.setText(type);
        tv_payment.setText(order.getPayment()+"");
        tv_info.setText(order.getInfo());
        btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(order.getFinish().equals("已完成")){
                    Intent intent = new Intent(DetailActivity.this,DoEvalActivity.class);
                    intent.putExtra("id",order.getId());
                    startActivity(intent);
                }else{
                    Toast.makeText(DetailActivity.this,"当前订单未完成,不能查看评价",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        MainActivity.lastSelectedPosition = 1;
//        startActivity(new Intent(DetailActivity.this,MainActivity.class));
//        finish();
//    }

    private void initView() {
        tv_ordername = findViewById(R.id.tv_ordername);
        tv_orderphone = findViewById(R.id.tv_orderphone);
        tv_startPlace = findViewById(R.id.tv_startPlace);
        tv_endPlace = findViewById(R.id.tv_endPlace);
        tv_type = findViewById(R.id.tv_type);
        tv_payment = findViewById(R.id.tv_payment);
        tv_info = findViewById(R.id.tv_info);
        btnComfirm = findViewById(R.id.btnConfirm);
    }
}
