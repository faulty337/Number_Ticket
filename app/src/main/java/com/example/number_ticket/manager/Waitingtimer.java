package com.example.number_ticket.manager;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class Waitingtimer extends Service {
    private IBinder mlBinder = new MyBinder();

    class MyBinder extends Binder {
        Waitingtimer getService(){
            return Waitingtimer.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mlBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


}
