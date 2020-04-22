package com.example.number_ticket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

    ArrayList<ShopData> shopDataList = new ArrayList<ShopData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shop_list);

        ListView listView = (ListView)findViewById(R.id.shop_search_listView);
        db = FirebaseFirestore.getInstance();


        searchAdapter = new SearchAdapter(this, shopDataList);

        listView.setAdapter(searchAdapter);
        initDatabase();
    }
    private void initDatabase() {
        db.collection("shop")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
                            ? "Local" : "Server";

                    List<DocumentSnapshot> documents = snapshot.getDocuments();
                    if (documents != null) {
                        // 기존 데이터를 가지고 옴
                        for (DocumentSnapshot document : documents) {
                            Map<String, Object> tempMovies = document.getData();
                            Map<String, Double> movieInfo = new HashMap<>();
                            Log.d(TAG, document.get("name").toString());
                            shopDataList.add(new ShopData(tempMovies.get("name").toString(), tempMovies.get("tel_number").toString(), tempMovies.get("type").toString(), tempMovies.get("address").toString()));
                        }
                        searchAdapter.notifyDataSetChanged();
                    }
                    else {
//                        Toast.makeText(this, "데이터 베이스 이상", Toast.LENGTH_SHORT);
                    }
                }});
//        db = FirebaseFirestore.getInstance();
//        db.collection("shop")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getData().get("name"));
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
    }
}