package com.example.packageapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btGo;
    private CardView cv;
    private FloatingActionButton fab;
    private UserDBHelper userDBHelper;
    private SQLiteDatabase sqLiteDatabase;
    private DBUtil dbUtil;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userDBHelper=new UserDBHelper(LoginActivity.this,"User3",null,1);
        sqLiteDatabase=userDBHelper.getReadableDatabase();

        initView();
        setListener();
    }

    private void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btGo = findViewById(R.id.bt_go);
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);
        dbUtil = new DBUtil(LoginActivity.this,"User3");
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
    }

    private void setListener() {
        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Explode explode = new Explode();
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);

                //判断密码是否正确
                String userPhone = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if(userPhone.length()==0 || userPhone.equals("") || userPhone.isEmpty() || userPhone==null){
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if (password.equals("") || password.length()==0 || password.isEmpty() || password==null){
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                } else {
                    if (!dbUtil.pswRightUser(userPhone, password)) {
                        //不正确
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    } else {
                        //正确
                        editor.putString("userphone",userPhone);
                        Log.e("loginAc", "onClick: userPhone:"+userPhone );
                        editor.apply();
                        Log.e("onClick", "onClick: 密码正确" );
                        Intent i2 = new Intent(LoginActivity.this, MainActivity.class);

                        startActivity(i2, oc2.toBundle());
                    }
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, fab, fab.getTransitionName());
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class), options.toBundle());
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);
    }
}
