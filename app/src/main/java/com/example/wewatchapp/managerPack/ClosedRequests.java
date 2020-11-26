package com.example.wewatchapp.managerPack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wewatchapp.R;
import com.example.wewatchapp.userPack.Request;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClosedRequests extends AppCompatActivity implements View.OnClickListener{

    /* buttons */
    private TextView back;
    private TextView open, open2, open3, open4, open5, open6;
    private TextView remove;

    /* firebase object */
    FirebaseDatabase database;
    /* firebase reference to the root */
    DatabaseReference rootRef;

    /* use to store requests from firebase */
    final Request[] requests = new Request[6];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closed_requests);

        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        remove = findViewById(R.id.remove);
        remove.setOnClickListener(this);

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

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.back:
                startActivity(new Intent(this, ProfileManager.class));
                break;
        }
    }
}