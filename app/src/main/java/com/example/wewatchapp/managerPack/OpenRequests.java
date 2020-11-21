package com.example.wewatchapp.managerPack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
        final TextView requestTextView = (TextView) findViewById(R.id.textNewRequest);
        final TextView requestTextView2 = (TextView) findViewById(R.id.textNewRequest2);
        final TextView requestTextView3 = (TextView) findViewById(R.id.textNewRequest3);
        final TextView requestTextView4 = (TextView) findViewById(R.id.textNewRequest4);
        final TextView requestTextView5 = (TextView) findViewById(R.id.textNewRequest5);
        final TextView requestTextView6 = (TextView) findViewById(R.id.textNewRequest6);

        /* database requests listener */
        rootRef.child("Requests").addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot snapshot) {

                /* use for debug */
                String requestList = "";

                /* to show each request by name in different text view */
                String requestLine = "new request from: ";
                /* use for switch case to insert each request to correct number of text view */
                int text_view_num = 1;

                for (DataSnapshot child : snapshot.getChildren()) {

                    /* use for debug */
                    System.out.println(child.toString());

                    /* use for debug */
                    String requestKey = child.getKey();
                    Request request = child.getValue(Request.class);
                    String name = request.getUserName();
                    requestList = requestList + "\n" + requestLine + name;


                    switch(text_view_num){
                        case 1:
                            requestTextView.setText(requestLine + name + " Click Here");
                            break;
                        case 2:
                            requestTextView2.setText(requestLine + name + " Click Here");
                            break;
                        case 3:
                            requestTextView3.setText(requestLine + name + " Click Here");
                            break;
                        case 4:
                            requestTextView4.setText(requestLine + name + " Click Here");
                            break;
                        case 5:
                            requestTextView5.setText(requestLine + name + " Click Here");
                            break;
                        case 6:
                            requestTextView6.setText(requestLine + name + " Click Here");
                            break;
                    }

                    text_view_num ++;

                    //requestTextView.setText(requestList);



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