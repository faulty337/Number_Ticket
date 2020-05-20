package com.example.number_ticket.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.number_ticket.R;
import com.example.number_ticket.customer.CsRsvActivity;
import com.example.number_ticket.data.ShopData;
import com.example.number_ticket.manager.AddShop;
import com.example.number_ticket.manager.PvActActivity;
import com.example.number_ticket.manager.ShopList;

import java.util.ArrayList;

public class ShopListAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ShopData> sample;

    public ShopListAdapter(Context context, ArrayList<ShopData> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() { return sample.size(); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public ShopData getItem(int position) { return sample.get(position); }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.activity_shop_list_content, null);

        TextView SName = (TextView)view.findViewById(R.id.pv_info_sname);
        TextView SGroup = (TextView)view.findViewById(R.id.pv_info_sgroup);
        TextView TelNumber = (TextView)view.findViewById(R.id.pv_info_stelnumber);
        TextView Saddr = (TextView)view.findViewById(R.id.pv_info_saddr);
        Button SRes1 = (Button)view.findViewById(R.id.pv_info_edit);
        Button SRes2 = (Button)view.findViewById(R.id.pv_info_del);

        SName.setText(sample.get(position).getName());
        SGroup.setText(sample.get(position).getType());
        TelNumber.setText(sample.get(position).getTel_number());
        Saddr.setText(sample.get(position).getAddress());
        SName.setOnClickListener(new TextView.OnClickListener(){
            public void onClick(View v){
                String shopName = sample.get(position).getName();
                Intent intent = new Intent(mContext.getApplicationContext(), PvActActivity.class);
                intent.putExtra("name", shopName);
                mContext.startActivity(intent);
            }
        });
        SRes1.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                String shopName = sample.get(position).getName();
                Intent intent = new Intent(mContext.getApplicationContext(), AddShop.class);
                intent.putExtra("name", shopName);
                mContext.startActivity(intent);
            }
        });
        SRes2.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                String shopName = sample.get(position).getName();
                Intent intent = new Intent(mContext.getApplicationContext(), AddShop.class);
                intent.putExtra("name", shopName);
                mContext.startActivity(intent);
            }
        });

        return view;
    }
}
