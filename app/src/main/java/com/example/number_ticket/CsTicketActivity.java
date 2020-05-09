package com.example.number_ticket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CsTicketActivity extends AppCompatActivity {
    private static final String TAG = "CsTicketActivity";
    private String shopName;
    private FirebaseFirestore db;
    private DatabaseReference dr;
    private TextView ticket_num;
    private TextView shopname;
    private TextView time;
    private TextView waitnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs_ticket);
        Intent intent = getIntent();
        shopName = intent.getExtras().getString("name");
        ticket_num = findViewById(R.id.ticket_num);
        shopname = findViewById(R.id.ticket_num);
        time = findViewById(R.id.time);
        waitnumber = findViewById(R.id.cs_waitnumber);
        Log.d(TAG, shopName);
        db = FirebaseFirestore.getInstance();
        dr = FirebaseDatabase.getInstance().getReference();
        dataget();

    }
    private void dataget(){
        dr.child("shop").child(shopName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ShopData shopData = dataSnapshot.getValue(ShopData.class);
//                Log.d(TAG, shopData.getClass().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        db.collection("shop")
//                .whereEqualTo("name", shopName)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                ShopData shopData = new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString());
//                                dataset(shopData);
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
        DocumentReference docRef = db.collection("shop").document(shopName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, document.getData().get("address").toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
//    private void dataset(ShopData shopData){
//        shopname.setText(shopData.getName());
//        ticket_num.setText(shopData.get);
//        time.setText();
//        waitnumber.setText(shopData.getWaitnumber());
//    }


}
