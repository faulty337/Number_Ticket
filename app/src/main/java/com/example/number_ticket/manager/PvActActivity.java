package com.example.number_ticket.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.number_ticket.R;
import com.example.number_ticket.SelectUserActivity;
import com.example.number_ticket.data.ShopData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static java.util.jar.Pack200.Unpacker.TRUE;

public class PvActActivity extends Activity {
    private static final String TAG = "PvActActivity";
    private String shopname;
    private TextView pv_info_sname;
    private TextView pv_code;
    private TextView pv_waitnumber;
    private int waittime;
    private int currentnum;
    private TextView pv_waittime;
    private Switch bt_pv_active;
    private Button bt_refresh;
    private FirebaseFirestore db;
    private ShopData shopData;
    private String code, code_check = "";
    private Boolean shopuse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pv_act);
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        pv_info_sname = findViewById(R.id.pv_info_sname);
        pv_code = findViewById(R.id.pv_code);
        pv_waitnumber = findViewById(R.id.pv_waitnumber);
        bt_pv_active = findViewById(R.id.bt_pv_activate);
        pv_waittime = findViewById(R.id.pv_waittime);
        bt_refresh = findViewById(R.id.bt_refresh);
        shopname = intent.getExtras().getString("name");


        findViewById(R.id.bt_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoHome();
            }
        });//홈으로 가는 부분

        findViewById(R.id.bt_pv_waitlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyStartActivity(PvWaitlistActivity.class, shopname);
            }
        });

        findViewById(R.id.bt_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent refresh = getIntent();
                finish();
                startActivity(refresh);
                //새로고침 버튼 클릭시 새로고침하는 코드
            }
        });

        bt_pv_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                shopuse = isChecked;
                if (isChecked){
                    modify_code_use(isChecked);
                }else{
                    modify_code_use(shopuse);
                }
            }
        });//번호표 사용 여부 변경 코드

        readshop(shopname);
        waitnumber_count();
        currentNum_count();
    }

    private void GotoHome() {
        Intent intent = new Intent(PvActActivity.this, SelectUserActivity.class);
        startActivity(intent);
    }//홈으로

    private void MyStartActivity(Class go_to, String shopname){
        Intent intent = new Intent(PvActActivity.this, go_to);
        intent.putExtra("name", shopname);
        startActivity(intent);
    }//화면전환

    private void readshop(final String shopName){
        db.collection("shop")
                .document(shopName)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException e) {
                        if (document != null){
                            Log.d(TAG, document.get("name").toString());
                            shopData = new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString(),document.get("code").toString(),Boolean.valueOf(document.get("code_use").toString()),document.get("owner").toString());
                            shopData.setWaitnumber(Integer.parseInt(document.get("waitnumber").toString()));
                            shopData.setUse(Boolean.valueOf(document.get("use").toString()));
                            code_check = shopData.getCode();
                            shopuse = shopData.getUse();
                            dataset(shopData);
                        }else {

                        }
                    }
                });
    }//매장 data 긁어오기

    private void dataset(ShopData shopData){
        pv_info_sname.setText(shopData.getName());
        pv_code.setText(shopData.getCode());
    }//매장이름 등 data 넣어주기

    private void waitnumber_count(){
        db.collection("shop")
                .document(shopname)
                .collection("waitinglist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            waittime = task.getResult().size();
                            pv_waittime.setText(waittime + " 명");
                            Log.d(TAG, waittime+"Aaa");
                        }
                    }
                });
    }//현재 대기인원 넣어주기

    private void currentNum_count(){
        Query docRef = db.collection("shop").document(shopname).collection("waitinglist").whereEqualTo("onoff",false).orderBy("ticket_number").limit(1);
        docRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null){
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        pv_waitnumber.setText(document.getData().get("ticket_number").toString());
                    }
                }else {
                        pv_waitnumber.setText("0");
                }
            }
        });

        db.collection("shop")
                .document(shopname)
                .collection("waitinglist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
//
                            pv_waitnumber.setText(currentnum + " 번");
                            Log.d(TAG, currentnum+"Aaa");
                        }
                    }
                });
    }

    private void modify_code_use(Boolean shopuse){
        db.collection("shop").document(shopname).update("use", shopuse);
    }
}
