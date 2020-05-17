package com.example.number_ticket.manager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.number_ticket.R;
import com.example.number_ticket.adapter.ShopListAdapter;
import com.example.number_ticket.adapter.WaitListAdapter;
import com.example.number_ticket.data.ShopData;
import com.example.number_ticket.data.waitingInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PvWaitlistActivity extends Activity {
    private static final String TAG = "PvWaitlistActivity";
    private FirebaseFirestore db;
    ArrayList<waitingInfo> waitlist = new ArrayList<waitingInfo>();;
    private WaitListAdapter waitListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pv_waitlist);
        db = FirebaseFirestore.getInstance();

        this.InitializeShopData();

        ListView listView = (ListView)findViewById(R.id.pv_wait_listcontent);
        waitListAdapter = new WaitListAdapter(this,waitlist);
        waitListAdapter.notifyDataSetChanged();

        listView.setAdapter(waitListAdapter);

    }

    private void InitializeShopData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("shop")
                .whereEqualTo("owner", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                waitlist.add(new waitingInfo(Integer.parseInt(document.get("ticket_number").toString()), document.get("time").toString(), document.get("waitingtime").toString(), Integer.parseInt(document.get("waiting_number").toString())));
                                Log.d(TAG, document.get("code_use").getClass().getClass().toString());
                            }
                            waitListAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
