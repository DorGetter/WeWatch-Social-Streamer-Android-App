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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerMyActivity extends AppCompatActivity implements View.OnClickListener{

    /* buttons */
    private TextView back;



    /* firebase object */
    FirebaseDatabase database;
    /* firebase reference to the root */
    DatabaseReference rootRef;

    /* use to store requests from firebase */
    final Request[] requests = new Request[6];

    private FirebaseUser manager;
    final private DatabaseReference referenceManager= FirebaseDatabase.getInstance().getReference("Managers");

    String managerName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_my);

        manager = FirebaseAuth.getInstance().getCurrentUser();

        // get the current manager details
        referenceManager.child(manager.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserManager userProfile = snapshot.getValue(UserManager.class);
                if(userProfile != null){
                    managerName = userProfile.getFullName();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ManagerMyActivity.this,"Something wrong happened!",Toast.LENGTH_LONG);
            }
        });


        back = findViewById(R.id.back);
        back.setOnClickListener(this);


        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference();

        /* shows the requests that this manager worked on */
        final TextView activityTextView = (TextView) findViewById(R.id.textNewActivity);
        final TextView activityTextView2 = (TextView) findViewById(R.id.textNewActivity2);
        final TextView activityTextView3 = (TextView) findViewById(R.id.textNewActivity3);
        final TextView activityTextView4 = (TextView) findViewById(R.id.textNewActivity4);
        final TextView activityTextView5 = (TextView) findViewById(R.id.textNewActivity5);
        final TextView activityTextView6 = (TextView) findViewById(R.id.textNewActivity6);

        /* database requests listener */
        rootRef.child("Requests").addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot snapshot) {

                /* use for debug */
                String requestList = "";

                /* to show each request by name in different text view */
                String requestLine = "request from: ";

                /* use for switch case to insert each request to correct number of text view */
                int text_view_num = 1;

                /* use for increment the 'text view num' only if the request closed by this manager */
                boolean toINC = false;

                for (DataSnapshot child : snapshot.getChildren()) {

                    toINC = false;

                    /* use for debug */
                    System.out.println(child.toString());

                    /* store request data */
                    String requestKey = child.getKey();
                    Request request = child.getValue(Request.class);
                    String status = request.getStatus();
                    String closed_by = request.getClosedBy();
                    String movie_name = request.getMovieName();
                    String name = request.getUserName();

                    /* use for debug */
                    requestList = requestList + "\n" + requestLine + name;



                    /* show only requests closed by this manager */
                    if(closed_by.compareTo(managerName) == 0) {
                        requests[text_view_num - 1] = child.getValue(Request.class);
                        toINC = true;
                    }


                    switch(text_view_num){
                        case 1:
                            if(status.compareTo("OPEN") != 0 && closed_by.compareTo(managerName) == 0)
                                activityTextView.setText("you closed " + requestLine + "   " + name
                                + "\nfor the movie:   " + movie_name);
                            break;
                        case 2:
                            if(status.compareTo("OPEN") != 0 && closed_by.compareTo(managerName) == 0)
                                activityTextView2.setText("you closed " + requestLine + "   " + name
                                        + "\nfor the movie:   " + movie_name);
                            break;
                        case 3:
                            if(status.compareTo("OPEN") != 0 && closed_by.compareTo(managerName) == 0)
                                activityTextView3.setText("you closed " + requestLine + "   " + name
                                        + "\nfor the movie:   " + movie_name);
                            break;
                        case 4:
                            if(status.compareTo("OPEN") != 0 && closed_by.compareTo(managerName) == 0)
                                activityTextView4.setText("you closed " + requestLine + "   " + name
                                        + "\nfor the movie:   " + movie_name);
                            break;
                        case 5:
                            if(status.compareTo("OPEN") != 0 && closed_by.compareTo(managerName) == 0)
                                activityTextView5.setText("you closed " + requestLine + "   " + name
                                        + "\nfor the movie:   " + movie_name);
                            break;
                        case 6:
                            if(status.compareTo("OPEN") != 0 && closed_by.compareTo(managerName) == 0)
                                activityTextView6.setText("you closed " + requestLine + "   " + name
                                        + "\nfor the movie:   " + movie_name);
                            break;
                    }

                    if(toINC)
                        text_view_num ++;



                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ManagerMyActivity.this,"Error!",Toast.LENGTH_LONG);
            }

        });

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