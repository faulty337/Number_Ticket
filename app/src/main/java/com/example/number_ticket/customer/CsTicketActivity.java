package com.example.number_ticket.customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CsTicketActivity extends AppCompatActivity {
    private static final String TAG = "CsTicketActivity";
    private String shopName; //매장 이름
    private int wait_number; //대기인원?
    private int ticket_number; //번호표 번호
    private String start_time; //발급 시간
    private String customer;
    private FirebaseFirestore db;
    private TextView ticket_num;
    private TextView shopname;
    private TextView time;
    private TextView waitnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs_ticket);
        Intent intent = getIntent();
        start_time = timeset();
        shopName = intent.getExtras().getString("name");
        db = FirebaseFirestore.getInstance();
        ticket_num = findViewById(R.id.ticket_num);
        shopname = findViewById(R.id.ticket_num);
        time = findViewById(R.id.time);
        waitnumber = findViewById(R.id.cs_waitnumber);
        dataget();

    }


    private String timeset() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formatDate = sdfNow.format(date);
        return formatDate;
    }

    private void dataget(){
        DocumentReference docRef = db.collection("shop").document(shopName);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        wait_number = Integer.parseInt(document.getData().get("waitnumber").toString());
                        ticketNumber_get();
                        waitinglistset();
                        Log.d(TAG, "됬냐?");
                        dataset();
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    private void dataset(){
        shopname.setText(shopName);
        ticket_num.setText(ticket_number+"");
        time.setText(start_time);
        waitnumber.setText(wait_number+"");
    }
    private void ticketNumber_get(){
        Query docRef = db.collection("shop").document(shopName).collection("waitinglist").orderBy("tel_number").limit(1);
        if(docRef != null){
            docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ticket_number = Integer.parseInt(document.getData().get("ticketNumber").toString())+ 1;
                            Log.d(TAG, ticket_number+"aaa");
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
        }else{
            ticket_number = 1;
        }
    }
    private void waitinglistset() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        customer = user.getUid();
        waitingInfo waitingData = new waitingInfo(ticket_number, start_time, "aa", wait_number);
        db.collection("shop").document(shopName).collection("waitinglist").document(customer).set(waitingData);
    }

}
