package com.example.number_ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShopList extends AppCompatActivity {

    ArrayList<ShopData> movieDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        this.InitializeMovieData();

        ListView listView = (ListView)findViewById(R.id.pv_info_listcontent);
        final SearchAdapter searchAdapter = new SearchAdapter(this,movieDataList);

        listView.setAdapter(searchAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),
                        searchAdapter.getItem(position).getName(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void InitializeMovieData()
    {
        movieDataList = new ArrayList<ShopData>();

        movieDataList.add(new ShopData("김밥천국 00점", "064-000-0000", "분식", "제주도 00시 ---"));
        movieDataList.add(new ShopData("김밥천국 00점", "064-000-0000", "분식", "제주도 00시 ---"));
        movieDataList.add(new ShopData("김밥천국 00점", "064-000-0000", "분식", "제주도 00시 ---"));
    }
}