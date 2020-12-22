package com.example.wewatchapp.userPack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wewatchapp.Home.MainActivity;
import com.example.wewatchapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileUser extends AppCompatActivity implements View.OnClickListener {
    private FirebaseUser user;
    private DatabaseReference reference
            = FirebaseDatabase.getInstance().getReference("Users");

    /* firebase object */
    FirebaseDatabase database;
    /* firebase reference to the root */
    DatabaseReference rootRef;

    /* current user name */
    String current_name;



    /* list to store all the users names from firebase */
    ArrayList<String> usersList = new ArrayList<>();

    Button moviesLibButton,feedButton,FriendsButton,myActivityButton,logOut,sendRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        /* init the firebase reference */
        rootRef = FirebaseDatabase.getInstance().getReference();





        /* call the function to get all the users name in to usersList */
        getFriends();


        //initializing objects.
        user                            = FirebaseAuth.getInstance().getCurrentUser();
        final TextView greetingTextView = (TextView) findViewById(R.id.greeting);

        sendRequest = (Button) findViewById(R.id.SendRequests);

        feedButton          = (Button) findViewById(R.id.FeedButton)        ;
        myActivityButton    = (Button) findViewById(R.id.myActivityButton)  ;
        moviesLibButton     = (Button) findViewById(R.id.MoviesButton)      ;
        FriendsButton       = (Button) findViewById(R.id.FriendsButton)     ;
        logOut              = (Button) findViewById(R.id.signOut)           ;


        sendRequest.setOnClickListener(this);

        feedButton          .setOnClickListener(this);
        myActivityButton    .setOnClickListener(this);
        moviesLibButton     .setOnClickListener(this);
        FriendsButton       .setOnClickListener(this);
        logOut              .setOnClickListener(this);

        // greeting the User (Top Left write user name)
        reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile    = snapshot.getValue(User.class);
                if(  userProfile   != null){
                    String fullName = userProfile.getFullName();
                    greetingTextView.setText("Hello "+ fullName);
                    current_name = fullName;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileUser.this,"Something wrong happened!",Toast.LENGTH_LONG);
            }
        });



    }


    /* get the users names form firebase in to usersList */
    public void getFriends(){

        /* database users listener */
        rootRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()){
                    User user = child.getValue(User.class);
                    usersList.add(user.getFullName());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




    /**
     * switch case for buttons.
     * @param view - current view
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.FeedButton:

//                startActivity(new Intent(this,Feed.class));
                break;
            case R.id.FriendsButton:
                /* send to 'Friends' activity list of users names and current user name by intent */

                startActivity(new Intent(this, Friends.class).putStringArrayListExtra("test", (ArrayList<String>) usersList)
                            .putExtra("user name", current_name));

                break;
            case R.id.MoviesButton:
                startActivity(new Intent(this,Vod.class));
                break;
            case R.id.myActivityButton:
//                startActivity(new Intent(this,MyActivity.class));
                startActivity(new Intent(this, userActivitydisplay.class));
                break;
            case R.id.SendRequests:
                startActivity(new Intent(this,RequestForm.class));
                break;
            case R.id.signOut:
                startActivity(new Intent( this,MainActivity.class));
                break;

        }

    }
}

/**
 rootRef.child("requests").addListenerForSingleValueEvent(new ValueEventListener() {
@Override
public void onDataChange(DataSnapshot dataSnapshot) {


// Result will be holded Here
for (DataSnapshot dsp : dataSnapshot.getChildren()) {
req.add(dsp.getValue(Request.class)); //add result into array list
}*/