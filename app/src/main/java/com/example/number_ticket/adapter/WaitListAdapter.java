package com.example.number_ticket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.number_ticket.R;
import com.example.number_ticket.data.waitingInfo;

import java.util.ArrayList;

public class WaitListAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<waitingInfo> sample;

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
        View view = mLayoutInflater.inflate(R.layout.activity_pv_waitlist_content, null);
        TextView number = (TextView)view.findViewById(R.id.cs_number);
        TextView name = (TextView)view.findViewById(R.id.cs_name);
        TextView waittime = (TextView)view.findViewById(R.id.cs_waittime);


        number.setText(sample.get(position).getWaiting_number());
        //name 은 users에 있는데 이거 어떻게 가져오냐아아아
        waittime.setText(sample.get(position).getWaitingtime());

        return view;
    }
}
