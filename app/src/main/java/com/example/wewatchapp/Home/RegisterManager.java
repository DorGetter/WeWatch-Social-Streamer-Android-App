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
import com.example.wewatchapp.managerPack.UserManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterManager extends AppCompatActivity implements View.OnClickListener {


    private ImageView banner;
    private TextView registerManager;
    private EditText editTextFullName, editTextAge, editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_manager);


        mAuth = FirebaseAuth.getInstance();

        banner = (ImageView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerManager = findViewById(R.id.signIn);
        registerManager.setOnClickListener(this);

        editTextFullName    = (EditText) findViewById(R.id.fullName);
        editTextAge         = (EditText) findViewById(R.id.age);
        editTextEmail       = (EditText) findViewById(R.id.email);
        editTextPassword    = (EditText) findViewById(R.id.password);

//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.signIn:
                registerManager();
                break;
        }
    }

    private void registerManager() {
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




        mAuth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            FirebaseUser Manager = FirebaseAuth.getInstance().getCurrentUser();
                            UserManager userM = new UserManager(fullName,ageParse,email);
                            //send it to firebase
                            FirebaseDatabase.getInstance().getReference("Managers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userM).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Manager.sendEmailVerification();
                                        Toast.makeText(RegisterManager.this,"Manager has been registered successfully! check your email to verify your account",Toast.LENGTH_LONG).show();
                                        // startActivity(new Intent(RegisterManager.this, MainActivity.class));
                                    }else{
                                        Toast.makeText(RegisterManager.this,
                                                "Failed to registered! Try again!",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterManager.this,
                                    "Failed to register",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}