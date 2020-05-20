package com.example.number_ticket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.number_ticket.R;
import com.example.number_ticket.data.WaitingInfo;
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
    ArrayList<WaitingInfo> sample;
    private ImageButton onoff;
    private TextView waittime;

    public WaitListAdapter(Context context, ArrayList<WaitingInfo> data){
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
        View view = mLayoutInflater.inflate(R.layout.activity_pv_waitlist_content, null);
        TextView number = (TextView)view.findViewById(R.id.cs_number);
        TextView name = (TextView)view.findViewById(R.id.cs_name);
        waittime = (TextView)view.findViewById(R.id.cs_waittime);
        onoff = (ImageButton)view.findViewById(R.id.bt_onoff);


        onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onoff.setImageResource(R.drawable.onbt);
                waittime.setText("이용중");
                onoff.setEnabled(false);
            }
        });
        name.setText(sample.get(position).getName());
        number.setText(sample.get(position).getTicket_number()+"");
        waittime.setText(sample.get(position).getWaitingtime());
        Log.d(TAG, "getView: ddd");

        return view;
    }

}
