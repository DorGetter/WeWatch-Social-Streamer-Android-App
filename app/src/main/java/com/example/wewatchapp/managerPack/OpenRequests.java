package com.example.wewatchapp.managerPack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wewatchapp.R;
import com.example.wewatchapp.userPack.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OpenRequests extends AppCompatActivity implements View.OnClickListener{


    private TextView back;


    /* firebase object */
    FirebaseDatabase database;
    /* firebase reference to the root */
    DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_requests);

        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference();


        /* shows new requests from the database */
        final TextView requestTextView = (TextView) findViewById(R.id.textNewRequest1);

        /* check if there is new requests on the database */
        rootRef.child("Requests").addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String requestList = "";
                String requestLine = "new request from: ";

                for (DataSnapshot child : snapshot.getChildren()) {

                    /* use for debug */
                    String requestKey = child.getKey();
                    Request request = child.getValue(Request.class);
                    String name = request.getUserName();
                    requestList = requestList + "\n" + requestLine + name;
                    requestTextView.setText(requestList);


                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OpenRequests.this,"Error!",Toast.LENGTH_LONG);
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.back:
                startActivity(new Intent(this, ProfileManager.class));
                break;
        }

    }
}