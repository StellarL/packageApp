package com.example.packageapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class evalActivity extends AppCompatActivity {

    private TextView tv_score;
    private DBUtil dbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorce);

        tv_score = findViewById(R.id.tv_score);

        dbUtil = new DBUtil(evalActivity.this,"User3");
        User user = dbUtil.queryByPhoneUser(getIntent().getStringExtra("phone"));
        Log.e("User query eval", "onCreate: "+user.toString() );
        tv_score.setText(user.getScore()+"");
    }
}
