package com.example.number_ticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<SampleData> sample;

    public MyAdapter(Context context, ArrayList<SampleData> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public SampleData getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.activity_shop_list_content, null);

        TextView SName = (TextView)view.findViewById(R.id.pv_info_sname);
        TextView SGroup = (TextView)view.findViewById(R.id.pv_info_sgroup);
        TextView TelNumber = (TextView)view.findViewById(R.id.pv_info_stelnumber);
        TextView Saddr = (TextView)view.findViewById(R.id.pv_info_saddr);

        SName.setText(sample.get(position).getSName());
        SGroup.setText(sample.get(position).getSGroup());
        TelNumber.setText(sample.get(position).getTelNumber());
        Saddr.setText(sample.get(position).getSaddr());

        return view;
    }
}