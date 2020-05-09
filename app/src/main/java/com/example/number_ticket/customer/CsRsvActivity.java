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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CsRsvActivity extends AppCompatActivity
{
    private static final String TAG = "CsRsvActivity";
    private String shopname;
    private FirebaseFirestore db;
    private TextView pv_info_sname;
    private TextView pv_waitnumber;
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
        giveticket.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CsRsvActivity.this, CsTicketActivity.class);
                intent.putExtra("name", shopname);
                startActivity(intent);
            }
        });
    }
    private void readshop(String shopName){
        db.collection("shop")
                .whereEqualTo("name", shopName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ShopData shopData = new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString(),document.get("code").toString(),Boolean.valueOf(document.get("code_use").toString()),document.get("owner").toString());
                                Log.d(TAG, document.get("code_use").toString());
                                dataset(shopData);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void dataset(ShopData shopData){
        pv_info_sname.setText(shopData.getName());
        pv_waitnumber.setText(shopData.getWaitnumber() + " 명");
    }
}
