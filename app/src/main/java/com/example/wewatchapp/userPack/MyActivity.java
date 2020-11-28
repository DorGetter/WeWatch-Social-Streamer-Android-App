package com.example.wewatchapp.userPack;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wewatchapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MyActivity extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference
            = FirebaseDatabase.getInstance().getReference("Users");
    private ArrayList<String> log;

    private TextView activity_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);
        /**activity_view = (TextView) findViewById(R.id.activity);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile    = snapshot.getValue(User.class);
                if(  userProfile   != null){
                    log = userProfile.getLog();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
         **/






    }

}
