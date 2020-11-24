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

import java.text.BreakIterator;

public class OpenRequests extends AppCompatActivity implements View.OnClickListener{

    /* buttons */
    private TextView back;
    private TextView open, open2, open3, open4, open5, open6;
    private TextView close;


    /* firebase object */
    FirebaseDatabase database;
    /* firebase reference to the root */
    DatabaseReference rootRef;

    /* use to store requests from firebase */
    final Request[] requests = new Request[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_requests);

        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        close = findViewById(R.id.close);
        close.setOnClickListener(this);

        open = findViewById(R.id.open);
        open.setOnClickListener(this);

        open2 = findViewById(R.id.open2);
        open2.setOnClickListener(this);

        open3 = findViewById(R.id.open3);
        open3.setOnClickListener(this);

        open4 = findViewById(R.id.open4);
        open4.setOnClickListener(this);

        open5 = findViewById(R.id.open5);
        open5.setOnClickListener(this);

        open6 = findViewById(R.id.open6);
        open6.setOnClickListener(this);

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

                    requests[text_view_num-1] = child.getValue(Request.class);

                    switch(text_view_num){
                        case 1:
                            requestTextView.setText(requestLine + name + " Click");
                            break;
                        case 2:
                            requestTextView2.setText(requestLine + name + " Click");
                            break;
                        case 3:
                            requestTextView3.setText(requestLine + name + " Click");
                            break;
                        case 4:
                            requestTextView4.setText(requestLine + name + " Click");
                            break;
                        case 5:
                            requestTextView5.setText(requestLine + name + " Click");
                            break;
                        case 6:
                            requestTextView6.setText(requestLine + name + " Click");
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


    /* use to store the request that the manger want to work on */
    Request requestInWork;

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.back:
                startActivity(new Intent(this, ProfileManager.class));
                break;

            case R.id.open:
                final TextView showTheRequest = (TextView) findViewById(R.id.showTheRequest);
                if(requests[0] != null) {
                    showTheRequest.setText("Request from:  " + requests[0].getUserName() + "\n"
                            + "Movie:  " + requests[0].getMovieName() + "\n");
                    requestInWork = requests[0];
                }
                break;

            case R.id.open2:
                final TextView showTheRequest2 = (TextView) findViewById(R.id.showTheRequest);
                if(requests[1] != null) {
                    showTheRequest2.setText("Request from:  " + requests[1].getUserName() + "\n"
                            + "Movie:  " + requests[1].getMovieName() + "\n");
                    requestInWork = requests[1];
                }
                break;

            case R.id.open3:
                final TextView showTheRequest3 = (TextView) findViewById(R.id.showTheRequest);
                if(requests[2] != null) {
                    showTheRequest3.setText("Request from:  " + requests[2].getUserName() + "\n"
                            + "Movie:  " + requests[2].getMovieName() + "\n");
                    requestInWork = requests[2];
                }
                break;

            case R.id.open4:
                final TextView showTheRequest4 = (TextView) findViewById(R.id.showTheRequest);
                if(requests[3] != null) {
                    showTheRequest4.setText("Request from:  " + requests[3].getUserName() + "\n"
                            + "Movie:  " + requests[3].getMovieName() + "\n");
                    requestInWork = requests[3];
                }
                break;

            case R.id.open5:
                final TextView showTheRequest5 = (TextView) findViewById(R.id.showTheRequest);
                if(requests[4] != null) {
                    showTheRequest5.setText("Request from:  " + requests[4].getUserName() + "\n"
                            + "Movie:  " + requests[4].getMovieName() + "\n");
                    requestInWork = requests[4];
                }
                break;

            case R.id.open6:
                final TextView showTheRequest6 = (TextView) findViewById(R.id.showTheRequest);
                if(requests[5] != null) {
                    showTheRequest6.setText("Request from:  " + requests[5].getUserName() + "\n"
                            + "Movie:  " + requests[5].getMovieName() + "\n");
                    requestInWork = requests[5];
                }
                break;

            case R.id.close:
                final TextView showTheRequestClosed = (TextView) findViewById(R.id.showTheRequest);
                showTheRequestClosed.setText("Request from:  " + requestInWork.getUserName() + "\n"
                            + "For The Movie:  " + requestInWork.getMovieName() + "\n  Is Now Closed");
                String closedRequestID = requestInWork.getRequestID();
                rootRef.child("Requests").child(closedRequestID).removeValue();
                break;


        }

    }
}