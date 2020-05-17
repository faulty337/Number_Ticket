package com.example.number_ticket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.number_ticket.R;
import com.example.number_ticket.data.waitingInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class WaitListAdapter extends BaseAdapter {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<waitingInfo> sample;
    private String username;

    public WaitListAdapter(Context context, ArrayList<waitingInfo> data){
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() { return sample.size(); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public Object getItem(int position) { return sample.get(position); }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        getname(sample.get(position).getUID());
        View view = mLayoutInflater.inflate(R.layout.activity_pv_waitlist_content, null);
        TextView number = (TextView)view.findViewById(R.id.cs_number);
        TextView name = (TextView)view.findViewById(R.id.cs_name);
        TextView waittime = (TextView)view.findViewById(R.id.cs_waittime);
        
        number.setText(sample.get(position).getWaiting_number());
        waittime.setText(sample.get(position).getWaitingtime());
        Log.d(TAG, "getView: ddd");
        name.setText(username);
        return view;
    }

    public void getname(String Uid){
        DocumentReference docRef = db.collection("users").document(Uid);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        username = document.getData().get("name").toString();
                        Log.d(TAG, "aaaa");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}
