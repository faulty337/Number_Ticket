package com.example.number_ticket.manager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.number_ticket.R;

public class PvWaitlistContent extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pv_waitlist_content);
    }

    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_on:
                break;
        }
    }
}
