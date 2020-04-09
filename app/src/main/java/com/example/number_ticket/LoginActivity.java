package com.example.number_ticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.bt_login).setOnClickListener(onClickListener);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.bt_goto_sign_up).setOnClickListener(onClickListener);
        findViewById(R.id.bt_goto_passwordreset).setOnClickListener(onClickListener);


    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_login:
                    login();
                    break;
                case R.id.bt_goto_sign_up:
                    myStartActivity(SignUpActivity.class);
                    break;
                case R.id.bt_goto_passwordreset:
                    myStartActivity(PasswordResetActivity.class);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    private void login(){
        String email = ((EditText)findViewById(R.id.et_email)).getText().toString();
        String password = ((EditText)findViewById(R.id.et_pw)).getText().toString();

        if(email.length() > 0 && password.length() > 0){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                openToast("로그인에 성공하였습니다.");
                                myStartActivity(SelectUserActivity.class);
                            } else {
                                if (task.getException() != null)
                                    // If sign in fails, display a message to the user.
                                    openToast(task.getException().toString() + "\n" + "로그인에 실패했습니다.");
                            }
                        }
                    });
        }else{
            openToast("이메일 혹은 비밀번호를 입력해주세요");
        }


    }
    private void myStartActivity(Class ac){
        Intent intent = new Intent(this, ac);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void openToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
