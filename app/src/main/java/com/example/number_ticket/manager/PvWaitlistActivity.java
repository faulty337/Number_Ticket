package com.example.number_ticket.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.number_ticket.R;
import com.example.number_ticket.adapter.WaitListAdapter;
import com.example.number_ticket.data.WaitingInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.LogDescriptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class PvWaitlistActivity extends Activity {
    private static final String TAG = "PvWaitlistActivity";
    private FirebaseFirestore db;
    ArrayList<WaitingInfo> waitlist = new ArrayList<WaitingInfo>();;
    private WaitListAdapter waitListAdapter;
    private String shopname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pv_waitlist);
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        shopname = intent.getExtras().getString("name");
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
        waitListAdapter = new WaitListAdapter(this,waitlist);
        waitListAdapter.notifyDataSetChanged();

        listView.setAdapter(waitListAdapter);

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
                                WaitingInfo waitingInfo = new WaitingInfo(Integer.parseInt(document.get("ticket_number").toString()), document.get("time").toString(), document.get("waitingtime").toString(), Integer.parseInt(document.get("waiting_number").toString()));
                                waitingInfo.setEmail(document.get("email").toString());
                                waitingInfo.setName(document.get("name").toString());
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
