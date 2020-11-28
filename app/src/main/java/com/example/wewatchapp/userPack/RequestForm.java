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


    private TextView sendRequest;
    private TextView back;

    private EditText movieCategory, movieName, yourName ;
    private FirebaseUser user;
    private DatabaseReference reference
            = FirebaseDatabase.getInstance().getReference("Users");
    private TextView action, comedy, drama, adventures;

    private EditText movieName;

    /* firebase object */
    FirebaseDatabase database;
    /* firebase reference to the root */
    DatabaseReference rootRef;


    String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);

        user = FirebaseAuth.getInstance().getCurrentUser();

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

        sendRequest = findViewById(R.id.sendRequest);
        sendRequest.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();

        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        action = findViewById(R.id.action);
        action.setOnClickListener(this);

        comedy = findViewById(R.id.comedy);
        comedy.setOnClickListener(this);

        drama = findViewById(R.id.drama);
        drama.setOnClickListener(this);

        adventures = findViewById(R.id.adventures);
        adventures.setOnClickListener(this);

        movieName = (EditText) findViewById(R.id.movieName);




        /* set the path to requests table */
        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference("Requests");
    }

    String movieCategory;

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.back:
                startActivity(new Intent(this, ProfileUser.class));
                break;

            case R.id.sendRequest:
                sendMovieRequest();
                reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userProfile    = snapshot.getValue(User.class);
                        if(  userProfile   != null){
                            userProfile.Logit("Requested "+movieName.toString());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                break;

            case R.id.action:
                movieCategory = "ACTION";
                Toast.makeText(this, "ACTION selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.comedy:
                movieCategory = "COMEDY";
                Toast.makeText(this, "COMEDY selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.drama:
                movieCategory = "DRAMA";
                Toast.makeText(this, "DRAMA selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.adventures:
                movieCategory = "ADVENTURES";
                Toast.makeText(this, "ADVENTURES selected", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    /* on clicking the 'send request' button this function will send the request to firebase */
    private void sendMovieRequest() {

        String movie = movieName.getText().toString().trim();

        Request request = new Request(movieCategory, movie, userName, null);

        /* set an ID from the database */
        request.setRequestID(rootRef.push().getKey());
        /* insert the movie by its ID */
        rootRef.child(request.getRequestID()).setValue(request);

        Toast.makeText(this, "Thanks   " + userName
                + "\nYour Request sent...", Toast.LENGTH_LONG).show();

    }
}