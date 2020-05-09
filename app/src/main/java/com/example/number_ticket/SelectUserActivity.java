package com.example.number_ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.number_ticket.customer.SearchShopList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class SelectUserActivity extends AppCompatActivity {

    private String name;
    private  String UID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            for (UserInfo profile : user.getProviderData()) {
                String name = profile.getDisplayName();
                Uri photoUrl = profile.getPhotoUrl();
            }
        }
        findViewById(R.id.bt_logout).setOnClickListener(onClickListener);
        findViewById(R.id.bt_manager).setOnClickListener(onClickListener);
        findViewById(R.id.bt_guest).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_logout:
                    FirebaseAuth.getInstance().signOut();
                    myStartActivity(LoginActivity.class);
                    break;
                case R.id.bt_guest:
                    myStartActivity(SearchShopList.class);
                    break;
                case R.id.bt_manager:

                    break;
            }
        }
    };
    private void myStartActivity(Class ac){
        Intent intent = new Intent(this, ac);
        startActivity(intent);
    }
}
