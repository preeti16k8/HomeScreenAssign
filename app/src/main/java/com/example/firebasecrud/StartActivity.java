package com.example.firebasecrud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    Button btn_reg,btn_login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btn_reg=(Button)findViewById(R.id.btn_reg);
        btn_login=(Button)findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser=mAuth.getCurrentUser();

        if(currentUser!=null) {
            Intent loginIntent=new Intent(StartActivity.this,MainActivity.class);
            startActivity(loginIntent);

        }


            btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent=new Intent(StartActivity.this,LoginActivity.class);
                startActivity(loginIntent);

            }
        });
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent=new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(regIntent);

            }
        });
    }
}
