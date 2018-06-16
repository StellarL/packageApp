package com.example.packageapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private CardView cvAdd;
    private Button button;
    private EditText et_username,et_password,et_repeatpassword;
    private DBUtil dbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ShowEnterAnimation();
        initView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("btnClick", "onClick: " );
                String userPhone = et_username.getText().toString();
                String password = et_password.getText().toString();//六位以上
                String repPassword = et_repeatpassword.getText().toString();
                if(userPhone.length()==0 || userPhone.equals("") || userPhone.isEmpty() || userPhone==null){
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if (password.equals("") || password.length()==0 || password.isEmpty() || password==null){
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                } else if (repPassword.equals("") || repPassword.length()==0 || repPassword.isEmpty() || repPassword==null){
                    Toast.makeText(RegisterActivity.this,"请输入确认密码",Toast.LENGTH_SHORT).show();
                } else if(!isPhoneLegal(userPhone)){
                    Toast.makeText(RegisterActivity.this,"手机号不正确",Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6){
                    Toast.makeText(RegisterActivity.this,"密码至少为6位数字",Toast.LENGTH_SHORT).show();
                } else if (!repPassword.equals(password)){
                    Toast.makeText(RegisterActivity.this,"密码与确认密码不一致",Toast.LENGTH_SHORT).show();
                } else {
                    //正确
                    //检查重复
                    boolean isRep = dbUtil.queryPhoneUser(userPhone);
                    if (isRep) {
                        //重复
                        Toast.makeText(RegisterActivity.this,"手机号已注册，请重新输入",Toast.LENGTH_SHORT).show();
                    } else {
                        //不重复
                        dbUtil.insertUser(userPhone, password);
                        Intent intent = new Intent(RegisterActivity.this,SetIdentActivity.class);
                        intent.putExtra("userphone",userPhone);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private boolean isPhoneLegal(String userPhone){
        String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        Pattern p = Pattern.compile(PHONE_NUMBER_REG);
        Matcher m = p.matcher(userPhone);
        return m.matches();
    }

    private void initView() {
        fab = findViewById(R.id.fab);
        cvAdd = findViewById(R.id.cv_add);
        button = findViewById(R.id.bt_go);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_repeatpassword = findViewById(R.id.et_repeatpassword);
        dbUtil = new DBUtil(RegisterActivity.this,"User3");
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }
}
