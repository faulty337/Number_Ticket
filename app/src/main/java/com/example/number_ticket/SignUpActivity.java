package com.example.number_ticket;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private String password_check;
    private String name;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViewById(R.id.bt_login).setOnClickListener(onClickListener);
        mAuth = FirebaseAuth.getInstance();
        email = new String();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_login:
                    signup();
                    break;
                case R.id.bt_back:
                    onBackPressed();
                    break;
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    private void signup(){
        email = ((EditText)findViewById(R.id.et_email)).getText().toString();
        password = ((EditText)findViewById(R.id.et_pw)).getText().toString();
        password_check = ((EditText)findViewById(R.id.et_pw_check)).getText().toString();
        name = ((EditText)findViewById(R.id.et_name)).getText().toString();
        phoneNumber = ((EditText)findViewById(R.id.et_phoneNumber)).getText().toString();

        if(email.length() > 0 && password.length() > 0 && password_check.length() > 0 && name.length() > 0 && phoneNumber.length() > 0) { //이메일과 패스워드 입력 여부
            if (password.equals(password_check)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    final UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name)
                                            .build();

                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        openToast("사용자업데이트에 성공하엿습니다.");
                                                    }
                                                }
                                            });
                                } else {
                                    if (task.getException() != null)
                                        // If sign in fails, display a message to the user.
                                        openToast(task.getException().toString() + "\n" + "회원가입에 실패했습니다.");
                                    //updateUI(null); //실패했을때
                                }
                                // ...
                            }
                        });
                profileUpdate();
                gotoLogin();
            } else {
                openToast("비밀번호가 일치하지 않습니다.");
            }
        }else{
            openToast("빈칸을 채워주세요");
        }


    }

    private  void profileUpdate(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        UserInfo userInfo = new UserInfo(name, email, phoneNumber);
        db.collection("users").document(user.getUid()).set(userInfo);
        Log.i(TAG, "asdaa");
    }

    private void openToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    private void gotoLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
