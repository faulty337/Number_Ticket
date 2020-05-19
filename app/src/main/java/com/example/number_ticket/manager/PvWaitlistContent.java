package com.example.number_ticket.manager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.number_ticket.R;

public class PvWaitlistContent extends Activity {
    private ImageButton onoff;
    private TextView waittime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pv_waitlist_content);

        onoff = (ImageButton)findViewById(R.id.bt_onoff);
        waittime = (TextView)findViewById(R.id.cs_waittime);
    }

    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_onoff:
                onoff.setImageResource(R.drawable.ONbt);
                waittime.setText("이용중");
                onoff.setEnabled(false);
                break;
        }
    }
}
