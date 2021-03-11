package com.example.food_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity implements View.OnClickListener  {

     TextView login_button;
      EditText email, password;
     String Email, Password;
//    private Button login_button;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //data creating database instance.
        mAuth = FirebaseAuth.getInstance();

        //create_users  = (TextView)findViewById(R.id.create_account);

        login_button = (Button)findViewById(R.id.login_btn);
        login_button.setOnClickListener(this);
//        login_button.setOnClickListener(new View.OnClickListener(){
//
//
//            @Override
//            public void onClick(View v) {
//                btn_longIn();
//            }
//        });

        email = (EditText) findViewById(R.id.inputEmail);
        password = (EditText) findViewById(R.id.input_password);


        // Check if user is already authenticated
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(login.this, MainActivity.class));
            finish();
        }



    }
//
    public void btn_register(View view) {
        Intent intent = new Intent(login.this, signup.class);
        startActivity(intent);
    }

//    public void btn_longIn(View, view) {
//
//    }
//
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_account:
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
                break;
            case R.id.login_btn:
                btn_longIn();
                break;

        }

    }

    public void btn_longIn() {
        String Email = email.getText().toString();
        String Password = password.getText().toString();
//
        if (Email.isEmpty()) {
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }
        if (Password.isEmpty()) {
            password.setError("Password is required!");
            password.requestFocus();

        }
        if (Password.length() < 8) {
            password.setError("Minimum password length should be 8");
            password.requestFocus();
            return;
        }

        //progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){

            @Override
            public void onComplete(@NonNull Task<AuthResult> task)

            {

                if (task.isSuccessful()){
                    //register user
                    startActivity (new Intent (login.this, MainActivity.class));
                    finish();
                }else{

                    Toast.makeText(login.this, "Failed to login! Please Check your credentials", Toast.LENGTH_LONG).show();
                }

            }

        });

    }

//    public void login_btn(View view) {
//        String Email = email.getText().toString().trim();
//        String Password = password.getText().toString().trim();
//
//        if (Email.isEmpty()) {
//            email.setError("Email is required!");
//            email.requestFocus();
//            return;
//        }
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
//            email.setError("Please provide valid email!");
//            email.requestFocus();
//            return;
//        }
//        if (Password.isEmpty()) {
//            password.setError("Password is required!");
//            password.requestFocus();
//
//        }
//        if (Password.length() < 8) {
//            password.setError("Minimum password length should be 8");
//            password.requestFocus();
//            return;
//        }
//
//        progressBar.setVisibility(View.VISIBLE);
//
//        mauth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
//
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task)
//
//            {
//
//                if (task.isSuccessful()){
//                    //register user
//                    startActivity (new Intent (login.this, MainActivity.class));
//                }else{
//
//                    Toast.makeText(login.this, "Failed to login! Please Check your credentials", Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//        });
//
//
//    }
}


