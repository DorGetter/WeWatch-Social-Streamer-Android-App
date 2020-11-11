package com.example.wewatchapp.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wewatchapp.R;
import com.example.wewatchapp.managerPack.ProfileManager;
import com.example.wewatchapp.userPack.ProfileUser;
import com.example.wewatchapp.userPack.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView registerUser,registerManager,forgotPassword;

    private EditText editTextEmail, editTextPassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    String origin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        registerUser = (TextView) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        registerManager = (TextView) findViewById(R.id.registerManager);
        registerManager.setOnClickListener(this);

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextEmail       = (EditText) findViewById(R.id.email);
        editTextPassword    = (EditText) findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerUser:
                // intent =כוונה
                startActivity(new Intent(this,RegisterUser.class));
                break;
            case R.id.registerManager:
                startActivity(new Intent(this,RegisterManager.class));
                break;

            case R.id.forgotPassword:
                startActivity(new Intent(this,ForgotPassword.class));
                break;

            case R.id.signIn:
                userLogin();
                break;
        }
    }

    /**
     * user login functionality.
     */
    private void userLogin() {
        String email    = editTextEmail     .getText().toString().trim();
        String password = editTextPassword  .getText().toString().trim();

        //Validations:

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Min password should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference referenceUser = FirebaseDatabase.getInstance().getReference("Users");
                    DatabaseReference referenceManager = FirebaseDatabase.getInstance().getReference("Managers");

                    //Redirect to user profile:
                    if(user.isEmailVerified()) {
                        EnterToProfile(referenceUser,referenceManager,user);
                    }
                    else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "check your email to verify your account", Toast.LENGTH_LONG).show();

                    }
                }else{
                    Toast.makeText(MainActivity.this,"Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();

                }
            }
        });

    }


    /**
     * After performing a password and email check ,
     * this function will check if the current 'user' is saved under "User" or "Manager" in the database,
     * in order to redirect to the desired page.
     * (Managers aka 'ManagerUser' send to Manager layout, and Users aka 'User' to the regular user layout).
     * @param referenceUser     - Will hold the reference to "root"/"Users".
     * @param referenceManager  - Will hold the reference to "root"/"Managers".
     * @param user              - Current user.
     */
    private void EnterToProfile(DatabaseReference referenceUser, DatabaseReference referenceManager, FirebaseUser user) {

        referenceUser.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    startActivity(new Intent(MainActivity.this, ProfileUser.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Something wrong happened!",Toast.LENGTH_LONG);
            }
        });

        System.out.println("checking if its a manager ");
        referenceManager.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    startActivity(new Intent(MainActivity.this, ProfileManager.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Something wrong happened!",Toast.LENGTH_LONG);
            }
        });
    }


}