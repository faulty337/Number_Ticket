package com.example.number_ticket.customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.number_ticket.R;
import com.example.number_ticket.data.ShopData;
import com.example.number_ticket.data.waitingInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CsRsvActivity extends AppCompatActivity
{
    private static final String TAG = "CsRsvActivity";
    private String shopname;
    private int wait_number;
    private FirebaseFirestore db;
    private TextView pv_info_sname;
    private TextView pv_waitnumber;
    private String shopName;
    private String owner;
    private ShopData shopData;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs_rsv);
        Intent intent = getIntent();
        Button giveticket = findViewById(R.id.bt_give_number);
        shopname = intent.getExtras().getString("name");
        pv_info_sname = findViewById(R.id.pv_info_sname);
        pv_waitnumber = findViewById(R.id.pv_waitnumber);
        Log.d(TAG, shopname);
        db = FirebaseFirestore.getInstance();
        readshop(shopname);
        waitnumber_count();
        giveticket.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CsRsvActivity.this, CsTicketActivity.class);
                intent.putExtra("name", shopname);
                shopData.setWaitnumber(wait_number);
                shopUpdate();
                startActivity(intent);
            }
        });
    }
    private void readshop(final String shopName){
        db.collection("shop")
                .whereEqualTo("name", shopName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                shopData = new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString(),document.get("code").toString(),Boolean.valueOf(document.get("code_use").toString()),document.get("owner").toString());
                                shopData.setWaitnumber(Integer.parseInt(document.get("waitnumber").toString()));
                                dataset(shopData);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void waitnumber_count(){
        db.collection("shop")
                .document(shopname)
                .collection("waitinglist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    wait_number = task.getResult().size();
                    pv_waitnumber.setText(wait_number + " 명");
                    Log.d(TAG, wait_number+"Aaa");

                }
            }
        });
    }
    private void dataset(ShopData shopData){
        pv_info_sname.setText(shopData.getName());

    }
    private void shopUpdate() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("shop").document(shopname).set(shopData);
    }

}
