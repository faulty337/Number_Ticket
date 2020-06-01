package com.example.number_ticket.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.number_ticket.R;
import com.example.number_ticket.data.ShopData;
import com.example.number_ticket.manager.PvActActivity;
import com.example.number_ticket.manager.EditShop;
import com.example.number_ticket.manager.ShopList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;



public class ShopListAdapter extends BaseAdapter {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    Activity activity;
    ArrayList<ShopData> sample;

    public ShopListAdapter(Context context, ArrayList<ShopData> data, Activity activity) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.activity = activity;
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
                Intent intent = new Intent(mContext.getApplicationContext(), EditShop.class);
                intent.putExtra("name", shopName);
                mContext.startActivity(intent);
            }
        });
        SRes2.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                dialogset(sample.get(position).getName());
            }
        });

        return view;
    }


    private void dialogset(final String shopname){
//        DialogInterface.OnClickListener confirm = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                db.collection("shop").document(shopname)
//                        .delete()
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                notifyDataSetChanged();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("aaa", "onFailure: youfail");
//                            }
//                        });
//                db.collection("shop").document(shopname).collection("service")
//            }
//        };
//        DialogInterface.OnClickListener cancel = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//                dialog.dismiss();
//            }
//        };
//        new AlertDialog.Builder(activity, R.style.MyCancelAlertTheme)
//                .setTitle("삭제하시겠습니까?")
//                .setPositiveButton("삭제",confirm)
//                .setNegativeButton("취소", cancel)
//                .show();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View dialogView = inflater.inflate(R.layout.activity_delete_shop_check, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyCancelAlertTheme);
        builder.setView(dialogView);
        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                db.collection("shop").document(shopname)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent intent = new Intent(mContext, ShopList.class);
                                mContext.startActivity(intent);
                                notifyDataSetChanged();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("aaa", "onFailure: youfail");
                            }
                        });
                db.collection("shop").document(shopname).collection("service");
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
