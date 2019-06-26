package com.example.firebasecrud;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_post;
    EditText et_post;
   public static String uid="";
    ListView listViewPosts;
    private FirebaseAuth mAuth;
    DatabaseReference databasePosts,databasePostsAll;

    List<PostModel> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser current_user= FirebaseAuth.getInstance().getCurrentUser();
        uid=current_user.getUid();
        Log.d("useriidddd",uid);
       // databaseTracks = FirebaseDatabase.getInstance().getReference("posts").child(uid);
        databasePosts= FirebaseDatabase.getInstance().getReference().child("posts").child(uid);
       // databasePostsAll= FirebaseDatabase.getInstance().getReference().child("posts");
        btn_post=(Button)findViewById(R.id.buttonAddpost);
        et_post=(EditText)findViewById(R.id.editTextName);
        listViewPosts = (ListView) findViewById(R.id.listViewPost);


        posts = new ArrayList<>();

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePost();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser=mAuth.getCurrentUser();

        if(currentUser==null){
            sendToStart();
        }

        databasePosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                posts.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    PostModel post = postSnapshot.getValue(PostModel.class);
                    posts.add(post);
                }
                PostList postListAdapter = new PostList(MainActivity.this, posts);
                listViewPosts.setAdapter(postListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void sendToStart() {
        Intent startIntent=new Intent(MainActivity.this,StartActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.main_logout_btn){
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }

        return true;
    }


    private void savePost() {
        String entertext=et_post.getText().toString().trim();
        if (!TextUtils.isEmpty(entertext)) {
            String id  = databasePosts.push().getKey();
            PostModel postss = new PostModel(id, entertext);
            databasePosts.child(id).setValue(postss);
            Toast.makeText(this, "Post saved", Toast.LENGTH_LONG).show();
            et_post.setText("");
        } else {
            Toast.makeText(this, "Please enter post", Toast.LENGTH_LONG).show();
        }
    }
}
