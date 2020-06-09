package com.example.number_ticket.customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.number_ticket.MainActivity;
import com.example.number_ticket.R;
import com.example.number_ticket.data.ShopData;
import com.example.number_ticket.popup.AddServicePopup;
import com.example.number_ticket.popup.CodeCheck;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

//View dialogView = getLayoutInflater().inflate(R.layout.activity_code_check, null);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(CsRsvActivity.this, R.style.MySaveAlertTheme);
//        builder.setView(dialogView);
//        builder.setPositiveButton("입력", new DialogInterface.OnClickListener(){
//@Override
//public void onClick(DialogInterface dialog, int id)
//        {
//        EditText code = (EditText)((AlertDialog)dialog).findViewById(R.id.et_codecheck);
//        }
//        });
//
//        builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
//@Override
//public void onClick(DialogInterface dialog, int id)
//        {
//        dialog.dismiss();
//        }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();

public class CsRsvActivity extends AppCompatActivity implements CodeCheck.OnCompleteListenner
{
    private static final String TAG = "CsRsvActivity";
    private String shopname, waitingTime;
    private String code, code_check = "";
    private Boolean shopuse;
    private int wait_number;
    private FirebaseFirestore db;
    private TextView pv_info_sname, pv_waittime;
    private TextView pv_waitnumber;
    private Intent intent;
    private int ticket_number, waitingtime, waitNumber, space, ATime, customertime;
    private ShopData shopData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs_rsv);
        Intent intent1 = getIntent();
        intent = new Intent(CsRsvActivity.this, CsTicketActivity.class);
        Button giveticket = findViewById(R.id.bt_give_number);
        shopname = intent1.getExtras().getString("name");
        pv_info_sname = findViewById(R.id.pv_info_sname);
        pv_waitnumber = findViewById(R.id.pv_waitnumber);
        pv_waittime = findViewById(R.id.pv_waittime);
        db = FirebaseFirestore.getInstance();

        readshop(shopname);
        waitnumber_count();

        giveticket.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(shopuse){
                    rsv_check();
                }else{
                    dialog_set();
                }
            }
        });


    }

//    private String waitingtimeSet() {
//        waitNumber = shopData.getWaitnumber();
//        space = shopData.getSpace_count();
//        ATime = shopData.getWaitingtime();
//        db.collection("shop").document(shopname).collection("waitinglist").orderBy("ticket_number").limit(waitNumber % space).orderBy("ticket_number", Query.Direction.DESCENDING).limit(1)
//            .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                @Override
//                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
//                        customertime = Integer.parseInt(document.get("service_total").toString());
//                    }
//                    waitingtime = waitNumber / space * ATime + customertime;
//                    pv_waittime.setText(waitingtime+"");
//                }
//            });
//        return String.valueOf(waitingtime);
//    }

    @Override
    public void onInputedData(String code) {
        this.code = code;
        Log.d(TAG, code + "입력 코드");
        Log.d(TAG, code_check + "코드 체크");
        if(code.equals(code_check)){
            gotoTicket();
        }else{
            code_false_action();
        }
    }

    private void gotoTicket(){
        intent.putExtra("name", shopname);
        shopData.setWaitnumber(wait_number);
        shopUpdate();
        ticketNumber_get();
    }

    private void ticketNumber_get(){
        Query docRef = db.collection("shop").document(shopname).collection("waitinglist").orderBy("waiting_number").limit(1);
        if(docRef != null){
            docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if(task.getResult().size() !=0){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ticket_number = Integer.parseInt(document.getData().get("ticket_number").toString())+ 1;
                                intent.putExtra("ticket", ticket_number+"");
                                startActivity(intent);
                            }
                        }else{
                            Log.d(TAG, "비어있음");
                            ticket_number = 1;
                            intent.putExtra("ticket", ticket_number+"");
                            Log.d(TAG, ticket_number+"");
                            startActivity(intent);
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
        }else{
            Log.d(TAG, "이게 비었다고??");
        }
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
                        code_check = shopData.getCode();
                        shopuse = shopData.getUse();
                        pv_info_sname.setText(shopData.getName());
//                        waitingtimeSet();

                    }
                });
    }
    private void waitnumber_count(){
        db.collection("shop")
                .document(shopname)
                .collection("waitinglist").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot query, @Nullable FirebaseFirestoreException e) {
                wait_number = query.size();
                pv_waitnumber.setText(wait_number + " 명");
                Log.d(TAG, wait_number+"Aaa");
                db.collection("shop").document(shopname).update("waitnumber", wait_number);
            }
        });
    }

    private void shopUpdate() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("shop").document(shopname).set(shopData);
    }
    private void dialog_set(){
        View dialogView = getLayoutInflater().inflate(R.layout.activity_cant_rsv, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(CsRsvActivity.this, R.style.MySaveAlertTheme);
        builder.setView(dialogView);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void rsv_check(){
        View dialogView = getLayoutInflater().inflate(R.layout.activity_cs_rsv_check, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(CsRsvActivity.this, R.style.MySaveAlertTheme);
        builder.setView(dialogView);
        builder.setPositiveButton("예약", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                code_check();
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

    private void code_check(){
        if(shopData.getCode_use()){
            DialogFragment Fragment = new CodeCheck();
            Fragment.show(getSupportFragmentManager(), "dialog");

        }else{
            gotoTicket();
        }
    }
    private void code_false_action(){
        View dialogView = getLayoutInflater().inflate(R.layout.activity_code_false, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(CsRsvActivity.this, R.style.MySaveAlertTheme);
        builder.setView(dialogView);

        builder.setNegativeButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
