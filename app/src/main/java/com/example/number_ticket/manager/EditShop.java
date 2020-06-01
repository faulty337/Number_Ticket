package com.example.number_ticket.manager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.number_ticket.R;
import com.example.number_ticket.adapter.ServiceAdapter;
import com.example.number_ticket.data.ServiceInfo;
import com.example.number_ticket.data.ShopData;
import com.example.number_ticket.popup.AddServicePopup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class EditShop extends AppCompatActivity {
    private static final String TAG = "EditShopActivity";
    private String shopname;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private String name, type, address, tel_number, code, owner, waitingtime, space;
    private Boolean code_use = false;
    private Button serviceadd;
    private TextView sname;
    private EditText sgroup;
    private EditText saddr;
    private EditText spNumber;
    private ArrayList<ServiceInfo> serviceList = new ArrayList<ServiceInfo>();
    private ShopData shopData;
    private LinearLayout code_layout;
    private LinearLayout service_add_layout;
    private ListView service_list;
    private ServiceAdapter serviceAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shop);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        shopname = intent.getExtras().getString("name");
        sname = findViewById(R.id.tv_sname);
        sgroup = findViewById(R.id.et_sgroup);
        saddr = findViewById(R.id.et_addr);
        spNumber = findViewById(R.id.et_spNumber);


        ((EditText)findViewById(R.id.et_code)).setClickable(false);
        ((EditText)findViewById(R.id.et_code)).setFocusable(false);
        ((EditText)findViewById(R.id.et_code)).setFocusableInTouchMode(false);
        ((EditText)findViewById(R.id.et_code)).setEnabled(false);
        //switch버튼 초기값을 fasle로 설정
        code_layout = findViewById(R.id.code_input);
        service_add_layout = findViewById(R.id.service_input);
        service_list = findViewById(R.id.service_listview);

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
                    service_list.setVisibility(View.VISIBLE);
                }else {
                    service_add_layout.setVisibility(View.INVISIBLE);
                    service_list.setVisibility(View.INVISIBLE);
                }

            }
        });//false면 코드입력 불가능하게 하는 코드

        readshop(shopname);

        findViewById(R.id.bt_pv_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.activity_edit_shop_check, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(EditShop.this, R.style.MySaveAlertTheme);
                builder.setView(dialogView);
                builder.setPositiveButton("수정", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        editshop(shopname);
                        Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
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
        });//edit_shop_check 팝업창


    }

    private void readshop(final String shopName){
        db.collection("shop")
                .document(shopName)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException e) {
                        shopData = new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString(),document.get("code").toString(),Boolean.valueOf(document.get("code_use").toString()),document.get("owner").toString());
                        shopData.setWaitnumber(Integer.parseInt(document.get("waitnumber").toString()));
                        shopData.setUse(Boolean.valueOf(document.get("use").toString()));
                        dataset(shopData);
                    }
                });
    }

    private void dataset(ShopData shopData){
        sname.setText(shopname);
        sgroup.setText(shopData.getType());
        saddr.setText(shopData.getAddress());
        spNumber.setText(shopData.getTel_number());
    }

    public void serviceadd(){
        DialogFragment Fragment = new AddServicePopup();
        Fragment.show(getSupportFragmentManager(), "dialog");
    }

    private void editshop(String shopname) {
        name = shopname; //추가하는 부분 코드
        type = ((EditText)findViewById(R.id.et_sgroup)).getText().toString();
        address = ((EditText)findViewById(R.id.et_addr)).getText().toString();
        tel_number = ((EditText)findViewById(R.id.et_spNumber)).getText().toString();
        if (code_use == true){
            code = ((EditText)findViewById(R.id.et_code)).getText().toString();
        }else{
            code = "사용안함";
        }
        waitingtime = ((EditText)findViewById(R.id.et_service_time)).getText().toString();
        space = ((EditText)findViewById(R.id.et_service_number)).getText().toString();
        shopUpdate();
        MyStartActivity(ShopList.class);
    }

    private void MyStartActivity(Class go_to) {
        Intent intent = new Intent(EditShop.this, go_to);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void shopUpdate() {
        owner = user.getEmail();
        Log.d(TAG, owner);
        ShopData shopData = new ShopData(name, tel_number, type, address, code, code_use, owner);
        shopData.setWaitingtime(waitingtime);
        if(!space.equals("")){
            shopData.setSpace_count(Integer.parseInt(space));
        }
        db.collection("shop").document(name).set(shopData);
        for(ServiceInfo service : serviceList){
            db.collection("shop").document(name).collection("service").document(service.getService()).set(service);
        }
    }




    /*수정 알고리즘

    간단하게 생각하자
    editshop에 있는 data를 button클릭 시 이름에 맞는 매장 부분에 넣어주면 되는거 아니냐
    null 값인건 넣지 말고
    *
    *
    * */
}
