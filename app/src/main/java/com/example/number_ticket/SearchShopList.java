package com.example.number_ticket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchShopList extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private FirebaseFirestore db;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private ListView listView;
    private SearchAdapter searchAdapter;
    private SearchView searchView;

    ArrayList<ShopData> shopDataList = new ArrayList<ShopData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shop_list);

        ListView listView = (ListView)findViewById(R.id.shop_search_listView);
        db = FirebaseFirestore.getInstance();
        searchView = findViewById(R.id.searchView);

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if(query == ""){
//                    db.collection("shop")
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            shopDataList.add(new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString()));
//                                        }
//                                        searchAdapter.notifyDataSetChanged();
//                                    } else {
//                                        Log.d(TAG, "Error getting documents: ", task.getException());
//                                    }
//                                }
//                            });
//                }else{
//                    shopDataList.clear();
//                    db.collection("shop")
//                            .whereEqualTo("name", query)
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            shopDataList.add(new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString()));
//                                        }
//                                        searchAdapter.notifyDataSetChanged();
//                                    } else {
//                                        Log.d(TAG, "Error getting documents: ", task.getException());
//                                    }
//                                }
//                            });
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if(newText == ""){
//                    db.collection("shop")
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            shopDataList.add(new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString()));
//                                        }
//                                        searchAdapter.notifyDataSetChanged();
//                                    } else {
//                                        Log.d(TAG, "Error getting documents: ", task.getException());
//                                    }
//                                }
//                            });
//                }else{
//                    shopDataList.clear();
//                    db.collection("shop")
//                            .whereEqualTo("name", newText)
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            shopDataList.add(new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString()));
//                                        }
//                                        searchAdapter.notifyDataSetChanged();
//                                    } else {
//                                        Log.d(TAG, "Error getting documents: ", task.getException());
//                                    }
//                                }
//                            });
//                }
//                return false;
//            }
//        });
//        searchAdapter = new SearchAdapter(this, shopDataList);
//
//        listView.setAdapter(searchAdapter);
//        initDatabase();
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),
//                        searchAdapter.getItem(position).getName(),
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//    private void initDatabase() {
//        db.collection("shop")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                shopDataList.add(new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString()));
//                            }
//                            searchAdapter.notifyDataSetChanged();
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
    }
}