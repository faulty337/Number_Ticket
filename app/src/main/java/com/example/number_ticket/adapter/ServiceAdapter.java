package com.example.number_ticket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.number_ticket.R;
import com.example.number_ticket.data.ServiceInfo;

import java.util.ArrayList;

public class ServiceAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ServiceInfo> sample;

    public ServiceAdapter(Context context, ArrayList<ServiceInfo> data) {
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
    public ServiceInfo getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.activity_add_shop_list_content, null);

        TextView name = view.findViewById(R.id.service_name);
        TextView time = view.findViewById(R.id.service_time);

        name.setText(sample.get(position).getService());
        time.setText(sample.get(position).getTime());
        return view;
    }
}
