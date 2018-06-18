package com.example.packageapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ConfirmOrderActivity extends AppCompatActivity {

    private Button btnConfirm;
    private selfDialog sfdig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);


        btnConfirm=findViewById(R.id.btnConfirm2);
        Log.e("btnConfirm", "onCreate: " );
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("btnConfirm", "onClick: " );
                sfdig=new selfDialog(ConfirmOrderActivity.this);
                sfdig.setTitle("确认收货");
                sfdig.setMessage("您是否确认收到货？");
                sfdig.setYesOnclickListener(new selfDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        int id = getIntent().getIntExtra("id",0);
                        //更新已完成
                        DBUtil dbUtil = new DBUtil(ConfirmOrderActivity.this,"Order");
                        dbUtil.updateFinishOrder(id);
                        startActivity(new Intent(ConfirmOrderActivity.this,DoEvalActivity.class).putExtra("id",id));
                        finish();
                    }
                });
                sfdig.setNoOnclickListener(new selfDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        sfdig.dismiss();
                    }
                });
                sfdig.show();
            }
        });
    }
}
