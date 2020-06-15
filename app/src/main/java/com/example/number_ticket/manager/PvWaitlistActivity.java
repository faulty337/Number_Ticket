package com.example.number_ticket.manager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.number_ticket.R;
import com.example.number_ticket.SelectUserActivity;
import com.example.number_ticket.adapter.WaitListAdapter;
import com.example.number_ticket.customer.CsRsvActivity;
import com.example.number_ticket.customer.CsTicketActivity;
import com.example.number_ticket.data.ServiceInfo;
import com.example.number_ticket.data.WaitingInfo;
import com.example.number_ticket.popup.AddServicePopup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.LogDescriptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class PvWaitlistActivity extends Activity {
    private static final String TAG = "PvWaitlistActivity";
    private FirebaseFirestore db;
    ArrayList<WaitingInfo> waitlist = new ArrayList<WaitingInfo>();;
    private WaitListAdapter waitListAdapter;
    private FirebaseUser user;
    private WaitingInfo waitingData;
    private int wait_number; //대기인원?
    private String start_time, username, shopName, customer, owner, shopname;
    private EditText client_name, time;
    private Waitingtimer waitingtimer;
    private boolean isBind;
//
//    ServiceConnection sconn = new ServiceConnection(){
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            Waitingtimer.MyBinder myBinder = (Waitingtimer.MyBinder) service;
//            waitingtimer = myBinder.getService();
//            isBind = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            waitingtimer = null;
//            isBind = false;
//        }
//    };
//    @Override
//    public void onInputedData(String client_name, String waiting_time) {
//        name = client_name;// 받아온 고객 이름
//        waitingtime = waiting_time; // 받아온 예상 대기시간
//        db = FirebaseFirestore.getInstance();
//
//        waitlist.add(new WaitingInfo(0, start_time, waiting_time, wait_number, shopname));
//        waitListAdapter.notifyDataSetChanged();
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pv_waitlist);
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        shopname = intent.getExtras().getString("name");
        start_time = timeset();
        nameset();
//        Intent intent2 = new Intent(getApplicationContext(), Waitingtimer.class);
//        bindService(intent2, sconn, Context.BIND_AUTO_CREATE);
        findViewById(R.id.bt_client_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = getLayoutInflater().inflate(R.layout.activity_pv_waitlist_add_popup, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(PvWaitlistActivity.this, R.style.MyAddAlertTheme);
                builder.setView(dialogView);
                builder.setPositiveButton("추가", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        addwait(dialogView);
                        Toast.makeText(getApplicationContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                //여기부터 버튼 가운데로 넣는 코드
                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                LinearLayout parent = (LinearLayout) positiveButton.getParent();
                parent.setGravity(Gravity.CENTER_HORIZONTAL);
                View leftSpacer = parent.getChildAt(1);
                leftSpacer.setVisibility(View.GONE);
                //여기까지
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                InitializeShopData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        this.InitializeShopData();

        ListView listView = (ListView)findViewById(R.id.pv_wait_listcontent);
        waitListAdapter = new WaitListAdapter(this,waitlist, shopname);
        waitListAdapter.notifyDataSetChanged();

        listView.setAdapter(waitListAdapter);

        findViewById(R.id.bt_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoHome();
            }
        });
    }

    private String timeset() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formatDate = sdfNow.format(date);
        return formatDate;
    }

    private void addwait(View view){
        client_name = view.findViewById(R.id.et_client_name);
        time = view.findViewById(R.id.et_service_time);

        db = FirebaseFirestore.getInstance();
        customer = user.getEmail();
        waitingData = new WaitingInfo(0, start_time, time.getText().toString(), 0, shopname);
        waitingData.setEmail(user.getEmail());
        waitingData.setName(client_name.getText().toString());// 대기자 이름을 넣어주어야할거 같은데 안들어간다
        db.collection("shop").document(shopname).collection("waitinglist").document(customer).set(waitingData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "끼요오오오옷");
            }
        });

//        DialogFragment Fragment = new AddServicePopup();
//        Fragment.show(getSupportFragmentManager(), "dialog");
    }

    private void GotoHome() {
        Intent intent = new Intent(PvWaitlistActivity.this, SelectUserActivity.class);
        startActivity(intent);
    }

    private void nameset(){
        db.collection("users").document(user.getEmail())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException e) {
                        username = document.getData().get("name").toString();
                    }
                });
    }

    private void InitializeShopData() {
        waitlist.clear();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("shop").document(shopname).collection("waitinglist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                WaitingInfo waitingInfo = new WaitingInfo(Integer.parseInt(document.get("ticket_number").toString()), document.get("time").toString(), document.get("waitingtime").toString(), Integer.parseInt(document.get("waiting_number").toString()), document.get("shopname").toString());
                                waitingInfo.setEmail(document.get("email").toString());
                                waitingInfo.setService_total(Integer.parseInt(document.get("service_total").toString()));
                                waitingInfo.setName(document.get("name").toString());
                                waitingInfo.setOnoff(Boolean.valueOf(document.get("onoff").toString()).booleanValue());
                                waitlist.add(waitingInfo);
                            }
                            Collections.reverse(waitlist);
                            waitListAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}
