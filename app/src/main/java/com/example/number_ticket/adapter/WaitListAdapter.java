package com.example.number_ticket.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.number_ticket.R;
import com.example.number_ticket.data.ServiceInfo;
import com.example.number_ticket.data.WaitingInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class WaitListAdapter extends BaseAdapter {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<WaitingInfo> sample;
    private ImageButton onoff;
    private TextView waittime;
    private ImageButton cancel;
    private String shopname;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    CountDownTimer timer;
    Random random = new Random();

    public WaitListAdapter(Context context, ArrayList<WaitingInfo> data, String shopname){
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.shopname = shopname;
    }

    @Override
    public int getCount() { return sample.size(); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public Object getItem(int position) { return sample.get(position); }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = mLayoutInflater.inflate(R.layout.activity_pv_waitlist_content, null);
        TextView number = (TextView)view.findViewById(R.id.cs_number);
        TextView name = (TextView)view.findViewById(R.id.cs_name);
        waittime = (TextView)view.findViewById(R.id.cs_waittime);
        onoff = (ImageButton)view.findViewById(R.id.bt_onoff);
        cancel = view.findViewById(R.id.bt_pv_waitcancle);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DocumentReference docRef = db.collection("shop").document(shopname);
                docRef.collection("waitinglist").document(sample.get(position).getEmail())
                        .delete();
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                        docRef.update("waitnumber", document.get("waitnumber").toString());
                    }
                });
            }
        });
        onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceadd();
                db.collection("shop").document(sample.get(position).getShopname()).update("onof", true);
                onoff.setImageResource(R.drawable.onbt);
                waittime.setText("이용중");
                onoff.setEnabled(false);
                timer = new CountDownTimer(sample.get(position).getService_total() * 60000, 60000){
                    @Override
                    public void onTick(long millisUntilFinished) {
                        db.collection("shop").document(shopname).collection("waitinglist").document(sample.get(position).getEmail()).update("service_total", sample.get(position).getService_total()-1);
                        sample.get(position).setService_total(sample.get(position).getService_total()-1);
                        Log.d(TAG, String.valueOf(sample.get(position).getService_total()));
                    }

                    @Override
                    public void onFinish() {

                    }
                };
                timer.start();


            }
        });
        name.setText(sample.get(position).getName());
        number.setText(sample.get(position).getTicket_number()+"");
        waittime.setText(sample.get(position).getWaitingtime());

        return view;
    }
    private void serviceadd(){
        db.collection("shop").document(shopname).collection("service")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        ArrayList<ServiceInfo> menu = new ArrayList<>();
                        int total = 0;
                        for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                            Boolean add = random.nextBoolean();
                            Log.d(TAG, add.toString());
                            if(add){
                                ServiceInfo serviceInfo = new ServiceInfo(document.getData().get("service").toString(), Integer.parseInt(document.getData().get("time").toString()));
                                menu.add(serviceInfo);
                                total += Integer.parseInt(document.getData().get("time").toString());
                            }
                        }
                        customer_service_update(menu, total);
                    }
                });
    }

    private void customer_service_update(ArrayList<ServiceInfo> menu, int total){
        Log.d(TAG, "메뉴 : " + menu.toString());
        DocumentReference waitRef = db.collection("shop").document(shopname).collection("waitinglist").document(user.getEmail());
        waitRef
                .update("menu", menu);
        waitRef
                .update("service_total", total);
        waitRef
                .update("personnel", random.nextInt(5)+1);
    }

}
