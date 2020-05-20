package com.example.number_ticket.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.number_ticket.R;

public class PvActActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pv_act);

        Intent intent = getIntent();
        final String shopname = intent.getExtras().getString("name");

        findViewById(R.id.bt_pv_waitlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyStartActivity(PvWaitlistActivity.class, shopname);
            }
        });

    }

    private void MyStartActivity(Class go_to, String shopname){
        Intent intent = new Intent(PvActActivity.this, go_to);
        intent.putExtra("name", shopname);
        startActivity(intent);
    }//화면전환

}
