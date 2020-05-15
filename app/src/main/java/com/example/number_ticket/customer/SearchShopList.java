package com.example.number_ticket.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.number_ticket.R;
import com.example.number_ticket.adapter.SearchAdapter;
import com.example.number_ticket.data.ShopData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchShopList extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private FirebaseFirestore db;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private ListView listView;
    private SearchAdapter searchAdapter;
    private SearchView searchView;

    Spinner spinner;
    ArrayList<ShopData> shopDataList = new ArrayList<ShopData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shop_list);

        spinner = findViewById(R.id.spinner);

        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this, R.array.store_search, android.R.layout.simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(monthAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            } //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됬는지 알 수 있습니다.
            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        ListView listView = (ListView)findViewById(R.id.shop_search_listView);
        db = FirebaseFirestore.getInstance();
        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query == ""){
                    db.collection("shop")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            shopDataList.add(new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString(),document.get("code").toString(),Boolean.valueOf(document.get("code_use").toString()),document.get("owner").toString()));
                                            Log.d(TAG, document.get("code_use").getClass().getClass().toString());
                                        }
                                        searchAdapter.notifyDataSetChanged();
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }else{
                    shopDataList.clear();
                    db.collection("shop")
                            .whereEqualTo("name", query)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            shopDataList.add(new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString(),document.get("code").toString(),Boolean.valueOf(document.get("code_use").toString()),document.get("owner").toString()));
                                            Log.d(TAG, document.get("code_use").getClass().getClass().toString());
                                        }

                                        searchAdapter.notifyDataSetChanged();
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText == ""){
                    db.collection("shop")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            shopDataList.add(new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString(),document.get("code").toString(),Boolean.valueOf(document.get("code_use").toString()),document.get("owner").toString()));
                                            Log.d(TAG, document.get("code_use").getClass().getClass().toString());
                                        }
                                        searchAdapter.notifyDataSetChanged();
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }else{
                    shopDataList.clear();
                    db.collection("shop")
                            .whereEqualTo("name", newText)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            shopDataList.add(new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString(),document.get("code").toString(),Boolean.valueOf(document.get("code_use").toString()),document.get("owner").toString()));
                                            Log.d(TAG, document.get("code_use").getClass().getClass().toString());
                                        }
                                        searchAdapter.notifyDataSetChanged();
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
                return false;
            }
        });
        searchAdapter = new SearchAdapter(this, shopDataList);

        listView.setAdapter(searchAdapter);
        initDatabase();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "아 쉬바좀");
                Toast.makeText(getApplicationContext(),
                        searchAdapter.getItem(position).getName(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initDatabase() {
        db.collection("shop")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                shopDataList.add(new ShopData(document.get("name").toString(), document.get("tel_number").toString(), document.get("type").toString(), document.get("address").toString(),document.get("code").toString(),Boolean.valueOf(document.get("code_use").toString()),document.get("owner").toString()));
                                Log.d(TAG, document.get("code_use").getClass().getClass().toString());
                            }
                            searchAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}