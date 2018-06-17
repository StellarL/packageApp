package com.example.packageapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class mineActivity extends AppCompatActivity {

    private TextView tv_relname,tv_phone,tv_idCard;
    private DBUtil dbUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        tv_relname = findViewById(R.id.tv_relname);
        tv_phone = findViewById(R.id.tv_phone);
        tv_idCard = findViewById(R.id.tv_idCard);

        dbUtil = new DBUtil(mineActivity.this,"User3");
        User user = dbUtil.queryByPhoneUser(getIntent().getStringExtra("phone"));
        tv_relname.setText(user.getRelName());
        tv_phone.setText(user.getPhone());
        tv_idCard.setText(user.getIdCard());

    }
}
