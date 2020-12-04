package com.example.wewatchapp.userPack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wewatchapp.R;
import com.example.wewatchapp.utilitiesPack.Views;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userActivitydisplay extends AppCompatActivity implements View.OnClickListener {

    /* firebase object */
    FirebaseDatabase database;
    /* firebase reference to the root */
    DatabaseReference rootRef;

    private FirebaseUser user;
    private DatabaseReference reference
            = FirebaseDatabase.getInstance().getReference("Users");

    String userName = "";

    TextView text ;

    String sb = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        text = (TextView) findViewById(R.id.actualViews);
        user = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = database.getInstance().getReference();



        getUserDetails();



    }

    private void getUserDetails() {

        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile    = snapshot.getValue(User.class);
                if(  userProfile   != null){
                    userName = userProfile.getFullName();
                    showUserViews();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void showUserViews() {
        /* database views listener */
        rootRef.child("Views").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()){
                    Views views = child.getValue(Views.class);
                    if(views.getUserName() != null && (views.getUserName().equals(userName))){
                        System.out.println(views.toString());

                        sb += "you watched : " + views.getMovieName()  +
                                "\n" + "on : " + views.getDate() + "\n\n";

                        System.out.println(sb);

                    }
                }
                text.setText(sb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}