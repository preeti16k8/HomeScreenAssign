package com.example.firebasecrud;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText et_name,et_email,et_pwd;
    Button btn_createacnt;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private ProgressDialog mRegProgess;
    private DatabaseReference mDatabaseReference;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_name=(EditText)findViewById(R.id.et_name);
        et_email=(EditText)findViewById(R.id.et_email);
        et_pwd=(EditText)findViewById(R.id.et_pwd);
        btn_createacnt=(Button)findViewById(R.id.btn_createacnt);
        mRegProgess=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        btn_createacnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String displayName=et_name.getText().toString();
                String email=et_email.getText().toString();
                String password=et_pwd.getText().toString();

                if (TextUtils.isEmpty(displayName)) {
                    et_name.setError("Please enter username");
                    et_name.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    et_email.setError("Please enter your email");
                    et_email.requestFocus();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    et_email.setError("Enter a valid email");
                    et_email.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    et_pwd.setError("Enter a password");
                    et_pwd.requestFocus();
                    return;
                }
                if(password.length()<6){
                    et_pwd.setError("Passord length must be equal or greater than 6");
                    et_pwd.requestFocus();
                    return;
                }

              //  if(!TextUtils.isEmpty(displayName)||!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)){
                    mRegProgess.setTitle("Registering User..");
                    mRegProgess.setMessage("Please wait while we create your account..");
                    mRegProgess.setCanceledOnTouchOutside(false);
                    mRegProgess.show();
                    register_user(displayName,email,password);
              //  }


            }
        });


    }

    private void register_user(final String displayName, final String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
                    String uid=current_user.getUid();
                    mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    HashMap<String,String> userMap=new HashMap<>();
                    userMap.put("name",displayName);
                    mDatabaseReference.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                mRegProgess.dismiss();
                                Intent mainIntent=new Intent(RegisterActivity.this,MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //this wont allow you to come back to register page from mainactivity
                                startActivity(mainIntent);
                                finish(); //we dont want to come back to this activity on pressing back button
                            }
                        }
                    });



                }
                else{
                    mRegProgess.hide();
                    Toast.makeText(RegisterActivity.this, "Cannot sign in ,please check the form and try again", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
