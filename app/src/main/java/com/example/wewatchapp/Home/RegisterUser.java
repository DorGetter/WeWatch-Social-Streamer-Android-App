package com.example.wewatchapp.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wewatchapp.R;
import com.example.wewatchapp.managerPack.ProfileManager;
import com.example.wewatchapp.userPack.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.channels.FileChannel;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{


    private ImageView banner;
    private TextView registerUser;
    private EditText editTextFullName, editTextAge, editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (ImageView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = findViewById(R.id.signIn);
        registerUser.setOnClickListener(this);

        editTextFullName    = (EditText) findViewById(R.id.fullName);
        editTextAge         = (EditText) findViewById(R.id.age);
        editTextEmail       = (EditText) findViewById(R.id.email);
        editTextPassword    = (EditText) findViewById(R.id.password);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.signIn:
                registerUser();
                break;

        }
    }

    private void registerUser() {
        //Converting inputs to a strings
        String fullName = editTextFullName  .getText().toString().trim();
        String age      = editTextAge       .getText().toString().trim();
        String email    = editTextEmail     .getText().toString().trim();
        String password = editTextPassword  .getText().toString().trim();



        //Validate inputs:
        if(fullName.isEmpty()){
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }
        if(age.isEmpty()){
            editTextAge.setError("Age is required!");
            editTextAge.requestFocus();
            return;
        }
        //parse to int.
        int ageParse = Integer.parseInt(age);
        if(ageParse < 0 || ageParse>120){
            editTextAge.setError("Age need to be valid!");
            editTextAge.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email address required!");
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


        // create new Authentication
        mAuth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullName,ageParse,email);

                            //send it to firebase
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        //if you able to set the new user object to Firebase.
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    if(task.isSuccessful()) {
                                        System.out.println("successful registered");
                                        user.sendEmailVerification();
                                        Toast.makeText(RegisterUser.this,"User has been registered successfully! check your email to verify your account",Toast.LENGTH_LONG).show();
                                        //redirect to login layout
                                        returnToMain();

                                    }else{
                                        Toast.makeText(RegisterUser.this,
                                                "Failed to registered! Try again!",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterUser.this,
                                    "Failed to register",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void returnToMain() {
        startActivity(new Intent(RegisterUser.this, MainActivity.class));
    }
}