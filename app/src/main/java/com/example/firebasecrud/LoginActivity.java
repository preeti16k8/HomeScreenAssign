package com.example.firebasecrud;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button btn_login;
    EditText et_email,et_pwd;
    private ProgressDialog mLoginProgess;
    private FirebaseAuth mAuth;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login=(Button)findViewById(R.id.btn_login);
        et_email=(EditText)findViewById(R.id.et_email);
        et_pwd=(EditText)findViewById(R.id.et_pwd);
        mLoginProgess=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=et_email.getText().toString();
                String password=et_pwd.getText().toString();
                if(!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)){
                    mLoginProgess.setTitle("Login In");
                    mLoginProgess.setMessage("Please wait while we check your credentials..");
                    mLoginProgess.setCanceledOnTouchOutside(false);
                    mLoginProgess.show();

                    loginuser(email,password);
                }

            }

            private void loginuser(String email, String password) {
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            mLoginProgess.dismiss();
                            Intent mainIntent=new Intent(LoginActivity.this,MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish(); //we dont want to come back to this activity on pressing back button
                        }
                        else{
                            mLoginProgess.hide();
                            Toast.makeText(LoginActivity.this, "Cannot log in ,please email and password and try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
