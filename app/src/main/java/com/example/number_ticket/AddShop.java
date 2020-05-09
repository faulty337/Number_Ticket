package com.example.number_ticket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.LinearLayout;

import android.widget.EditText;
import android.widget.Switch;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AddShop extends AppCompatActivity {

    private static final String TAG = "AddShopActivity";
    private FirebaseAuth mAuth;
    private String name;
    private String type;
    private String address;
    private String tel_number;
    private Boolean code_use;
    private String code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.addservice);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layoutInflater.inflate(R.layout.activity_add_shop_addservice, linearLayout, true);

        Intent intent = getIntent();

       findViewById(R.id.bt_pv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MyStartActivity(ShopList.class);
               addshop();
            }

        });


    }

    private void MyStartActivity(Class go_to){
        Intent intent = new Intent(AddShop.this, go_to);
        startActivity(intent);
    }

    private void addshop(){
       name = ((EditText)findViewById(R.id.et_sname)).getText().toString(); //추가하는 부분 코드
       type = ((EditText)findViewById(R.id.et_sgroup)).getText().toString();
       address = ((EditText)findViewById(R.id.et_addr)).getText().toString();
       tel_number = ((EditText)findViewById(R.id.et_spNumber)).getText().toString();
       code_use = ((Switch)findViewById(R.id.code_use)).getText().equals(1);//잘 모르겠어서 일단 넣음
       code = ((EditText)findViewById(R.id.et_code)).getText().toString();
    }

}
