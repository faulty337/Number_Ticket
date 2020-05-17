package com.example.number_ticket.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.number_ticket.R;
import com.example.number_ticket.SelectUserActivity;
import com.example.number_ticket.adapter.SearchAdapter;
import com.example.number_ticket.adapter.ShopListAdapter;
import com.example.number_ticket.data.ShopData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShopList extends AppCompatActivity {
    private static final String TAG = "ShopListActivity";
    private FirebaseFirestore db;
    ArrayList<ShopData> shopDataList = new ArrayList<ShopData>();;
    private ShopListAdapter shopListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        db = FirebaseFirestore.getInstance();

        this.InitializeShopData();

        ListView listView = (ListView)findViewById(R.id.pv_info_listcontent);
        shopListAdapter = new ShopListAdapter(this,shopDataList);
        shopListAdapter.notifyDataSetChanged();

        listView.setAdapter(shopListAdapter);

        Intent intent = getIntent();//SelectUser -> ShopList

        Intent intent2 = getIntent();//Addshop -> ShopList

        findViewById(R.id.bt_shopadd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyStartActivity(AddShop.class);
            }
        });//AddShop 이동

    }

    public void InitializeShopData()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("shop")
                .whereEqualTo("owner", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                shopDataList.add(new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString(),document.get("code").toString(),Boolean.valueOf(document.get("code_use").toString()),document.get("owner").toString()));
                                Log.d(TAG, document.get("code_use").getClass().getClass().toString());
                            }
                            shopListAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void MyStartActivity(Class go_to){
        Intent intent = new Intent(ShopList.this, go_to);
        startActivity(intent);
    }

}