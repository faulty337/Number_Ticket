package com.example.number_ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShopList extends AppCompatActivity {

    ArrayList<ShopData> shopDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        this.InitializeShopData();

        ListView listView = (ListView)findViewById(R.id.pv_info_listcontent);
        final SearchAdapter searchAdapter = new SearchAdapter(this,shopDataList);

        listView.setAdapter(searchAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),
                        searchAdapter.getItem(position).getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = getIntent();

        findViewById(R.id.bt_shopadd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyStartActivity(AddShop.class);
            }
        });//AddShop 이동


    }

    public void InitializeShopData()
    {
        shopDataList = new ArrayList<ShopData>();

//        movieDataList.add(new ShopData("cu제주대점", "064-123-4567", "편의", "대충제주대"));
//        movieDataList.add(new ShopData("김밥천국", "064-987-6543", "식당", "발할라"));
    }

    private void MyStartActivity(Class go_to){
        Intent intent = new Intent(ShopList.this, go_to);
        startActivity(intent);
    }

}