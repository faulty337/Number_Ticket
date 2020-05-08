package com.example.number_ticket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AddShop extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.addservice);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layoutInflater.inflate(R.layout.activity_add_shop_addservice, linearLayout, true);

        Intent intent = getIntent();

       findViewById(R.id.bt_pv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MyStartActivity(ShopList.class);
            }

        });


    }

    private void MyStartActivity(Class go_to){
        Intent intent = new Intent(AddShop.this, go_to);
        startActivity(intent);
    }



}
