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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestForm extends AppCompatActivity implements View.OnClickListener{

    /* buttons */
    private TextView sendRequest;
    private TextView back;

    /* edit text for movie name */
    private EditText movieName;

    /* user reference from firebase */
    private FirebaseUser user;
    private DatabaseReference reference
            = FirebaseDatabase.getInstance().getReference("Users");




    /* firebase object */
    FirebaseDatabase database;
    /* firebase reference to the root */
    DatabaseReference rootRef;

    /* string to store user name */
    String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);

        /* get the current user from fire base auth */
        user = FirebaseAuth.getInstance().getCurrentUser();

        /* store the current user name */
        reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile    = snapshot.getValue(User.class);
                if(  userProfile   != null){
                    userName = userProfile.getFullName();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /* initialize buttons and text on the page */
        sendRequest = findViewById(R.id.sendRequest);
        sendRequest.setOnClickListener(this);

        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        movieName = (EditText) findViewById(R.id.movieName);


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

                /* call send movie request function */
                sendMovieRequest();
                /* show a dialog */
                openDialog();
                break;

        }

    }

    /* open new dialog */
    public void openDialog(){

        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(), "dialog");

    }


    /* on clicking the 'send request' button this function will send the request to firebase */
    private void sendMovieRequest() {

        /* get the movie name the user typed */
        String movie = movieName.getText().toString().trim();

        /* check correct input */
        if(movie.isEmpty()) {
            movieName.setError("movie name is required!");
            movieName.requestFocus();
            return;
        }

        if(movie.length() < 3){
            movieName.setError("Min movie name should be 3 characters!");
            movieName.requestFocus();
            return;
        }

        /* create new request object */
        Request request = new Request("", movie, userName, null);

        /* set an ID from the database */
        request.setRequestID(rootRef.push().getKey());
        /* insert the movie by its ID */
        rootRef.child(request.getRequestID()).setValue(request);

        Toast.makeText(this, "Thanks   " + userName
                + "\nYour Request sent...", Toast.LENGTH_LONG).show();

        /* clear the movie name edit text when the request sent */
        movieName.setText("");
    }
}