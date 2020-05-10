package com.example.number_ticket.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.CompoundButton;
import android.widget.LinearLayout;

import android.widget.EditText;
import android.widget.Switch;


import androidx.appcompat.app.AppCompatActivity;

import com.example.number_ticket.R;
import com.example.number_ticket.data.ShopData;
import com.example.number_ticket.data.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddShop extends AppCompatActivity {

    private static final String TAG = "AddShopActivity";
    private FirebaseAuth mAuth;
    private String name;
    private String type;
    private String address;
    private String tel_number;
    private Boolean code_use;
    private String code;
    private String owner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        Switch sw = ((Switch)findViewById(R.id.code_use));
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                code_use = isChecked;
                ((EditText)findViewById(R.id.et_code)).setClickable(isChecked);
                ((EditText)findViewById(R.id.et_code)).setFocusable(isChecked);
                ((EditText)findViewById(R.id.et_code)).setFocusableInTouchMode(isChecked);
                ((EditText)findViewById(R.id.et_code)).setEnabled(isChecked);
            }
        });

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.addservice);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.activity_add_shop_addservice, linearLayout, true);

        Intent intent = getIntent();

       findViewById(R.id.bt_pv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addshop();
            }
        });

        mAuth = FirebaseAuth.getInstance();

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
        code = ((EditText)findViewById(R.id.et_code)).getText().toString();

        shopUpdate();
        MyStartActivity(ShopList.class);
    }

    private void shopUpdate() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        owner = user.getUid();
        ShopData shopData = new ShopData(name, tel_number, type, address, code, code_use, owner);
        db.collection("shop").document(name).set(shopData);
    }

}
