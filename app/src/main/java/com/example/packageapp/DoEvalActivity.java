package com.example.packageapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

public class DoEvalActivity extends AppCompatActivity {

    private Button btnSubmit;
    private RatingBar rStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_eval);

        rStar=findViewById(R.id.star);


        int score = rStar.getNumStars();
        DBUtil dbUtil = new DBUtil(DoEvalActivity.this,"Order");
        int id = getIntent().getIntExtra("id",0);
        dbUtil.updateScoreOrder(id,score);
        Order order = dbUtil.queryByIdOrder(id);
        int user_id = order.getReceiveId();
        DBUtil dbUtil1User = new DBUtil(DoEvalActivity.this,"User3");

        dbUtil1User.updateScoreUser(user_id,score);


        btnSubmit=findViewById(R.id.bt_go);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoEvalActivity.this,MainActivity.class));
                finish();
            }
        });



    }
}
