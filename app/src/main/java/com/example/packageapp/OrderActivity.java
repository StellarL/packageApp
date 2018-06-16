package com.example.packageapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    private EditText et_ordername,et_orderphone,et_payment,et_info;
    private Spinner sp_startplace,sp_endplace,sp_type;
    private Button btnOrder;
    private DBUtil dbUtilUser;
    private DBUtil dbUtilOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        dbUtilUser = new DBUtil(OrderActivity.this,"User3");
        String userphone = getIntent().getStringExtra("userphone");
        final User user = dbUtilUser.queryByPhoneUser(userphone);
        et_ordername.setText(user.getRelName());
        et_orderphone.setText(user.getPhone());

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int order_id = user.getId();
                String order_name = et_ordername.getText().toString();
                String order_phone = et_orderphone.getText().toString();
                String startplace = sp_startplace.getSelectedItem().toString();
                String endplace = sp_endplace.getSelectedItem().toString();
                int type = 0;
                switch (sp_type.getSelectedItem().toString()){
                    case "大包裹":
                        type = 3;break;
                    case "中包裹":
                        type = 2;break;
                    case "小包裹":
                        type = 1;break;
                }
                int payment = Integer.valueOf(et_payment.getText().toString());
                String info = et_info.getText().toString();
                if(startplace.equals("") || startplace.length()==0){
                    Toast.makeText(OrderActivity.this,"请选择起点",Toast.LENGTH_SHORT).show();
                } else if(endplace.equals("") || endplace.length()==0){
                    Toast.makeText(OrderActivity.this,"请选择终点",Toast.LENGTH_SHORT).show();
                } else if(type ==0 ){
                    Toast.makeText(OrderActivity.this,"请选择包裹大小",Toast.LENGTH_SHORT).show();
                } else {

                    dbUtilOrder = new DBUtil(OrderActivity.this, "Order");
                    dbUtilOrder.insertOrder(order_id, order_name, order_phone, startplace, endplace, payment, type, info);
                    Toast.makeText(OrderActivity.this,"成功下单",Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(OrderActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    private void initView() {
        et_ordername = findViewById(R.id.et_ordername);
        et_orderphone = findViewById(R.id.et_orderphone);
        et_payment = findViewById(R.id.et_payment);
        et_info = findViewById(R.id.et_info);
        sp_startplace = findViewById(R.id.spinnerStart);
        sp_endplace = findViewById(R.id.spinnerEnd);
        sp_type = findViewById(R.id.spinnerType);
        btnOrder = findViewById(R.id.btnOrder);

        final String[] arrType=new String[]{
                "大包裹","中包裹","小包裹"
        };
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(OrderActivity.this,android.R.layout.simple_spinner_item,arrType);
        sp_type.setAdapter(arrayAdapter);

        final String[] arrPlace=new String[]{
                "D1","D2","D3","D5","D6","D7","D8","D9","D10","D11","C1","C2","C7","A1","A2","A3","A4","A5","A6","A7","A8"
        };
        ArrayAdapter<String> arrayAdapter2=new ArrayAdapter<String>(OrderActivity.this,android.R.layout.simple_spinner_item,arrPlace);
        sp_startplace.setAdapter(arrayAdapter2);
        sp_endplace.setAdapter(arrayAdapter2);


    }
}
