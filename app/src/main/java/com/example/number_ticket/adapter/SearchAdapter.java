package com.example.number_ticket.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.number_ticket.customer.CsRsvActivity;
import com.example.number_ticket.R;
import com.example.number_ticket.data.ShopData;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ShopData> sample;

    public SearchAdapter(Context context, ArrayList<ShopData> data) {
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
    public ShopData getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.activity_search_shop_list_content, null);

        TextView SName = (TextView)view.findViewById(R.id.shop_name);
        TextView SGroup = (TextView)view.findViewById(R.id.shop_type);
        TextView TelNumber = (TextView)view.findViewById(R.id.shop_tel_number);
        TextView Saddr = (TextView)view.findViewById(R.id.shop_addr);
        Button SRes = (Button)view.findViewById(R.id.bt_cs_rsv);

        SName.setText(sample.get(position).getName());
        SGroup.setText(sample.get(position).getType());
        TelNumber.setText(sample.get(position).getTel_number());
        Saddr.setText(sample.get(position).getAddress());
        SRes.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                String shopName = sample.get(position).getName();
                Intent intent = new Intent(mContext.getApplicationContext(), CsRsvActivity.class);
                intent.putExtra("name", shopName);
                mContext.startActivity(intent);
            }
        });


        return view;
    }
}