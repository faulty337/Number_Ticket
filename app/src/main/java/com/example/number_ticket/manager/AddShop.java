package com.example.number_ticket.manager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.number_ticket.R;
import com.example.number_ticket.customer.CsRsvActivity;
import com.example.number_ticket.data.ServiceInfo;
import com.example.number_ticket.data.ShopData;
import com.example.number_ticket.popup.AddServicePopup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class AddShop extends AppCompatActivity implements AddServicePopup.OnCompleteListenner{

    private static final String TAG = "AddShopActivity";
    private FirebaseAuth mAuth;
    private String name, type, address, tel_number, code, owner, waitingtime, space;
    private Boolean code_use = false;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private Button serviceadd;
    private ArrayList<ServiceInfo> serviceList = new ArrayList<ServiceInfo>();
    private LinearLayout code_layout;
    private LinearLayout service_add_layout;

    @Override
    public void onInputedData(String service, String time) {
        serviceList.add(new ServiceInfo(service, time));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        ((EditText)findViewById(R.id.et_code)).setClickable(false);
        ((EditText)findViewById(R.id.et_code)).setFocusable(false);
        ((EditText)findViewById(R.id.et_code)).setFocusableInTouchMode(false);
        ((EditText)findViewById(R.id.et_code)).setEnabled(false);
        //switch버튼 초기값을 fasle로 설정
        code_layout = findViewById(R.id.code_input);
        service_add_layout = findViewById(R.id.service_input);

        serviceadd = findViewById(R.id.bt_add_sevicetime);
        Switch sw = ((Switch)findViewById(R.id.code_use));
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                code_use = isChecked;
                ((EditText)findViewById(R.id.et_code)).setClickable(isChecked);
                ((EditText)findViewById(R.id.et_code)).setFocusable(isChecked);
                ((EditText)findViewById(R.id.et_code)).setFocusableInTouchMode(isChecked);
                ((EditText)findViewById(R.id.et_code)).setEnabled(isChecked);
                if (isChecked){
                    code_layout.setVisibility(View.VISIBLE);
                }else{
                    code_layout.setVisibility(View.INVISIBLE);
                }
            }
        });
        serviceadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceadd();
            }
        });
        Switch service_expand = ((Switch)findViewById(R.id.sevice_expanded_switch));
        service_expand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    service_add_layout.setVisibility(View.VISIBLE);
                }else {
                    service_add_layout.setVisibility(View.INVISIBLE);
                }

            }
        });//false면 코드입력 불가능하게 하는 코드
        findViewById(R.id.bt_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyStartActivity(ShopList.class);
            }
        });//뒤로가기

       findViewById(R.id.bt_pv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.activity_add_shop_check, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(AddShop.this, R.style.MySaveAlertTheme);
                builder.setView(dialogView);
                builder.setPositiveButton("저장", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        addshop();
                        Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });//버튼 클릭 시 firebase에 data저장
        mAuth = FirebaseAuth.getInstance();
    }


    public void serviceadd(){
        DialogFragment Fragment = new AddServicePopup();
        Fragment.show(getSupportFragmentManager(), "dialog");
    }



    private void MyStartActivity(Class go_to){
        Intent intent = new Intent(AddShop.this, go_to);
        startActivity(intent);
    }//화면전환

    private void addshop(){
        name = ((EditText)findViewById(R.id.et_sname)).getText().toString(); //추가하는 부분 코드
        type = ((EditText)findViewById(R.id.et_sgroup)).getText().toString();
        address = ((EditText)findViewById(R.id.et_addr)).getText().toString();
        tel_number = ((EditText)findViewById(R.id.et_spNumber)).getText().toString();
        code = ((EditText)findViewById(R.id.et_code)).getText().toString();
        waitingtime = ((EditText)findViewById(R.id.et_service_time)).getText().toString();
        space = ((EditText)findViewById(R.id.et_service_number)).getText().toString();
        shopUpdate();
        MyStartActivity(ShopList.class);
    }

    private void shopUpdate() {
        owner = user.getEmail();
        Log.d(TAG, owner);
        ShopData shopData = new ShopData(name, tel_number, type, address, code, code_use, owner);
        shopData.setWaitingtime(waitingtime);
        shopData.setSpace_count(Integer.parseInt(space));
        db.collection("shop").document(name).set(shopData);
        for(ServiceInfo service : serviceList){
            db.collection("shop").document(name).collection("service").document(service.getService()).set(service);
        }
    }

}
