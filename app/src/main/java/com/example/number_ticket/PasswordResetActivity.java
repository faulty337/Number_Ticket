package com.example.number_ticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordResetActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.bt_send).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_send:
                    send();
                    break;
            }
        }
    };


    private void send(){
        String email = ((EditText)findViewById(R.id.et_email)).getText().toString();


        if(email.length() > 0){
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                            }
                        }
                    });
        }else{
            openToast("이메일을 입력해주세요");
        }


    }
    private void gotosignup(){
        Intent intent = new Intent(PasswordResetActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
    private void openToast(String text){
        Toast.makeText(PasswordResetActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
