package com.example.packageapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SetIdentActivity extends AppCompatActivity {

    private EditText et_relname,et_cardId;
    private ImageView cardUp,cardBack;
    private Button btnGo;
    private DBUtil dbUtil;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ident);

        initView();

        final String userphone = getIntent().getStringExtra("userphone");

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String relname = et_relname.getText().toString();
                String cardId = et_cardId.getText().toString();
                if(relname.length()==0 || relname.equals("") || relname.isEmpty() || relname==null){
                    Toast.makeText(SetIdentActivity.this,"请输入真实姓名",Toast.LENGTH_SHORT).show();
                } else if (cardId.equals("") || cardId.length()==0 || cardId.isEmpty() || cardId==null){
                    Toast.makeText(SetIdentActivity.this,"请输入身份证号码",Toast.LENGTH_SHORT).show();
                } else if (!isCardLegal(cardId)) {
                    Toast.makeText(SetIdentActivity.this, "身份证号码不正确", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SetIdentActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //更新数据库
                    dbUtil.addIdentUser(userphone,relname,cardId);
                    dbUtil.queryAllUser();
                    //将userphone 存入 SharedPreferences
                    editor.putString("userphone",userphone);
                    editor.apply();
                    //跳转到主界面
                    Intent intent = new Intent(SetIdentActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        });


    }

    private boolean isCardLegal(String idCard){
        String reg = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(idCard);
        return m.matches();
    }

    private void initView() {
        et_relname = findViewById(R.id.et_relname);
        et_cardId = findViewById(R.id.et_cardId);
        cardUp = findViewById(R.id.cardUp);
        cardBack = findViewById(R.id.cardBack);
        btnGo = findViewById(R.id.bt_go);
        dbUtil = new DBUtil(SetIdentActivity.this,"User3");
        editor = getSharedPreferences("data",MODE_PRIVATE).edit();
    }
}
