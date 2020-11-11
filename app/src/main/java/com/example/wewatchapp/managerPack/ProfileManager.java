package com.example.wewatchapp.managerPack;

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

public class ProfileManager extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser Manager;
    final private DatabaseReference referenceManager=
            FirebaseDatabase.getInstance().getReference("Managers");


    Button logOut,openRequestsButton ,closedRequestsButton ,myMoviesButton ,myActivityButton ,uploadMovieButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_manager);

        Manager                         =   FirebaseAuth.getInstance().getCurrentUser();
        final TextView greetingTextView =   (TextView) findViewById(R.id.greeting);


        logOut                  = (Button) findViewById(R.id.signOut)               ;
        openRequestsButton      = (Button) findViewById(R.id.OpenRequestsButton)    ;
        closedRequestsButton    = (Button) findViewById(R.id.closedRequestsButton)  ;
        myMoviesButton          = (Button) findViewById(R.id.myMoviesButton)        ;
        uploadMovieButton       = (Button) findViewById(R.id.uploadMovieButton)     ;
        myActivityButton        = (Button) findViewById(R.id.M_myActivityButton)    ;

        logOut                  .setOnClickListener(this);
        openRequestsButton      .setOnClickListener(this);
        closedRequestsButton    .setOnClickListener(this);
        myMoviesButton          .setOnClickListener(this);
        myActivityButton        .setOnClickListener(this);
        uploadMovieButton       .setOnClickListener(this);


        // greeting the Manager (Top Left write manager name)
        referenceManager.child(Manager.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserManager userProfile    =  snapshot.getValue(UserManager.class);
                if(  userProfile   != null){
                    String fullName = userProfile.getFullName();
                    greetingTextView.setText("Hello "+ fullName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileManager.this,"Something wrong happened!",Toast.LENGTH_LONG);
            }
        });

    }

    /**
     * switch case for buttons.
     * @param view - current view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.OpenRequestsButton:
                //directing to OpenRequests layout
                break;
            case R.id.closedRequestsButton:
                //directing to closedRequests layout
                break;
            case R.id.myMoviesButton:
                //directing to myMovies layout
                break;

            case R.id.uploadMovieButton:
                //directing to UploadMovies layout
                startActivity(new Intent(this,UploadMovie.class));
                break;

            case R.id.M_myActivityButton:
                //directing to Statistics layout
                break;

            case R.id.signOut:
                //directed to Login layout (MainActivity).
                startActivity(new Intent( this, MainActivity.class));
                break;

            default:
                break;
        }
    }
}