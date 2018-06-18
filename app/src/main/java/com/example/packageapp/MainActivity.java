package com.example.packageapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar bottomNavigationBar;
    private DrawerLayout drawerLayout;
    static int lastSelectedPosition = 0;
    private MyFragment mMyFragment;
    private ScanFragment mScanFragment;
    private HomeFragment mHomeFragment;
    ArrayList<Order> arrayList;
    ListView listView;
    DBUtil dbUtilOrder;
    String phone;
    DBUtil dbUtilUser;
    private UserDBHelper userDBHelper;
    private OrderDBHelper orderDBHelper;
    private TextView username,phonenumber;


//    public int getLastSelectedPosition() {
//        return lastSelectedPosition;
//    }
//
//    public void setLastSelectedPosition(int lastSelectedPosition) {
//        this.lastSelectedPosition = lastSelectedPosition;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NavigationView navView = findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);

//        LayoutInflater layoutInflater = getLayoutInflater();
//        View nav_header = layoutInflater.inflate(R.layout.nav_header,null);

        userDBHelper=new UserDBHelper(MainActivity.this,"User3",null,1);
        SQLiteDatabase sqLiteDatabaseUser=userDBHelper.getReadableDatabase();
        orderDBHelper = new OrderDBHelper(MainActivity.this,"Order",null,1);
        SQLiteDatabase sqLiteDatabaseOrder = orderDBHelper.getReadableDatabase();

        phone = PreferenceManager.getDefaultSharedPreferences(this).getString("userphone","");
        Log.e("userPhone", "onCreate: phone:"+phone);

        phonenumber=(TextView)headerView.findViewById(R.id.phone);
        username=(TextView)headerView.findViewById(R.id.username1);

        DBUtil dbUtilUser = new DBUtil(MainActivity.this,"User3");
        User user = dbUtilUser.queryByPhoneUser(phone);
        Log.e("user", "onCreate: " +user.toString() );
        Log.e("user", "onCreate: "+user.getPhone()+" "+ user.getRelName()+" "+phonenumber+" "+username);
        phonenumber.setText(user.getPhone());
        username.setText(user.getRelName());


        drawerLayout = findViewById(R.id.drawer_layout);
        listView = findViewById(R.id.listOrder);


//        navView.setCheckedItem(R.id.nav_score);
//        navView.setItemTextColor(ColorStateList.valueOf(Color.parseColor("#9a9a9a")));
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                drawerLayout.closeDrawers();
                switch (item.getItemId()){
                    case R.id.nav_score:
                        startActivity(new Intent(MainActivity.this,evalActivity.class).putExtra("phone",phone));
                        break;
                    case R.id.info:
                        startActivity(new Intent(MainActivity.this,mineActivity.class).putExtra("phone",phone));
                        break;
                    case R.id.fix:
                        startActivity(new Intent(MainActivity.this,SetIdentActivity.class).putExtra("userphone",phone));
                        break;

                }
                return true;
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //底部导航栏
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        /** 导航基础设置 包括按钮选中效果 导航栏背景色等 */
        bottomNavigationBar
                .setTabSelectedListener(this)
                .setMode(BottomNavigationBar.MODE_SHIFTING)
                /**
                 *  setMode() 内的参数有三种模式类型：
                 *  MODE_DEFAULT 自动模式：导航栏Item的个数<=3 用 MODE_FIXED 模式，否则用 MODE_SHIFTING 模式
                 *  MODE_FIXED 固定模式：未选中的Item显示文字，无切换动画效果。
                 *  MODE_SHIFTING 切换模式：未选中的Item不显示文字，选中的显示文字，有切换动画效果。
                 */

                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                /**
                 *  setBackgroundStyle() 内的参数有三种样式
                 *  BACKGROUND_STYLE_DEFAULT: 默认样式 如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC
                 *                                    如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
                 *  BACKGROUND_STYLE_STATIC: 静态样式 点击无波纹效果
                 *  BACKGROUND_STYLE_RIPPLE: 波纹样式 点击有波纹效果
                 */

                .setActiveColor(R.color.colorPurple) //选中颜色
                .setInActiveColor(R.color.colorWhite) //未选中颜色
                .setBarBackgroundColor(R.color.colorTheme);//导航栏背景色

        /** 添加导航按钮 */
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.first, "首页"))
                .addItem(new BottomNavigationItem(R.drawable.second, "我的接单"))
                .addItem(new BottomNavigationItem(R.drawable.third, "我的下单"))
                .setFirstSelectedPosition(MainActivity.lastSelectedPosition)
                .initialise(); //initialise 一定要放在 所有设置的最后一项

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.reorder);
        }
        dbUtilOrder = new DBUtil(MainActivity.this,"Order");
        switch (lastSelectedPosition){
            case 0:

//        dbUtilOrder.insertInitOrder();
                arrayList = dbUtilOrder.queryAllState0Order();
                AllOrdersAdapter AllOrdersAdapter = new AllOrdersAdapter(MainActivity.this,arrayList,phone);
                listView.setAdapter(AllOrdersAdapter);
                break;
            case 1:
                arrayList = dbUtilOrder.selectMyReceiveOrder(phone);
                MyOrderAdapter myReceAdapter = new MyOrderAdapter(MainActivity.this,arrayList);
                listView.setAdapter(myReceAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Order order = arrayList.get(position);
                        startActivity(new Intent(MainActivity.this,DetailActivity.class).putExtra("id",order.getId()));
                    }
                });
                break;
            case 2:
                arrayList = dbUtilOrder.selectMyOrder(phone);
                MyOrderAdapter myOrderAdapter = new MyOrderAdapter(MainActivity.this,arrayList);
                listView.setAdapter(myOrderAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Order order = arrayList.get(position);
                        startActivity(new Intent(MainActivity.this,ConfirmOrderActivity.class).putExtra("id",order.getId()));

                    }
                });
                break;
        }



        //悬浮按钮
        final FloatingActionButton fabtn = findViewById(R.id.fab);
        fabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,OrderActivity.class);
                intent.putExtra("userphone",phone);
                startActivity(intent);
//                Snackbar.make(v, "Data add", Snackbar.LENGTH_SHORT).setAction("doSome", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //事件
//                        Toast.makeText(MainActivity.this,"data res",Toast.LENGTH_SHORT).show();
//                    }
//                }).show();
            }
        });


    }

//    private void setDefaultFragment() {
//        Log.e("      ", "setDefaultFragment: ");
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();
////        mScanFragment = ScanFragment.newInstance("我的接单");
//        mHomeFragment = HomeFragment.newInstance("首页");
//        transaction.replace(R.id.tb, mHomeFragment);
//        transaction.commit();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch(item.getItemId()){
           case android.R.id.home:
               drawerLayout.openDrawer(GravityCompat.START);
               break;
       }
        return true;
    }
    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = this.getFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                //首页 接单
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance("首页");
                }
                transaction.replace(R.id.tb, mHomeFragment);
                arrayList = dbUtilOrder.queryAllState0Order();
                AllOrdersAdapter allOrdersAdapter = new AllOrdersAdapter(MainActivity.this,arrayList,phone);
                listView.setAdapter(allOrdersAdapter);
                break;
            case 1:
                //我的接单
                if (mScanFragment == null) {
                    mScanFragment = ScanFragment.newInstance("我的接单");
//                    Intent intent = new Intent();
//                    startActivity(intent);
//                    finish();
                }
                transaction.replace(R.id.tb, mScanFragment);
                arrayList = dbUtilOrder.selectMyReceiveOrder(phone);
                MyOrderAdapter myReceAdapter = new MyOrderAdapter(MainActivity.this,arrayList);
                listView.setAdapter(myReceAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Order order = arrayList.get(position);
                        startActivity(new Intent(MainActivity.this,DetailActivity.class).putExtra("id",order.getId()));
                    }
                });
                break;
            case 2:
                //我的下单
                if (mMyFragment == null) {
                    mMyFragment = MyFragment.newInstance("我的下单");
//                    Intent intent = new Intent();
//                    startActivity(intent);
//                    finish();
                }
                transaction.replace(R.id.tb, mMyFragment);
                arrayList = dbUtilOrder.selectMyOrder(phone);
                MyOrderAdapter myOrderAdapter = new MyOrderAdapter(MainActivity.this,arrayList);
                listView.setAdapter(myOrderAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Order order = arrayList.get(position);
                        startActivity(new Intent(MainActivity.this,ConfirmOrderActivity.class).putExtra("id",order.getId()));

                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

}

