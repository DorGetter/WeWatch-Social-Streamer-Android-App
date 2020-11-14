package com.example.wewatchapp.userPack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wewatchapp.Home.MainActivity;
import com.example.wewatchapp.Home.RegisterUser;
import com.example.wewatchapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestForm extends AppCompatActivity implements View.OnClickListener{


    private TextView sendRequest;
    private TextView back;
    private EditText movieCategory, movieName, yourName ;

    /* firebase object */
    FirebaseDatabase database;
    /* firebase reference to the root */
    DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);

        sendRequest = findViewById(R.id.sendRequest);
        sendRequest.setOnClickListener(this);

        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        movieCategory = (EditText) findViewById(R.id.movieCategory);
        movieName = (EditText) findViewById(R.id.movieName);
        yourName = (EditText) findViewById(R.id.yourName);

        /* set the path to requests table */
        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference("Requests");
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.back:
                startActivity(new Intent(this, ProfileUser.class));
                break;

            case R.id.sendRequest:
                sendMovieRequest();
                break;

        }

    }

    /* on clicking the 'send request' button this function will send the request to firebase */
    private void sendMovieRequest() {

        String category = movieCategory.getText().toString().trim();
        String movie = movieName.getText().toString().trim();
        String userName = yourName.getText().toString().trim();

        Request request = new Request(category, movie, userName, null);

        /* set an ID from the database */
        request.setRequestID(rootRef.push().getKey());
        /* insert the movie by its ID */
        rootRef.child(request.getRequestID()).setValue(request);

        Toast.makeText(this, "Request sent...", Toast.LENGTH_LONG).show();

    }
}