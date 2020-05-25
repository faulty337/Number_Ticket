package com.example.number_ticket.customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.number_ticket.R;
import com.example.number_ticket.data.WaitingInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CsTicketActivity extends AppCompatActivity {
    private static final String TAG = "CsTicketActivity";
    private String shopName; //매장 이름
    private int wait_number; //대기인원?
    private String start_time; //발급 시간
    private String customer;
    private String username;
    private FirebaseFirestore db;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private WaitingInfo waitingData;
    private TextView ticket_num;
    private TextView shopname;
    private TextView time;
    private TextView waitnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs_ticket);
        final Intent intent = getIntent();
        start_time = timeset();

        shopName = intent.getExtras().getString("name");
        final int ticket_number = Integer.parseInt(intent.getExtras().getString("ticket"));
        Log.d(TAG, ticket_number+"");
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        nameset(ticket_number);
        ticket_num = findViewById(R.id.ticket_num);
        ticket_num.setText(ticket_number+"");
        shopname = findViewById(R.id.pv_info_sname);
        shopname.setText(shopName);
        time = findViewById(R.id.time);
        time.setText(start_time);
        waitnumber = findViewById(R.id.cs_waitnumber);
        Button cancel = findViewById(R.id.bt_numcancel);
        cancel.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.activity_cs_ticket_cancel_check, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(CsTicketActivity.this, R.style.MyCancelAlertTheme);
                builder.setView(dialogView);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        db.collection("shop").document(shopName).collection("waitinglist").document(user.getEmail())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent intent1 = new Intent(CsTicketActivity.this, CsRsvActivity.class);
                                        intent1.putExtra("name", shopName);
                                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent1);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });
                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
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
        db.collection("shop").document(shopName).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException e) {
                wait_number = Integer.parseInt(document.getData().get("waitnumber").toString());
                waitnumber_count();
            }
        });
    }

    private void waitinglistset(int ticket_n) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        customer = user.getEmail();
        waitingData = new WaitingInfo(ticket_n, start_time, "aa", wait_number, shopName);
        waitingData.setEmail(user.getEmail());
        waitingData.setName(username);
        db.collection("shop").document(shopName).collection("waitinglist").document(customer).set(waitingData);
    }
    private void waitnumber_count(){
        db.collection("shop")
                .document(shopName)
                .collection("waitinglist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            wait_number = task.getResult().size();
                            waitnumber.setText(wait_number + " 명");
                        }
                    }
                });
    }
    private void nameset(final int ticket_number){
        db.collection("users").document(user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        if(document.exists()){
                            username = document.getData().get("name").toString();
                            waitinglistset(ticket_number);
                            waitnumber_count();
                        }
                    }
                });
    }
    private void Alarmset(){
        db.collection("shop").document(shopName).collection("waiting").document(user.getEmail())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException e) {
                        if(document.get("alarmset").toString() == "true"){
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            ringtone.play();
                        }
                    }
                });

    }

}
