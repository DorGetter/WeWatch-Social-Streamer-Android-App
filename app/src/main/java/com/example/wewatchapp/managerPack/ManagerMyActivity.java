package com.example.wewatchapp.managerPack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wewatchapp.R;
import com.example.wewatchapp.userPack.Request;
import com.example.wewatchapp.userPack.User;
import com.example.wewatchapp.utilitiesPack.Views;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerMyActivity extends AppCompatActivity implements View.OnClickListener{

    /* firebase object */
    FirebaseDatabase database;
    /* firebase reference to the root */
    DatabaseReference rootRef;

    private FirebaseUser manager;
    private DatabaseReference reference
            = FirebaseDatabase.getInstance().getReference("Managers");

    String managerName = "";

    TextView text ;

    String sb = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        text = (TextView) findViewById(R.id.actualViews);
        manager = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();



        getManagerDetails();



    }

    private void getManagerDetails() {

        reference.child(manager.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserManager managerProfile    = snapshot.getValue(UserManager.class);
                if(managerProfile != null){
                    managerName = managerProfile.getFullName();
                    showManagerRequests();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void showManagerRequests() {
        /* database requests listener */
        rootRef.child("Requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()){
                    Request request = child.getValue(Request.class);
                    if(request.getClosedBy().equals(managerName)){
                        sb += "You Closed Request from :  " + request.getUserName()  +
                                "\n" + "For The Movie :  " + request.getMovieName() + "\n\n";

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