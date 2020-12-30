package com.example.wewatchapp.userPack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import com.example.wewatchapp.R;
import com.example.wewatchapp.utilitiesPack.Triplet;
import com.example.wewatchapp.utilitiesPack.Views;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Feed extends AppCompatActivity {

    /* firebase object */
    FirebaseDatabase database;
    /* firebase reference to the root */
    DatabaseReference rootRef;

    /* reference to views */
    DatabaseReference ViewsRef;

    /* use to store the current user name */
    String userName = "";

    /* show the friends activity on the page */
    TextView text ;

    /* use as a string buffer for friends activities */
    String sb = "";

    /* list to store my friends names from firebase */
    ArrayList<String> myFriends = new ArrayList<>();

    /* will contain all the views from friends before sorting by date*/
    ArrayList<Triplet> views_triplet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        text = (TextView) findViewById(R.id.actualViews);

        rootRef = database.getInstance().getReference("Friends");
        ViewsRef = database.getInstance().getReference();

        userName = getIntent().getStringExtra("user name");

        /* get list of friends from firebase */
        initMyFriendsL();
    }


    /* get my friends names from firebase */
    private void initMyFriendsL() {

        rootRef.child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot child : snapshot.getChildren()){

                    Friend friend = child.getValue(Friend.class);
                    String name = friend.getName();
                    myFriends.add(name);
                }

                /* update the friends views layout */
                showFriendsViews();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }





    /* get form firebase friends views */
    private void showFriendsViews() {
        /* database views listener */
        ViewsRef.child("Views").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    Views views = child.getValue(Views.class);

                    if (views.getUserName() != null && (myFriends.contains(views.getUserName()))) {

                        /* add single watch to list */
                        views_triplet.add(new Triplet(views.getUserName(),views.getMovieName(),views.getDate()));

                    }
                }
                /* sort the list by watch date */
                sortToView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /* sort function */
    private void sortToView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            views_triplet.sort(new Comparator<Triplet>() {
                @Override
                public int compare(Triplet o1, Triplet o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            });
        }
        for(Triplet t : views_triplet){
            sb += t.getName() + " watched : " + t.getMovie() +
                                "\n" + "on : " + t.getDate() + "\n\n";
        }
        text.setText(sb);
    }
}